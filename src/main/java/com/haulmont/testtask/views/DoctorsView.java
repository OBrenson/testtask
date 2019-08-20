package com.haulmont.testtask.views;

import com.haulmont.testtask.DAO.DoctorDAO;
import com.haulmont.testtask.MainUI;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.exceptions.AbsenceOfChangeException;
import com.haulmont.testtask.exceptions.SelectNullReturnException;
import com.haulmont.testtask.views.modalwindows.DeleteExceptionWindow;
import com.haulmont.testtask.views.modalwindows.DoctorAddWindow;
import com.haulmont.testtask.views.modalwindows.DoctorStatisticWindow;
import com.haulmont.testtask.views.modalwindows.DoctorUpdateWindow;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.util.List;

public class DoctorsView extends VerticalLayout implements View {

    public void startView() {

        DoctorDAO.getNumRecipes();
        setSizeFull();

        VerticalLayout left = new VerticalLayout();
        VerticalLayout center = new VerticalLayout();
        VerticalLayout right = new VerticalLayout();

        left.setSizeFull();
        right.setSizeFull();

        Table table = createDoctorsTable();
        addComponent(table);
        table.setSizeFull();
        table.setPageLength(table.size());


        center.addComponent(createAddButton());
        center.addComponent(createStatisticButton());

        left.addComponent(createButtonGoPatient());
        right.addComponent(createButtonNavigRecipe());

        HorizontalLayout hl = new HorizontalLayout();
        hl.setSizeFull();
        hl.setSizeFull();
        hl.addComponent(left);
        hl.addComponent(center);
        hl.addComponent(right);

        addComponent(hl);
    }

    private Button createButtonGoPatient(){
        Button goPatient = new Button("Пациенты");
        goPatient.addClickListener(e -> {
            UI.getCurrent().getNavigator().navigateTo(MainUI.PATIENTS);
        });
        return goPatient;
    }

    private Button createStatisticButton(){
        Button statisticBut = new Button("Показать статистику врачей");
        statisticBut.addClickListener(e->{
            UI.getCurrent().addWindow(new DoctorStatisticWindow());
        });
        return statisticBut;
    }

    private Table createDoctorsTable() {
        Table table = new Table("Список докторов");

        table.addContainerProperty("Имя", String.class, "НЕ УКАЗАНО");
        table.addContainerProperty("Фамилия", String.class, "НЕ УКАЗАНО");
        table.addContainerProperty("Отчество", String.class, "НЕ УКАЗАНО");
        table.addContainerProperty("Специлизация", String.class, "НЕ УКАЗАНО");
        table.addContainerProperty("Кнопка удаления", Button.class, null);
        table.addContainerProperty("Кнопка редактирования", Button.class, null);

        List<Doctor> doctors;
        try {
            doctors = DoctorDAO.selectAllDoctors();
        } catch (SelectNullReturnException e) {
            e.printStackTrace();
            table.addItem(new Object[]{"НЕТ ЗАПИСЕЙ", " ", " ", " ", null, null}, 0);
            return table;
        }

        int i = 0;
        for (Doctor doctor : doctors) {
            table.addItem(new Object[]{doctor.getName(), doctor.getSurname(),
                    doctor.getPatronymic(), doctor.getSpecialization(),
                    createDeleteButton(doctor.getId()), createUpdateButton(doctor)
            }, i);
            i++;
        }

        return table;
    }

    private Button createButtonNavigRecipe(){
        Button navigRecipe = new Button("Рецепты");
        navigRecipe.addClickListener(e->{
            UI.getCurrent().getNavigator().navigateTo(MainUI.RECIPES);
        });
        return navigRecipe;
    }

    private Button createDeleteButton(Long id) {
        Button button = new Button("Удалить");
        button.addClickListener(e ->
        {
            try {
                DoctorDAO.deleteDoctor(id);
                refreshView();
            } catch (AbsenceOfChangeException ex) {
                ex.printStackTrace();
                UI.getCurrent().addWindow(new DeleteExceptionWindow());
            }
        });
        button.setSizeFull();
        button.setHeight("45px");
        return button;
    }

    private Button createUpdateButton(Doctor doctor) {
        Button button = new Button("Редактировать");
        DoctorUpdateWindow window = new DoctorUpdateWindow(doctor);
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
        DoctorAddWindow window = new DoctorAddWindow();
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