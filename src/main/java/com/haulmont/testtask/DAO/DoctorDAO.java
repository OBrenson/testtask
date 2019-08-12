package com.haulmont.testtask.DAO;

import com.haulmont.testtask.entities.Doctor;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


public class DoctorDAO {

    private static EntityManager em = EntityManagerUtil.getEntityManager();
    public static void insertDoctor(Doctor doctor){
        em.getTransaction().begin();
        em.persist(doctor);
        em.getTransaction().commit();
    }

    public static List<Doctor> selectDoctorByFIO(String name, String surname, String patronymic){
        Query query = em.createNativeQuery("SELECT (*) FROM Doctor WHERE " +
                "(name=:doc_name AND surname=:doc_surname AND patronymic=:doc_patronymic)");
        query.setParameter("doc_name", name);
        query.setParameter("doc_surname", surname);
        query.setParameter("doc_patronymic", patronymic);
        return (List<Doctor>)query.getResultList();
    }
}
