package net.severo.fct.DAO.JDBC;

import net.severo.fct.DAO.DAOException;
import net.severo.fct.DAO.ICasa;
import net.severo.fct.POJO.Casa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CasaJDBC implements ICasa {
    static String mostrarTodasLasCasas = "SELECT * FROM casa;";
    static String getCasa = "SELECT * FROM casa WHERE idCasa=?;";
    static String insertCasa = "INSERT INTO casa(idCasa,tieneJardin) VALUES (?,?);";
    static String borrarCasa = "DELETE FROM casa WHERE idCasa=?;";


    @Override
    public void crearCasa(Casa casa) throws DAOException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConexionJDBC.getInstance().getConnection();

            ps = conn.prepareStatement(insertCasa);
            ps.setInt(1, casa.getIdCasa());
            ps.setBoolean(2, casa.getTieneJardin());


            @SuppressWarnings("unused")
            int afectadas = ps.executeUpdate();
            //Este entero no lo vamos a usar pero devuelve el número de filas aceptadas
            //En otras ocasiones nos puede ser útil, aquí siempre debe devolver 1


        } catch (Exception ex) {
            throw new DAOException("Ha habido un problema al insertar la casa en la base de datos: ", ex);
        } finally {
            try {
                ps.close();

            } catch (SQLException sqlex) {
                throw new DAOException("Error al cerrar la sentencia", sqlex);
            }
        }
    }

    @Override
    public void eliminarCasa(int idCasa) throws DAOException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexionJDBC.getInstance().getConnection();

            ps = conn.prepareStatement(borrarCasa);
            ps.setInt(1, idCasa);

            int afectadas = ps.executeUpdate();


        } catch (Exception ex) {
            throw new DAOException("Ha habido un problema al eliminar la casa de la base de datos: ", ex);
            //ex.printStackTrace();
        } finally {
            try {
                ps.close();
                conn.close();
            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar la base de datos", ex);
            }
        }
    }

    @Override
    public List<Casa> obtenerTodasLasCasas() throws DAOException {
        List<Casa> casas = new ArrayList<Casa>();
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexionJDBC.getInstance().getConnection();

            ps = conn.prepareStatement(mostrarTodasLasCasas);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Casa j = new Casa();
                int codCasa = rs.getInt("idCasa");
                Boolean jardin = rs.getBoolean("tieneJardin");

                j.setIdCasa(codCasa);
                j.setTieneJardin(jardin);
                casas.add(j);
            }
            return casas;
        } catch (Exception e) {
            throw new DAOException("Ha habido un problema al obtener la lista de casas en la base de datos: ", e);
        } finally {
            try {
                ps.close();

            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar la base de datos", ex);
            }
        }
    }

    @Override
    public Casa obtenerCasaPorID(int id) throws DAOException {
        Casa j = new Casa();
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexionJDBC.getInstance().getConnection();

            ps = conn.prepareStatement(getCasa);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery(); //el string se transforma en una sentencia de la bd, un query, se guarda en rs
            if (!rs.next()) {
                // Nos metemos aquí si la consulta no devuelve nada
                return null;
            }

            int idCasa = rs.getInt("idCasa");
            Boolean jardin = rs.getBoolean("tieneJardin");


            j.setIdCasa(idCasa);
            j.setTieneJardin(jardin);
            return j;

        } catch (Exception e) {
            throw new DAOException("Ha habido un problema al obtener la casa de la base de datos: ", e);
        } finally {
            try {
                ps.close();

            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar la base de datos", ex);
            }
        }
    }

    @Override
    public void finalizar() throws DAOException {
        try {
            ConexionJDBC.getInstance().getConnection().close();
        } catch (SQLException ex) {
            throw new DAOException("Error al finalizar la conexion con la base de datos", ex);
        }

    }

    @Override
    public void iniciarTransaccion() throws DAOException {
        try {
            ConexionJDBC.getInstance().getConnection().setAutoCommit(false); //deja en espera a la base de datos para que no haga comit
        } catch (SQLException ex) {
            throw new DAOException("Error al iniciar la transaccion", ex);
        }

    }

    @Override
    public void finalizarTransaccion() throws DAOException {
        try {
            ConexionJDBC.getInstance().getConnection().commit();
        } catch (SQLException ex) {
            try {
                ConexionJDBC.getInstance().getConnection().rollback();
            } catch (SQLException ex1) {
                throw new DAOException("No se ha podido finalizar la transsaccion. Se han desecho los cambios", ex);
            }
        }
    }
}
