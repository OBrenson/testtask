package com.haulmont.testtask.UI;

import com.haulmont.testtask.DAO.DoctorDAO;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.exceptions.AbsenceOfChangeException;
import com.haulmont.testtask.exceptions.SelectNullReturnException;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DoctorsView extends VerticalLayout implements View {

    public void startView() {
        setSizeFull();

        final Label docLabel = new Label("Докторы");
        addComponent(docLabel);

        Table table = createDoctorsTable();
        addComponent(table);
        table.setPageLength(table.size());
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
            table.addItem(new Object[]{"НЕТ ЗАПИСЕЙ", " ", " ", " "},1);
            return table;
        }

        for(Doctor doctor : doctors){
            table.addItem(new Object[]{doctor.getName(), doctor.getSurname(),
                    doctor.getPatronymic(), doctor.getSpecialization(),
                    createDeleteButton(doctor.getId()), new Button()
            },0);
        }

        return table;
    }

    private Button createDeleteButton(Long id){
        Button button = new Button();
        button.addClickListener(e ->
                {
                    try {
                        DoctorDAO.deleteDoctor(id);
                        removeAllComponents();
                        startView();
                    } catch (AbsenceOfChangeException ex) {
                        ex.printStackTrace();
                    }
                });
        return button;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        startView();
    }
}