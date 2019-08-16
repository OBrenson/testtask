package com.haulmont.testtask.views.modalwindows;

import com.haulmont.testtask.DAO.DoctorDAO;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.exceptions.AbsenceOfChangeException;
import com.vaadin.ui.*;

public class DoctorUpdateWindow extends Window {

    public DoctorUpdateWindow(Doctor doctor){
        super("Редактирование записи о докторе");

        setModal(true);

        HorizontalLayout fieldsLay = new HorizontalLayout();
        fieldsLay.setMargin(true);

        TextField nameField = new TextField("Имя");
        nameField.setValue(doctor.getName());

        TextField surnameField = new TextField("Фамилия");
        surnameField.setValue(doctor.getSurname());

        TextField patronField = new TextField("Отчество");
        patronField.setValue(doctor.getPatronymic());

        TextField specField = new TextField("Специлизация");
        specField.setValue(doctor.getSpecialization());

        fieldsLay.addComponents(nameField, surnameField, patronField, specField);

        Button okBut = new Button("OK");
        okBut.addClickListener(e->{
            try {
                DoctorDAO.updateDoctor(doctor.getId(), nameField.getValue(), surnameField.getValue(), patronField.getValue(),
                        specField.getValue());
                close();
            } catch (AbsenceOfChangeException ex) {
                ex.printStackTrace();
            }
        });

        Button cancelBut = new Button("Отмена");
        cancelBut.addClickListener(e->{
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
