package net.severo.fct.DAO.JDBC;

import net.severo.fct.DAO.DAOException;
import net.severo.fct.DAO.ICasa;
import net.severo.fct.POJO.Casa;
import net.severo.fct.POJO.Confinado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConexionJDBC.getInstance().getConnection();

            ps = conn.prepareStatement(insertCasa);
            ps.setInt(1, casa.getIdCasa());
            ps.setBoolean(2, casa.getTiene_jardin());


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
                Boolean jardin = rs.getBoolean("tiene_jardin");

                j.setIdCasa(codCasa);
                j.setTiene_jardin(jardin);
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
            Boolean jardin = rs.getBoolean("tiene_jardin");


            j.setIdCasa(idCasa);
            j.setTiene_jardin(jardin);
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
    public void asignarCasaAlConfinado(Casa m, Confinado v) throws DAOException {
        Connection conn = null;
        PreparedStatement ps = null;
        Set<Confinado> confinados = new HashSet<>();

        try {
            conn = ConexionJDBC.getInstance().getConnection();

            ps = conn.prepareStatement(trampaParaOsos);

            ps = conn.prepareStatement(asignarCasa);
            ps.setInt(1, m.getIdCasa());
            ps.setInt(2, v.getIdConfinado());
            confinados.add(v);

            @SuppressWarnings("unused")
            int afectadas = ps.executeUpdate();
            //Este entero no lo vamos a usar pero devuelve el número de filas aceptadas
            //En otras ocasiones nos puede ser útil, aquí siempre debe devolver 1

        } catch (Exception ex) {
            throw new DAOException("Ha habido un problema al insertar el confinado a la casa en la base de datos: ", ex);
        } finally {
            try {
                ps.close();

            } catch (SQLException sqlex) {
                throw new DAOException("Error al cerrar la sentencia", sqlex);
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
