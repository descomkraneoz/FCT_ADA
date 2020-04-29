package net.severo.fct.DAO.Hibernate;

import net.severo.fct.DAO.DAOException;
import net.severo.fct.DAO.ICasa;
import net.severo.fct.POJO.Casa;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CasaHibernate implements ICasa {
    Transaction tx = null;

    @Override
    public void crearCasa(Casa casa) throws DAOException {
        Session sesion = null;
        try {

            sesion = SesionHibernate.getInstance().getSesion();
            sesion.save(casa);

        } catch (Exception e) {
            throw new DAOException("Ha habido un problema al insertar una nueva casa", e);
        }

    }

    @Override
    public void eliminarCasa(int idCasa) throws DAOException {
        try {
            Casa j = this.obtenerCasaPorID(idCasa);
            Session sesion = SesionHibernate.getInstance().getSesion();
            sesion.delete(j);
        } catch (Exception e) {
            throw new DAOException("Ha habido un problema al eliminar la casa", e);
        }
    }

    @Override
    public List<Casa> obtenerTodasLasCasas() throws DAOException {
        try {
            Session sesion = SesionHibernate.getInstance().getSesion();

            // Variable que almacena la lista a devolver
            List<Casa> lista;

            // Hacemos la consulta
            Query q = sesion.createQuery("from Casa");
            lista = q.list();
            for (Casa j : lista) {
                Hibernate.initialize(j.getIdCasa());
            }

            return lista;
        } catch (Exception e) {
            throw new DAOException("Ha habido un problema al obtener las casas", e);
        }
    }

    @Override
    public Casa obtenerCasaPorID(int id) throws DAOException {
        Session sesion = SesionHibernate.getInstance().getSesion();
        try {
            Casa j = new Casa();

            j = (Casa) sesion.get(Casa.class, id);

            return j;

        } catch (Exception e) {
            throw new DAOException("Ha habido un problema al obtener la casa", e);
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
