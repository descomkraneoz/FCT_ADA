package net.severo.fct.servicio;

import net.severo.fct.DAO.DAOException;
import net.severo.fct.DAO.Hibernate.ConfinadoHibernate;
import net.severo.fct.DAO.IConfinado;
import net.severo.fct.DAO.JDBC.ConfinadoJDBC;
import net.severo.fct.POJO.Casa;
import net.severo.fct.POJO.Confinado;

import java.util.List;

public class ServicioConfinado {

    private IConfinado idao = null;

    private static ServicioConfinado servicioConfinado = null;

    public static ServicioConfinado getServicio() throws DAOException {
        if (servicioConfinado == null) {
            servicioConfinado = new ServicioConfinado();
        }
        return servicioConfinado;
    }

    public void elegirSistemaAlmacenamiento(int opcion) throws DAOException {

        if (opcion == 1) {
            idao = new ConfinadoJDBC();
        }
        if (opcion == 2) {

            idao = new ConfinadoHibernate();
        }
        if (opcion == 3) {

            //NO IMPLEMENTADO
        }
    }

    //crear un confinado nuevo

    public void servicioCrearConfinado(Confinado v) throws ServiciosException, DAOException {
        if (idao.obtenerUnConfinadoPorID(v.getIdConfinado()) != null) {
            throw new ServiciosException("El confinado ya existe con esa id.");
        }

        idao.iniciarTransaccion();

        idao.crearNuevoConfinadoDAO(v);

        idao.finalizarTransaccion();

    }

    //obtener un confinado a partir de su id

    public Confinado servicioObtenerConfinado(int idConfinado) throws DAOException, ServiciosException {
        List<Confinado> confinados = idao.obtenerTodosLosConfinados();
        for (Confinado v : confinados) {
            if (v.getIdConfinado()==(idConfinado)) {
                return v;
            }
        }
        throw new ServiciosException("No hay ningún vehiculo con el codigo especificado");
    }

    //elimina un confinado al pasarle un id

    public void servicioEliminarConfinado(int codigo) throws DAOException, ServiciosException {
        //this.servicioObtenerConfinado(codigo);
        idao.eliminarConfinadoDAO(codigo);

    }

    //devuelve una lista con los confinados

    public List<Confinado> servicioObtenerConfinados() throws DAOException, ServiciosException {
        List<Confinado> confinados = idao.obtenerTodosLosConfinados();
        if (confinados.isEmpty()) {
            throw new ServiciosException("No hay ningún confinado");
        }
        return confinados;
    }

    //asignar confinado a una casa
    public void servicioAsignarCasaAlConfinado(int idCasa, int idConfinado) throws DAOException, ServiciosException {

        Confinado v;
        v = ServicioConfinado.getServicio().servicioObtenerConfinado(idConfinado);

        Casa m;
        m = ServicioCasa.getServicio().servicioObtenerCasaPorID(idCasa);

        idao.asignarCasaAlConfinado(m.getIdCasa(), v.getIdConfinado());
    }


    /**
     * Transacciones
     */

    public void finalizar() throws DAOException {
        //cierra las conexiones  e BD en caso de que fuera necesario
        //en el caso de archivos de texto o bianrios no es necesario hacer nada
        //ya que los abrimos y cerramos en cada operacion
        idao.finalizar();
    }

    public void iniciarTransaccion() throws DAOException {
        idao.iniciarTransaccion();
    }

    public void finalizarTransaccion() throws DAOException {
        idao.finalizarTransaccion();
    }

    /**
     *  FIN Transacciones
     */
}
