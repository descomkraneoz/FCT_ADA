package net.severo.fct.DAO.JDBC;

import net.severo.fct.DAO.DAOException;
import net.severo.fct.DAO.ICasa;
import net.severo.fct.POJO.Casa;
import net.severo.fct.POJO.Confinado;

import java.util.List;

public class CasaJDBC implements ICasa {
    static String mostrarTodasLasCasas = "SELECT * FROM casa;";
    static String getCasa = "SELECT * FROM casa WHERE idCasa=?;";
    static String insertCasa = "INSERT INTO casa(idCasa,tiene_jardin) VALUES (?,?);";
    static String borrarCasa = "DELETE FROM casa WHERE idCasa=?;";


    static String trampaParaOsos = "SET FOREIGN_KEY_CHECKS=0";

    static String asignarCasa = "INSERT INTO confinadocasa(idCasa,idConfinado) VALUES (?,?);";
    //static String borrarMecanicoVehiculo = "DELETE FROM vehiculomecanico WHERE idMecanico = ?;";

    @Override
    public void crearCasa(Casa casa) throws DAOException {

    }

    @Override
    public void eliminarCasa(int idCasa) throws DAOException {

    }

    @Override
    public List<Casa> obtenerTodasLasCasas() throws DAOException {
        return null;
    }

    @Override
    public Casa obtenerCasaPorID(int id) throws DAOException {
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
