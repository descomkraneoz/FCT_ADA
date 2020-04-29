package net.severo.fct.DAO.Hibernate;

import net.severo.fct.DAO.DAOException;
import net.severo.fct.DAO.IConfinado;
import net.severo.fct.POJO.Casa;
import net.severo.fct.POJO.Confinado;
import net.severo.fct.servicio.ServicioCasa;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ConfinadoHibernate implements IConfinado {
    Transaction tx = null;

    @Override
    public void crearNuevoConfinadoDAO(Confinado confinado) throws DAOException {
        Session sesion = null;
        try {

            sesion = SesionHibernate.getInstance().getSesion();
            sesion.save(confinado);

        } catch (Exception e) {
            throw new DAOException("Ha habido un problema al insertar un nuev confinado", e);
        }
    }

    @Override
    public void eliminarConfinadoDAO(int idConfinado) throws DAOException {

        try {
            Confinado j = this.obtenerUnConfinadoPorID(idConfinado);
            Session sesion = SesionHibernate.getInstance().getSesion();

            Query query = sesion.createQuery("delete Confinado where idConfinado = :id");
            query.setParameter("id", idConfinado);
            //sesion.delete(j);
            int result = query.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Ha habido un problema al eliminar el confinado", e);
        }

    }

    @Override
    public Confinado obtenerUnConfinadoPorID(int id) throws DAOException {
        Session sesion = SesionHibernate.getInstance().getSesion();
        try {
            Confinado j = new Confinado();
            j = (Confinado) sesion.get(Confinado.class, id);

            return j;

        } catch (Exception e) {
            throw new DAOException("Ha habido un problema al obtener el confinado con ese id", e);
        }
    }

    @Override
    public List<Confinado> obtenerTodosLosConfinados() throws DAOException {
        try {
            Session sesion = SesionHibernate.getInstance().getSesion();

            // Variable que almacena la lista a devolver
            List<Confinado> lista;
            // Hacemos la consulta
            Query q = sesion.createQuery("from Confinado");
            lista = q.list();
            for (Confinado j : lista) {
                Hibernate.initialize(j.getIdConfinado());
            }

            return lista;
        } catch (Exception e) {
            throw new DAOException("Ha habido un problema al obtener los confinados", e);
        }
    }

    @Override
    public void asignarCasaAlConfinado(int codCasa, int codConfinado) throws DAOException {
        Session sesion = SesionHibernate.getInstance().getSesion();
        try {
            Confinado con = this.obtenerUnConfinadoPorID(codConfinado);
            Casa casa = ServicioCasa.getServicio().servicioObtenerCasaPorID(codCasa);
            con.setCasa(casa);
            sesion.save(con);

        } catch (Exception e) {
            throw new DAOException("Ha habido un problema al asignar el confinado a la casa desde ConfinadoHibernate", e);
        }

    }

    @Override
    public void finalizar() throws DAOException {
        SesionHibernate.getInstance().getSesion().close();
        SesionHibernate.getInstance().getSessionFactory().close();
    }

    @Override
    public void iniciarTransaccion() throws DAOException {
        this.tx = SesionHibernate.getInstance().getSesion().beginTransaction();
    }

    @Override
    public void finalizarTransaccion() throws DAOException {
        this.tx.commit();
    }
}
