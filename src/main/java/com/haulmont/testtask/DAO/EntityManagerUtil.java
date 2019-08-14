package com.haulmont.testtask.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {

    public static final String PERSISTENCE_UNIT_NAME = "TestDB";

    private static EntityManager entityManager;

    private EntityManagerUtil() {
    }
    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

            return emFactory.createEntityManager();
        }
        return entityManager;
    }
}
