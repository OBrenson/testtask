package com.haulmont.testtask.DAO;

import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.exceptions.AbsenceOfChangeException;
import com.haulmont.testtask.exceptions.SelectNullReturnException;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class DoctorDAO {

    private static final EntityManager em = EntityManagerUtil.getEntityManager();

    public static void insertDoctor(Doctor doctor) {
        em.getTransaction().begin();
        em.persist(doctor);
        em.getTransaction().commit();
    }

    public static Doctor selectDoctorByID(Long id) throws SelectNullReturnException {
        Query query = em.createNativeQuery("SELECT (*) FROM Doctor WHERE id=:i", Doctor.class)
                .setParameter("i", id);
        Doctor doctor = (Doctor) query.getSingleResult();
        em.clear();
        if (doctor == null) {
            throw new SelectNullReturnException("id = " + id + " FROM Doctor");
        }
        return doctor;
    }

    public static void updateDoctor(Long id, String newName, String newSurname, String newPatronymic, String specialization) throws AbsenceOfChangeException {
        em.getTransaction().begin();
        int numUpdatedRows = em.createNativeQuery("UPDATE Doctor SET name=:n, surname=:s, patronymic=:p, specialization=:spec WHERE id=:i")
                .setParameter("n", newName)
                .setParameter("s", newSurname)
                .setParameter("p", newPatronymic)
                .setParameter("spec", specialization)
                .setParameter("i", id)
                .executeUpdate();
        em.getTransaction().commit();
        em.clear();
        if (numUpdatedRows == 0) {
            throw new AbsenceOfChangeException("UPDATE");
        }
    }

    public static List<Doctor> selectAllDoctors() throws SelectNullReturnException {
        em.getTransaction().begin();
        List<Doctor> doctors = em.createNativeQuery("SELECT * FROM Doctor ", Doctor.class).getResultList();
        em.getTransaction().commit();
        if (doctors.size() == 0) {
            throw new SelectNullReturnException("* FROM Doctor");
        }
        return doctors;
    }

    public static void deleteDoctor(long id) throws AbsenceOfChangeException {
        try {
            em.getTransaction().begin();
            int numDeletedRows = em.createNativeQuery("DELETE FROM Doctor WHERE id=:i  ", Doctor.class)
                    .setParameter("i", id)
                    .executeUpdate();
            em.getTransaction().commit();
            if (numDeletedRows == 0) {
                throw new AbsenceOfChangeException("DELETE");
            }
        } catch (javax.persistence.PersistenceException ex){
            em.getTransaction().rollback();
            throw new AbsenceOfChangeException("DELETE");
        }
    }

    public static List<Object[]> getNumRecipes() {
        em.getTransaction().begin();
        List<Object[]> num = em.createNativeQuery("SELECT Doctor.name, Doctor.surname, Doctor.patronymic ,Doctor.specialization, Count(Recipe.id) FROM Doctor " +
                "LEFT JOIN Recipe ON Doctor.id = Recipe.doctorId GROUP BY Doctor.id").getResultList();
        em.getTransaction().commit();
        return num;
    }
}
