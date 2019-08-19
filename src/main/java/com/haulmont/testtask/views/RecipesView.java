package com.haulmont.testtask.views;

import com.haulmont.testtask.DAO.DoctorDAO;
import com.haulmont.testtask.DAO.PatientDAO;
import com.haulmont.testtask.DAO.RecipeDAO;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.entities.Recipe;
import com.haulmont.testtask.exceptions.AbsenceOfChangeException;
import com.haulmont.testtask.exceptions.SelectNullReturnException;
import com.haulmont.testtask.interfaces.IHospitalUser;
import com.haulmont.testtask.views.modalwindows.RecipeAddWindow;
import com.haulmont.testtask.views.modalwindows.RecipeUpdateWindow;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.util.Date;
import java.util.List;

public class RecipesView extends VerticalLayout implements View {

    private List<Doctor> doctors;
    private List<Patient> patients;

    public void startView() {
        setSizeFull();

        VerticalLayout left = new VerticalLayout();
        VerticalLayout center = new VerticalLayout();
        VerticalLayout right = new VerticalLayout();

        left.setSizeFull();
        center.setSizeFull();
        right.setSizeFull();

        Table table = createPatientsTable();
        addComponent(table);
        table.setSizeFull();
        table.setPageLength(table.size());

        center.addComponent(createAddButton());

        HorizontalLayout hl = new HorizontalLayout();
        hl.setSizeFull();
        hl.setSizeFull();
        hl.addComponent(left);
        hl.addComponent(center);
        hl.addComponent(right);

        addComponent(hl);

    }

    private Table createPatientsTable() {
        Table table = new Table("Список пациентов");

        table.addContainerProperty("Описание", String.class, "НЕ УКАЗАНО");
        table.addContainerProperty("Пациент", String.class, "НЕ УКАЗАНО");
        table.addContainerProperty("Доктор", String.class, "НЕ УКАЗАНО");
        table.addContainerProperty("Дата создания", Date.class, "НЕ УКАЗАНО");
        table.addContainerProperty("Срок действия(в днях)", Integer.class, "НЕ УКАЗАНО");
        table.addContainerProperty("Приоритет", String.class,"НЕ УКАЗАНО");
        table.addContainerProperty("Кнопка удаления", Button.class, null);
        table.addContainerProperty("Кнопка редактирования", Button.class, null);

        List<Recipe> recipes;
        try {
            doctors = DoctorDAO.selectAllDoctors();
            patients = PatientDAO.selectAllPatients();
            recipes = RecipeDAO.selectAllRecipes();
        } catch (SelectNullReturnException e) {
            e.printStackTrace();
            table.addItem(new Object[]{"НЕТ ЗАПИСЕЙ", " ", " ", null, null, "", null, null}, 0);
            return table;
        }

        int i = 0;
        for (Recipe recipe : recipes) {
            table.addItem(new Object[]{recipe.getDescription(), getFIOById(patients, recipe.getPatientId()),
                    getFIOById(doctors, recipe.getDoctorId()),
                    recipe.getDateRecipeCreation(),
                    recipe.getValidityDays(), recipe.getPriority(), createDeleteButton(recipe.getId()),
                    createUpdateButton(recipe, doctors, patients)
            }, i);
            i++;
        }

        return table;
    }

    private String getFIOById(List list, long id) {
        String FIO = "";
        for(IHospitalUser user : (List<IHospitalUser>)list){
            if(user.getId() == id){
                FIO = user.getSurname() + " " + user.getName() + " " + user.getPatronymic();
            }
        }
        return FIO;
    }

    private Button createDeleteButton(Long id) {
        Button button = new Button("Удалить");
        button.addClickListener(e ->
        {
            try {
                RecipeDAO.deleteRecipe(id);
                refreshView();
            } catch (AbsenceOfChangeException ex) {
                ex.printStackTrace();
            }
        });
        button.setSizeFull();
        button.setHeight("45px");
        return button;
    }

    private Button createUpdateButton(Recipe recipe, List<Doctor> doctors, List<Patient> patients) {
        Button button = new Button("Редактировать");
        RecipeUpdateWindow window = new RecipeUpdateWindow(recipe, doctors, patients);
        window.addCloseListener(new Window.CloseListener() {
            @Override
            public void windowClose(Window.CloseEvent closeEvent) {
                refreshView();
            }
        });

        button.addClickListener(e ->
        {
            UI.getCurrent().addWindow(window);
        });
        button.setSizeFull();
        button.setHeight("45px");
        return button;
    }

    private Button createAddButton() {
        Button button = new Button("Добавить");
        RecipeAddWindow window = new RecipeAddWindow(doctors, patients);
        window.addCloseListener(new Window.CloseListener() {
            @Override
            public void windowClose(Window.CloseEvent closeEvent) {
                refreshView();
            }
        });

        button.addClickListener(e -> {
            UI.getCurrent().addWindow(window);
        });
        return button;
    }

    private void refreshView() {
        removeAllComponents();
        startView();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        refreshView();
    }
}
