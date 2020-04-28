package net.severo.fct.DAO.Hibernate;

import net.severo.fct.DAO.DAOException;
import net.severo.fct.DAO.IConfinado;
import net.severo.fct.POJO.Casa;
import net.severo.fct.POJO.Confinado;

import java.util.List;

public class ConfinadoHibernate implements IConfinado {
    @Override
    public void crearNuevoConfinadoDAO(Confinado confinado) throws DAOException {

    }

    @Override
    public void eliminarConfinadoDAO(int idConfinado) throws DAOException {

    }

    @Override
    public Confinado obtenerUnConfinadoPorID(int id) throws DAOException {
        return null;
    }

    @Override
    public List<Confinado> obtenerTodosLosConfinados() throws DAOException {
        return null;
    }

    @Override
    public void asignarCasaAlConfinado(Casa m, Confinado v) throws DAOException {

    }

    @Override
    public void finalizar() throws DAOException {

    }

    @Override
    public void iniciarTransaccion() throws DAOException {

    }

    @Override
    public void finalizarTransaccion() throws DAOException {

    }
}
