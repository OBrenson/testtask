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

    public static void updatePatient(Patient patient) throws AbsenceOfChangeException {
        int numUpdatedRows = em.createNativeQuery("UPDATE Patient SET name=:n, surname=:s, patronymic=:p, telephone=:tel WHERE id =: i")
                .setParameter("n", patient.getName())
                .setParameter("s", patient.getSurname())
                .setParameter("p", patient.getPatronymic())
                .setParameter("tel", patient.getTelephone())
                .setParameter("i", patient.getId())
                .executeUpdate();

        if(numUpdatedRows == 0){
            throw new AbsenceOfChangeException("UPDATE");
        }
    }

    public static List<Patient> selectAllPatients() throws SelectNullReturnException {
        List<Patient> patients = em.createNativeQuery("SELECT * FROM Patient ").getResultList();
        if(patients == null){
            throw new SelectNullReturnException("* FROM patient");
        }
        return patients;
    }

    public static void deletepatient(String name, String surname, String patronymic) throws AbsenceOfChangeException {
        int numDeletedRows = em.createNativeQuery("DELETE FROM Patient WHERE name=:n AND surname=:s AND patronymic=:p")
                .setParameter("n", name)
                .setParameter("s", surname)
                .setParameter("p", patronymic)
                .executeUpdate();

        if(numDeletedRows == 0){
            throw new AbsenceOfChangeException("DELETE");
        }
    }
}

