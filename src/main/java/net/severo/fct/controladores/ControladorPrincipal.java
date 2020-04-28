package net.severo.fct.controladores;


import net.severo.fct.DAO.DAOException;
import net.severo.fct.servicio.ServicioCasa;
import net.severo.fct.servicio.ServicioConfinado;
import net.severo.fct.vistas.VistaPrincipal;

public class ControladorPrincipal {

    VistaPrincipal vp = null;

    public ControladorPrincipal() {
        vp = new VistaPrincipal();
    }

    public void iniciarAplicacion() {
        int sistemaAl = vp.elegirSistemaAlmacenamiento();
        if (sistemaAl == 0) {
            //salimos de la aplicacion
            return;

        } else {

            try {
                ServicioConfinado.getServicio().elegirSistemaAlmacenamiento(sistemaAl);
                ServicioCasa.getServicio().elegirSistemaAlmacenamiento(sistemaAl);
            } catch (DAOException ex) {
                vp.mostrarError("Ha habido un error al iniciar el sistema de almacenamiento " + ex.getMessage());
            }

            this.iniciarMenuPrincipal();
        }
    }

    public void iniciarMenuPrincipal() {
        do {
            int opcion = vp.menuPrincipal();
            switch (opcion) {
                case 0:
                    //Salimos
                    return;
                case 1:
                    new ControladorConfinado().iniciarConfinado();
                    break;
                case 2:
                    new ControladorCasa().iniciarCasa();
                    break;
                case 3:
                    //new ControladorInformes().iniciarInformes();
                    break;
            }
        } while (true);
    }

}
