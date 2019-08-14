package com.haulmont.testtask.DAO;

import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.exceptions.AbsenceOfChangeException;
import com.haulmont.testtask.exceptions.SelectNullReturnException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;


public class DoctorDAO {

    private static EntityManager em = EntityManagerUtil.getEntityManager();

    public static void insertDoctor(Doctor doctor) {
        em.getTransaction().begin();
        em.persist(doctor);
        em.getTransaction().commit();
    }

    public static List<Doctor> selectDoctorByFIO(String name, String surname, String patronymic) throws SelectNullReturnException {
        Query query = em.createNativeQuery("SELECT (*) FROM Doctor WHERE " +
                "(name=:doc_name AND surname=:doc_surname AND patronymic=:doc_patronymic)")
                .setParameter("doc_name", name)
                .setParameter("doc_surname", surname)
                .setParameter("doc_patronymic", patronymic);

        List<Doctor> doctors = query.getResultList();
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
        List<Doctor> doctors = em.createNativeQuery("SELECT * FROM Doctor ", Doctor.class).getResultList();
        if(doctors == null){
            throw new SelectNullReturnException("* FROM Doctor");
        }
        return (List<Doctor>) doctors;
    }

    private static List<Doctor> transform(List<Object[]> objects){
        ArrayList<Doctor> doctors = new ArrayList<Doctor>();

        for(Object[] obj : objects){
            doctors.add(new Doctor((long)obj[0], String.valueOf(obj[1]), String.valueOf(obj[2]),
                    String.valueOf(obj[3]), String.valueOf(obj[4])));
        }
        return doctors;
    }

    public static void deleteDoctor(long id) throws AbsenceOfChangeException {
        em.getTransaction().begin();
        int numDeletedRows = em.createNativeQuery("DELETE FROM Doctor WHERE id=:i", Doctor.class)
                .setParameter("i", id)
                .executeUpdate();
        em.getTransaction().commit();
        if(numDeletedRows == 0){
            throw new AbsenceOfChangeException("DELETE");
        }
    }
}
