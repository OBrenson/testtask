package com.haulmont.testtask.UI;

import com.haulmont.testtask.DAO.DoctorDAO;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.exceptions.AbsenceOfChangeException;
import com.haulmont.testtask.exceptions.SelectNullReturnException;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DoctorsUI extends UI {

    @Override
    public void init(VaadinRequest vaadinRequest) {
        final HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.setSizeFull();
        hLayout.setMargin(true);

        final Label docLabel = new Label("Докторы");
        hLayout.addComponent(docLabel);

        hLayout.addComponent(createDoctorsTable());

    }

    private Table createDoctorsTable() {
        Table table = new Table("Список докторов");

        table.addContainerProperty("Имя", String.class, "НЕ УКАЗАНО");
        table.addContainerProperty("Фамилия", String.class, "НЕ УКАЗАНО");
        table.addContainerProperty("Отчество", String.class, "НЕ УКАЗАНО");
        table.addContainerProperty("Специлизация", String.class, "НЕ УКАЗАНО");
        table.addContainerProperty(" ", Button.class, null);
        table.addContainerProperty(" ", Button.class, null);

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
            });
        }

        return table;
    }

    private Button createDeleteButton(Long id){
        Button button = new Button();
        button.addClickListener(e ->
                {
                    try {
                        DoctorDAO.deleteDoctor(id);
                    } catch (AbsenceOfChangeException ex) {
                        ex.printStackTrace();
                        createDoctorsTable();
                    }
                });
        return button;
    }
}