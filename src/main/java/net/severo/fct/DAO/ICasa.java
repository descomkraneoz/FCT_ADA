package net.severo.fct.DAO;

import net.severo.fct.POJO.Casa;

import java.util.List;

public interface ICasa {

    void crearCasa(Casa casa)throws DAOException ;

    void eliminarCasa(int idCasa)throws DAOException ;

    List<Casa> obtenerTodasLasCasas()  throws DAOException ;

    Casa obtenerCasaPorID(int id) throws DAOException;

    void finalizar() throws DAOException; //cortar la conexion

    void iniciarTransaccion() throws DAOException;

    void finalizarTransaccion() throws DAOException;
}
