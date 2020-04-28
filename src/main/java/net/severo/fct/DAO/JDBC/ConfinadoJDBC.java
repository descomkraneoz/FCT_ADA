package net.severo.fct.DAO.JDBC;

import net.severo.fct.DAO.DAOException;
import net.severo.fct.DAO.IConfinado;
import net.severo.fct.POJO.Confinado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConfinadoJDBC implements IConfinado {
    static String mostrarTodosLosConfinados = "SELECT * FROM confinado;";
    static String getConfinado = "SELECT * FROM confinado WHERE idConfinado=?;";
    static String insertConfinado = "INSERT INTO confinado(idConfinado,nombre,idCasa) VALUES (?,?,?);";
    static String borrarConfinado = "DELETE FROM confinado WHERE idConfinado=?;";

    public ConfinadoJDBC() throws DAOException {

        //acedemos al singleton ahora por si hay fallos que salten aqui
        ConexionJDBC.getInstance().getConnection();
    }


    @Override
    public void crearNuevoConfinadoDAO(Confinado confinado) throws DAOException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = ConexionJDBC.getInstance().getConnection();

            ps = conn.prepareStatement(insertConfinado);
            ps.setInt(1, confinado.getIdConfinado());
            ps.setString(2, confinado.getNombre());
            ps.setInt(3, confinado.getIdCasa());

            @SuppressWarnings("unused")
            int afectadas = ps.executeUpdate();
            //Este entero no lo vamos a usar pero devuelve el número de filas aceptadas
            //En otras ocasiones nos puede ser útil, aquí siempre debe devolver 1


        } catch (Exception ex) {
            throw new DAOException("Ha habido un problema al insertar el confinado en la base de datos: ", ex);
        } finally {
            try {
                ps.close();

            } catch (SQLException sqlex) {
                throw new DAOException("Error al cerrar la sentencia", sqlex);
            }
        }
    }

    @Override
    public void eliminarConfinadoDAO(int idConfinado) throws DAOException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(borrarConfinado);
            ps.setInt(1, idConfinado);
            ps.executeUpdate();

        } catch (Exception ex) {
            throw new DAOException("Ha habido un problema al eliminar el confinado de la base de datos: ", ex);
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
    public Confinado obtenerUnConfinadoPorID(int id) throws DAOException {
        Confinado j = new Confinado();
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexionJDBC.getInstance().getConnection();

            ps = conn.prepareStatement(getConfinado);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery(); //el string se transforma en una sentencia de la bd, un query, se guarda en rs
            if (!rs.next()) {
                // Nos metemos aquí si la consulta no devuelve nada
                return null;
            }

            int idConfi = rs.getInt("idConfinado");
            String nom = rs.getString("nombre");
            int idCasa = rs.getInt("idCasa");

            j.setIdConfinado(id);
            j.setNombre(nom);
            j.setIdCasa(idCasa);
            return j;

        } catch (Exception e) {
            throw new DAOException("Ha habido un problema al obtener el confinado por id de la base de datos: ", e);
        } finally {
            try {
                ps.close();

            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar la base de datos", ex);
            }
        }
    }

    @Override
    public List<Confinado> obtenerTodosLosConfinados() throws DAOException {
        List<Confinado> confinados = new ArrayList<Confinado>();
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexionJDBC.getInstance().getConnection();

            ps = conn.prepareStatement(mostrarTodosLosConfinados);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Confinado j = new Confinado();
                int codConfinado = rs.getInt("idConfinado");
                String nombre = rs.getString("nombre");
                int codCasa = rs.getInt("idCasa");

                j.setIdConfinado(codConfinado);
                j.setNombre(nombre);
                j.setIdCasa(codCasa);

                confinados.add(j);

            }
            return confinados;


        } catch (Exception e) {
            throw new DAOException("Ha habido un problema al obtener la lista de confinados en la base de datos: ", e);
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
            throw new DAOException("Error al inicair la transaccion", ex);
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
