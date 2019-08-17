package com.haulmont.testtask.views.modalwindows;

import com.haulmont.testtask.DAO.DoctorDAO;
import com.haulmont.testtask.DAO.PatientDAO;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.exceptions.AbsenceOfChangeException;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

public class PatientUpdateWindow extends Window {

    public PatientUpdateWindow(Patient patient){
        super("Редактирование записи о докторе");

        setModal(true);

        HorizontalLayout fieldsLay = new HorizontalLayout();
        fieldsLay.setMargin(true);

        TextField nameField = new TextField("Имя");
        nameField.setValue(patient.getName());
        nameField.setNullSettingAllowed(false);
        nameField.addValidator(new StringLengthValidator("Имя должно быть более 2 симолов",2,20,true));
        nameField.addValidator(new RegexpValidator("[A-z]|[А-я]",false, "Только буквы"));

        TextField surnameField = new TextField("Фамилия");
        surnameField.setValue(patient.getSurname());
        surnameField.setNullSettingAllowed(false);
        surnameField.addValidator(new StringLengthValidator("Фамилия должна быть более 2 симолов",2,20,true));
        surnameField.addValidator(new RegexpValidator("[A-z]|[А-я]",false, "Только буквы"));

        TextField patronField = new TextField("Отчество");
        patronField.setValue(patient.getPatronymic());
        patronField.addValidator(new RegexpValidator("[A-z]|[А-я]",false, "Только буквы"));

        TextField telField = new TextField("Телефон");
        telField.setNullSettingAllowed(false);
        telField.setValue(patient.getTelephone());
        telField.addValidator(new StringLengthValidator("Пожалуйста введите номер телефона",0,20,true));
        telField.addValidator(new RegexpValidator("^[+]|[\\d][\\d]",false, "Только цифры"));

        fieldsLay.addComponents(surnameField, nameField, patronField, telField);

        Button okBut = new Button("OK");
        okBut.addClickListener(e->{
            try {
                if(nameField.isValid() && surnameField.isValid() && patronField.isValid() && telField.isValid()) {
                    PatientDAO.updatePatient(patient.getId(), nameField.getValue(), surnameField.getValue(), patronField.getValue(),
                            telField.getValue());
                    close();
                }
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
