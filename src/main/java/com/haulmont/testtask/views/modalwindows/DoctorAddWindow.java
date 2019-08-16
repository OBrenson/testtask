package com.haulmont.testtask.views.modalwindows;

import com.haulmont.testtask.DAO.DoctorDAO;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.exceptions.AbsenceOfChangeException;
import com.vaadin.ui.*;

public class DoctorAddWindow extends Window {
    public DoctorAddWindow() {
        super("Редактирование записи о докторе");

        setModal(true);

        HorizontalLayout fieldsLay = new HorizontalLayout();
        fieldsLay.setMargin(true);

        TextField nameField = new TextField("Имя");
        TextField surnameField = new TextField("Фамилия");
        TextField patronField = new TextField("Отчество");
        TextField specField = new TextField("Специлизация");

        fieldsLay.addComponents(nameField, surnameField, patronField, specField);

        Button okBut = new Button("OK");
        okBut.addClickListener(e -> {
            DoctorDAO.insertDoctor(new Doctor(nameField.getValue(), surnameField.getValue(), patronField.getValue(),
                    specField.getValue()));
            close();
        });

        Button cancelBut = new Button("Отмена");
        cancelBut.addClickListener(e -> {
            close();
        });
        HorizontalLayout butLay = new HorizontalLayout();
        butLay.addComponent(cancelBut);
        butLay.addComponent(okBut);

        VerticalLayout vl = new VerticalLayout();
        vl.addComponents(fieldsLay, butLay);
        setContent(vl);

        setHeight("250px");
        setWidth("800px");

        center();
    }
}
