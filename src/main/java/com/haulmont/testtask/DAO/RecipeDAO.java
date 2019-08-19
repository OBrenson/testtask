package com.haulmont.testtask.DAO;

import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.entities.Recipe;
import com.haulmont.testtask.exceptions.AbsenceOfChangeException;
import com.haulmont.testtask.exceptions.SelectNullReturnException;

import javax.persistence.EntityManager;
import java.util.List;

public class RecipeDAO {

    private static final EntityManager em = EntityManagerUtil.getEntityManager();

    public static void insertRecipe(Recipe recipe) {
        em.getTransaction().begin();
        em.persist(recipe);
        em.getTransaction().commit();
    }

    public static List<Recipe> selectAllRecipes() throws SelectNullReturnException {
        List<Recipe> recipes = em.createNativeQuery("SELECT * FROM Recipe ", Recipe.class).getResultList();
        if(recipes.size() == 0){
            throw new SelectNullReturnException("* FROM Recipe");
        }
        em.clear();

        return recipes;
    }

    public static void updateRecipe(Recipe recipe, long id) throws AbsenceOfChangeException {
        em.getTransaction().begin();
        int numChangedRows = em.createNativeQuery("UPDATE Recipe SET description=:des, patientId=:pId, " +
                "doctorId=:dId, dateRecipeCreation=:date, validityDays=:days, priority=:p WHERE id=:i")
                .setParameter("des", recipe.getDescription())
                .setParameter("pId", recipe.getPatientId())
                .setParameter("dId", recipe.getDoctorId())
                .setParameter("date", recipe.getDateRecipeCreation())
                .setParameter("days", recipe.getValidityDays())
                .setParameter("p", recipe.getPriority())
                .setParameter("i", id)
                .executeUpdate();
        em.getTransaction().commit();
        em.clear();

        if(numChangedRows == 0){
            throw  new AbsenceOfChangeException("UPDATE Recipie with id = "+recipe.getId());
        }
    }

    public static void deleteRecipe(Long id) throws AbsenceOfChangeException {
        em.getTransaction().begin();
        int numDeletedRows = em.createNativeQuery("DELETE FROM Recipe WHERE id=:i")
                .setParameter("i", id)
                .executeUpdate();
        em.getTransaction().commit();

        if(numDeletedRows == 0){
            throw new AbsenceOfChangeException("DELETE FROM Recipe WHERE id = " + id);
        }
    }
}
