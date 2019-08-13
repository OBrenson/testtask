package com.haulmont.testtask.DAO;

import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.exceptions.AbsenceOfChangeException;
import com.haulmont.testtask.exceptions.SelectNullReturnException;

import javax.persistence.EntityManager;
import java.util.List;


public class DoctorDAO {

    private static EntityManager em = EntityManagerUtil.getEntityManager();

    public static void insertDoctor(Doctor doctor) {
        em.getTransaction().begin();
        em.persist(doctor);
        em.getTransaction().commit();
    }

    public static List<Doctor> selectDoctorByFIO(String name, String surname, String patronymic) throws SelectNullReturnException {
        List<Doctor> doctors = em.createNativeQuery("SELECT (*) FROM Doctor WHERE " +
                "(name=:doc_name AND surname=:doc_surname AND patronymic=:doc_patronymic)")
                .setParameter("doc_name", name)
                .setParameter("doc_surname", surname)
                .setParameter("doc_patronymic", patronymic)
                .getResultList();
        if(doctors == null){
            throw  new SelectNullReturnException(name+" "+surname+" "+patronymic+" FROM Doctor");
        }
        return doctors;
    }

    public static void updateDoctor(Long id, String newName, String newSurname, String newPatronymic, String specialization) throws AbsenceOfChangeException {
        int numUpdatedRows = em.createNativeQuery("UPDATE Doctor SET name=:n, surname=:s, patronymic=:p, specialization=:spec WHERE id =: i")
                .setParameter("n", newName)
                .setParameter("s", newSurname)
                .setParameter("p", newPatronymic)
                .setParameter("spec", specialization)
                .setParameter("i", id)
                .executeUpdate();

        if(numUpdatedRows == 0){
            throw new AbsenceOfChangeException("UPDATE");
        }
    }

    public static List<Doctor> selectAllDoctors() throws SelectNullReturnException {
        List<Doctor> doctors = em.createNativeQuery("SELECT * FROM Doctor ").getResultList();
        if(doctors == null){
            throw new SelectNullReturnException("* FROM Doctor");
        }
        return doctors;
    }

    public static void deleteDoctor(String name, String surname, String patronymic) throws AbsenceOfChangeException {
        int numDeletedRows = em.createNativeQuery("DELETE FROM Doctor WHERE name=:n AND surname=:s AND patronymic=:p")
                .setParameter("n", name)
                .setParameter("s", surname)
                .setParameter("p", patronymic)
                .executeUpdate();

        if(numDeletedRows == 0){
            throw new AbsenceOfChangeException("DELETE");
        }
    }
}
