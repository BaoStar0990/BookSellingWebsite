package database;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;




public class DBUtil {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bookstorePU");

    public static EntityManagerFactory getEmFactory() {
        return emf;
    }
}


