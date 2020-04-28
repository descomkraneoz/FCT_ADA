package net.severo.fct.controladores;

import net.severo.fct.DAO.DAOException;
import net.severo.fct.POJO.Casa;
import net.severo.fct.servicio.ServicioCasa;
import net.severo.fct.servicio.ServicioConfinado;
import net.severo.fct.servicio.ServiciosException;
import net.severo.fct.vistas.VistaCasa;
import net.severo.fct.vistas.VistaConfinado;

public class ControladorCasa {
    VistaCasa vm = null;

    public ControladorCasa() {
        vm = new VistaCasa();
    }

    public void iniciarCasa() {
        do {
            int opcion = vm.menuPrincipal();
            if (opcion == 0) {
                return;
            }

            switch (opcion) {
                case 1:
                    this.ControladorNuevaCasa();
                    break;
                case 2:
                    this.ControladorMostrarCasas();
                    break;
                case 3:
                    this.ControladorEliminarCasa();
                    break;
                case 4:
                    this.ControladorAsignarCasaAlConfinado();
                    break;
                case 5:
                   //
                    break;

            }
        } while (true);

    }

    public void ControladorNuevaCasa() {
        //Aqui podemos iniciar transacciones

        //Id
        Integer id = vm.pedirIdCasa();
        if (id == null) {
            return;
        }
        //jardin
        Boolean tiene_jardin = vm.pedirJardin();
        if (tiene_jardin == null) {
            return;
        }
        Casa mc = new Casa(id, tiene_jardin);
        try {
            ServicioCasa.getServicio().servicioNuevaCasa(mc);
        } catch (DAOException ex) {
            vm.mostrarError("Error al intentar acceder a los datos: " + ex.getMessage());

        } catch (ServiciosException ex) {
            vm.mostrarError("Error al intentar crear nueva casa: " + ex.getMessage());
        }

        //aqui podemos finalizar transacción

    }

    public void ControladorMostrarCasas() {
        try {
            vm.mostrarListaCasas(ServicioCasa.getServicio().servicioObtenerTodasLasCasas());
        } catch (DAOException ex) {
            vm.mostrarError("Error de controlador al intentar obtener los datos: " + ex);
        } catch (ServiciosException ex) {
            vm.mostrarError("Error de controlador al intentar mostrar las casas: " + ex);
        }
    }


    public void ControladorEliminarCasa() {
        try {
            vm.mostrarListaCasas(ServicioCasa.getServicio().servicioObtenerTodasLasCasas());
            Integer codigo = vm.pedirIdCasa();
            if (codigo == null) {
                return;
            }
            ServicioCasa.getServicio().servicioEliminarCasa(codigo);
        } catch (DAOException dao) {
            vm.mostrarError("Error al intentar obtener los datos: " + dao.getMessage());
        } catch (ServiciosException se) {
            vm.mostrarError("Error al eliminar una casa: " + se.getMessage());
        }
    }

    private void ControladorAsignarCasaAlConfinado() {
        try {
            ServicioCasa.getServicio().iniciarTransaccion();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        try {
            vm.mostrarListaCasas(ServicioCasa.getServicio().servicioObtenerTodasLasCasas());
            Integer codMec = vm.pedirIdCasa();

            new VistaConfinado().mostrarListaConfinados(ServicioConfinado.getServicio().servicioObtenerConfinados());
            Integer codigoConfinado = new VistaConfinado().pedirIdConfinado();

            ServicioCasa.getServicio().servicioAsignarCasaAlConfinado(codMec, codigoConfinado);

        } catch (DAOException dao) {
            vm.mostrarError("Error en el controlador al intentar obtener los datos: " + dao.getMessage());
        } catch (ServiciosException se) {
            vm.mostrarError("Error en el controlador al asignar una casa al confinado: " + se.getMessage());
        }

        try {
            ServicioCasa.getServicio().finalizarTransaccion();
        } catch (DAOException e) {
            e.printStackTrace();
        }

    }

}
