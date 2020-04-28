package net.severo.fct.DAO;

import net.severo.fct.POJO.Confinado;

import java.util.List;

public interface IConfinado {

    void crearNuevoConfinadoDAO(Confinado confinado) throws DAOException;

    void eliminarConfinadoDAO(int idConfinado) throws DAOException;

    Confinado obtenerUnConfinadoPorID(int id) throws DAOException;

    List<Confinado> obtenerTodosLosConfinados()  throws DAOException ;


    void finalizar() throws DAOException; //cortar la conexion

    void iniciarTransaccion() throws DAOException;

    void finalizarTransaccion() throws DAOException;
}
