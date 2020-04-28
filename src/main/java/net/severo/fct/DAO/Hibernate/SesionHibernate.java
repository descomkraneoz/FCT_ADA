package net.severo.fct.DAO.Hibernate;


import net.severo.fct.DAO.DAOException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class SesionHibernate {
    private static SesionHibernate instancia = null;
    Session sesion = null;
    private SessionFactory sessionFactory;

    private SesionHibernate() throws DAOException {
        try {

            sessionFactory = new Configuration().configure().buildSessionFactory();
            sesion = sessionFactory.openSession();


        } catch (Throwable e) {
            System.err.println("Ha fallado la creación de la factoría de sesiones." + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SesionHibernate getInstance() throws DAOException {
        if (instancia == null) {
            instancia = new SesionHibernate();
        }
        return instancia;
    }

    public Session getSesion() {
        return sesion;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
