
package br.com.cras.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory =
            criarSessionFactory();

    private static SessionFactory criarSessionFactory() {
        try {
            return new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();
        } catch (Throwable erro) {
            System.err.println("Erro ao configurar o Hibernate:");
            erro.printStackTrace();
            throw new ExceptionInInitializerError(erro);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void fechar() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
