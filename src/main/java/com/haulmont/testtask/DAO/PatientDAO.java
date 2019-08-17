package com.haulmont.testtask.DAO;

import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.exceptions.AbsenceOfChangeException;
import com.haulmont.testtask.exceptions.SelectNullReturnException;

import javax.persistence.EntityManager;
import java.util.List;


public class PatientDAO {

    private static EntityManager em = EntityManagerUtil.getEntityManager();

    public static void insertPatient(Patient patient) {
        em.getTransaction().begin();
        em.persist(patient);
        em.getTransaction().commit();
    }

    public static List<Patient> selectPatientByFIO(String name, String surname, String patronymic) throws SelectNullReturnException {
        List<Patient> patients = em.createNativeQuery("SELECT (*) FROM Patient WHERE " +
                "(name=:pat_name AND surname=:pat_surname AND patronymic=:pat_patronymic)")
                .setParameter("pat_name", name)
                .setParameter("pat_surname", surname)
                .setParameter("pat_patronymic", patronymic)
                .getResultList();
        if(patients == null){
            throw  new SelectNullReturnException(name+" "+surname+" "+patronymic+" FROM Patient");
        }
        return patients;
    }

    public static void updatePatient(long id, String name, String surname, String patronymic, String telephone) throws AbsenceOfChangeException {
        em.getTransaction().begin();
        int numUpdatedRows = em.createNativeQuery("UPDATE Patient SET name=:n, surname=:s, patronymic=:p, telephone=:tel WHERE id=:i")
                .setParameter("n", name)
                .setParameter("s", surname)
                .setParameter("p", patronymic)
                .setParameter("tel", telephone)
                .setParameter("i", id)
                .executeUpdate();
        em.getTransaction().commit();
        em.clear();

        if(numUpdatedRows == 0){
            throw new AbsenceOfChangeException("UPDATE");
        }
    }

    public static List<Patient> selectAllPatients() throws SelectNullReturnException {
        List<Patient> patients = em.createNativeQuery("SELECT * FROM Patient", Patient.class).getResultList();
        if(patients.size() == 0){
            throw new SelectNullReturnException("* FROM patient");
        }
        return patients;
    }

    public static void deletePatient(Long id) throws AbsenceOfChangeException {
        em.getTransaction().begin();
        int numDeletedRows = em.createNativeQuery("DELETE FROM Patient WHERE id=:i")
                .setParameter("i", id)
                .executeUpdate();
        em.getTransaction().commit();

        if(numDeletedRows == 0){
            throw new AbsenceOfChangeException("DELETE");
        }
    }
}

