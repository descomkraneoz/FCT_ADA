package net.severo.fct.controladores;

import net.severo.fct.DAO.DAOException;
import net.severo.fct.POJO.Casa;
import net.severo.fct.POJO.Confinado;
import net.severo.fct.servicio.ServicioCasa;
import net.severo.fct.servicio.ServicioConfinado;
import net.severo.fct.servicio.ServiciosException;
import net.severo.fct.vistas.VistaCasa;
import net.severo.fct.vistas.VistaConfinado;



public class ControladorConfinado {
    private VistaConfinado vv = null;

    public ControladorConfinado() {
        vv = new VistaConfinado();
    }

    public void iniciarConfinado() {
        do {
            int opcion = vv.menuPrincipal();
            if (opcion == 0) {
                return;
            }
            switch (opcion) {
                case 1:
                    this.ControladorNuevoConfinado();
                    break;
                case 2:
                    this.ControladorMostrarListaConfinados();
                    break;
                case 3:
                    this.ControladorEliminarConfinado();
                    break;
                case 4:
                    this.ControladorAsignarCasaAlConfinado();
                    break;
                case 5:
                    //No implementado
                    break;

            }
        } while (true);
    }


    public void ControladorNuevoConfinado() {
        Confinado v;
        Integer id = vv.pedirIdConfinado();
        if (id == null) {
            return;
        }
        String nombre = vv.pedirNombre();
        if (nombre == null) {
            return;
        }
        try {
            new VistaCasa().mostrarListaCasas(ServicioCasa.getServicio().servicioObtenerTodasLasCasas());
            Integer codCasa = new VistaCasa().pedirIdCasa();
            Casa casa = ServicioCasa.getServicio().servicioObtenerCasaPorID(codCasa);
            v = new Confinado(id, nombre, casa);
            ServicioConfinado.getServicio().servicioCrearConfinado(v);
        } catch (ServiciosException e) {
            vv.mostrarError("Error al generar un nuevo vehiculo en el controlador: " + e);
        } catch (DAOException e) {
            vv.mostrarError("Error desde el controlador al intentar obtener los datos, el confinado no será creado: " + e);
        }
    }

    public void ControladorEliminarConfinado() {
        try {
            vv.mostrarListaConfinados(ServicioConfinado.getServicio().servicioObtenerConfinados());
            Integer codigo = vv.pedirIdConfinado();
            if (codigo == null) {
                return;
            }
            ServicioConfinado.getServicio().servicioEliminarConfinado(codigo);
        } catch (DAOException dao) {
            vv.mostrarError("Error al intentar obtener los datos: " + dao.getMessage());
        } catch (ServiciosException se) {
            vv.mostrarError("Error al eliminar un confinado: " + se.getMessage());
        }
    }

    public void ControladorMostrarListaConfinados() {
        try {
            vv.mostrarListaConfinados(ServicioConfinado.getServicio().servicioObtenerConfinados());
        } catch (DAOException ex) {
            vv.mostrarError("Error al intentar obtener los datos" + ex);
            ex.printStackTrace();
        } catch (ServiciosException ex) {
            vv.mostrarError("Error al intentar mostrar los datos: " + ex);
            ex.printStackTrace();
        }
    }

    private void ControladorAsignarCasaAlConfinado() {
        try {
            ServicioCasa.getServicio().iniciarTransaccion();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        try {
            new VistaCasa().mostrarListaCasas(ServicioCasa.getServicio().servicioObtenerTodasLasCasas());
            Integer codCasa = vv.pedirIdCasa();
            vv.mostrarListaConfinados(ServicioConfinado.getServicio().servicioObtenerConfinados());
            Integer codigoConfinado = vv.pedirIdConfinado();

            ServicioConfinado.getServicio().servicioAsignarCasaAlConfinado(codCasa, codigoConfinado);

        } catch (DAOException dao) {

            vv.mostrarError("Error en el controlador al intentar obtener los datos: " + dao.getMessage());
        } catch (ServiciosException se) {
            se.printStackTrace();
            vv.mostrarError("Error en el controlador al asignar una casa al confinado: " + se.getMessage());
        }

        try {
            ServicioCasa.getServicio().finalizarTransaccion();
        } catch (DAOException e) {
            e.printStackTrace();
        }

    }

}
