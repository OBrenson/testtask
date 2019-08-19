package com.haulmont.testtask.views;

import com.haulmont.testtask.DAO.DoctorDAO;
import com.haulmont.testtask.DAO.PatientDAO;
import com.haulmont.testtask.MainUI;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.exceptions.AbsenceOfChangeException;
import com.haulmont.testtask.exceptions.SelectNullReturnException;
import com.haulmont.testtask.views.modalwindows.DoctorAddWindow;
import com.haulmont.testtask.views.modalwindows.DoctorUpdateWindow;
import com.haulmont.testtask.views.modalwindows.PatientAddWindow;
import com.haulmont.testtask.views.modalwindows.PatientUpdateWindow;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.util.List;

public class PatientsView extends VerticalLayout implements View {
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
        left.addComponent(createButtonNavigRecipe());
        right.addComponent(createButtonNavigDoctor());

        HorizontalLayout hl = new HorizontalLayout();
        hl.setSizeFull();
        hl.setSizeFull();
        hl.addComponent(left);
        hl.addComponent(center);
        hl.addComponent(right);

        addComponent(hl);
    }

    private Button createButtonNavigRecipe(){
        Button navigRecipe = new Button("Рецепты");
        navigRecipe.addClickListener(e->{
            UI.getCurrent().getNavigator().navigateTo(MainUI.RECIPES);
        });
        return navigRecipe;
    }

    private Button createButtonNavigDoctor(){
        Button navigDoctor = new Button("Доктора");
        navigDoctor.addClickListener(e->{
            UI.getCurrent().getNavigator().navigateTo(MainUI.DOCTORS);
        });
        return navigDoctor;
    }

    private Table createPatientsTable() {
        Table table = new Table("Список пациентов");

        table.addContainerProperty("Имя", String.class, "НЕ УКАЗАНО");
        table.addContainerProperty("Фамилия", String.class, "НЕ УКАЗАНО");
        table.addContainerProperty("Отчество", String.class, "НЕ УКАЗАНО");
        table.addContainerProperty("Телефон", String.class, "НЕ УКАЗАНО");
        table.addContainerProperty("Кнопка удаления", Button.class, null);
        table.addContainerProperty("Кнопка редактирования", Button.class, null);

        List<Patient> patients;
        try {
            patients = PatientDAO.selectAllPatients();
        } catch (SelectNullReturnException e) {
            e.printStackTrace();
            table.addItem(new Object[]{"НЕТ ЗАПИСЕЙ", " ", " ", " ", null, null}, 0);
            return table;
        }

        int i = 0;
        for (Patient patient: patients) {
            table.addItem(new Object[]{patient.getName(), patient.getSurname(),
                    patient.getPatronymic(), patient.getTelephone(),
                    createDeleteButton(patient.getId()), createUpdateButton(patient)
            }, i);
            i++;
        }

        return table;
    }

    private Button createDeleteButton(Long id) {
        Button button = new Button("Удалить");
        button.addClickListener(e ->
        {
            try {
                PatientDAO.deletePatient(id);
                refreshView();
            } catch (AbsenceOfChangeException ex) {
                ex.printStackTrace();
            }
        });
        button.setSizeFull();
        button.setHeight("45px");
        return button;
    }

    private Button createUpdateButton(Patient patient) {
        Button button = new Button("Редактировать");
        PatientUpdateWindow window = new PatientUpdateWindow(patient);
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

    private Button createAddButton(){
        Button button = new Button("Добавить");
        PatientAddWindow window = new PatientAddWindow();
        window.addCloseListener(new Window.CloseListener() {
            @Override
            public void windowClose(Window.CloseEvent closeEvent) {
                refreshView();
            }
        });

        button.addClickListener(e->{
            UI.getCurrent().addWindow(window);
        });
        return button;
    }

    private void refreshView(){
        removeAllComponents();
        startView();
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        refreshView();
    }
}
