package net.severo.fct.servicio;

import net.severo.fct.DAO.DAOException;
import net.severo.fct.DAO.ICasa;
import net.severo.fct.DAO.JDBC.CasaJDBC;
import net.severo.fct.POJO.Casa;
import net.severo.fct.POJO.Confinado;

import java.util.ArrayList;
import java.util.List;

public class ServicioCasa {

    private static ServicioCasa servicioCasa = null;
    private ICasa idao = null;

    public static ServicioCasa getServicio() throws DAOException {
        if (servicioCasa == null) {
            servicioCasa = new ServicioCasa();
        }
        return servicioCasa;
    }

    public void elegirSistemaAlmacenamiento(int opcion) throws DAOException {

        if (opcion == 1) {
            idao = new CasaJDBC();
        }
        if (opcion == 2) {

            //idao = new CasaHibernate();
        }
        if (opcion == 3) {

            // no implementado
        }

    }


    public Casa servicioObtenerCasaPorID(int id) throws DAOException, ServiciosException {
        List<Casa> casas = idao.obtenerTodasLasCasas();
        for (Casa m : casas) {
            if (m.getIdCasa() == (id)) {
                return m;
            }
        }
        throw new ServiciosException("No hay ninguna casa con el codigo especificado");
    }


    public void servicioNuevaCasa(Casa r) throws ServiciosException, DAOException {
        if (idao.obtenerCasaPorID(r.getIdCasa()) != null) {
            throw new ServiciosException("La casa ya existe.");
        }
        idao.iniciarTransaccion();
        idao.crearCasa(r);
        idao.finalizarTransaccion();
    }

    //obtener todos las casas en una lista

    public List<Casa> servicioObtenerTodasLasCasas() throws DAOException, ServiciosException {
        List<Casa> casas = idao.obtenerTodasLasCasas();
        if (casas.isEmpty()) {
            throw new ServiciosException("No hay ninguna casa en la base de datos");
        }

        return casas;
    }


    public ArrayList<Confinado> servicioObtenerTodosLosConfinados() throws DAOException, ServiciosException {
        idao.iniciarTransaccion();
        List<Casa> casas = idao.obtenerTodasLasCasas();
        ArrayList<Confinado> totalConfinados = new ArrayList<>();
        idao.finalizarTransaccion();
        for (Casa r : casas) {
            for (Confinado p : r.getConfinados()) {
                totalConfinados.add(p);
            }
        }
        return totalConfinados;
    }


    //eliminar una casa al pasarle un id

    public void servicioEliminarCasa(int codigo) throws DAOException, ServiciosException {

        if (servicioObtenerCasaPorID(codigo) == null) {
            throw new ServiciosException("La casa no existe");
        }
        idao.eliminarCasa(codigo);
    }

    //asignar confinados a una casa
    public void servicioAsignarCasaAlConfinado(int idCasa, int idConfinado) throws DAOException, ServiciosException {

        Confinado v;
        v = ServicioConfinado.getServicio().servicioObtenerConfinado(idConfinado);

        Casa m;
        m = this.servicioObtenerCasaPorID(idCasa);

        idao.asignarCasaAlConfinado(m, v);
    }


    //transacciones

    public void iniciarTransaccion() throws DAOException {
        idao.iniciarTransaccion();
    }

    public void finalizarTransaccion() throws DAOException {
        idao.finalizarTransaccion();
    }
}
