package com.haulmont.testtask.views.modalwindows;

import com.haulmont.testtask.DAO.DoctorDAO;
import com.haulmont.testtask.DAO.PatientDAO;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Patient;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

public class PatientAddWindow extends Window {
    public PatientAddWindow() {
        super("Добавление записи о пациенте");

        setModal(true);

        HorizontalLayout fieldsLay = new HorizontalLayout();
        fieldsLay.setMargin(true);

        TextField nameField = new TextField("Имя");
        nameField.setNullSettingAllowed(false);
        nameField.addValidator(new StringLengthValidator("Имя должно быть более 2 симолов",2,20,true));
        nameField.addValidator(new RegexpValidator("[A-z]|[А-я]",false, "Только буквы"));

        TextField surnameField = new TextField("Фамилия");
        surnameField.setNullSettingAllowed(false);
        surnameField.addValidator(new StringLengthValidator("Фамилия должна быть более 2 симолов",2,20,true));
        surnameField.addValidator(new RegexpValidator("[A-z]|[А-я]",false, "Только буквы"));

        TextField patronField = new TextField("Отчество");
        patronField.addValidator(new RegexpValidator("[A-z]|[А-я]",false, "Только буквы"));

        TextField telField = new TextField("Телефон");
        telField.setNullSettingAllowed(false);
        telField.setValue("+7");
        telField.addValidator(new StringLengthValidator("Пожалуйста введите номер телефона",0,20,true));
        telField.addValidator(new RegexpValidator("^[+]|[\\d][\\d]",false, "Только буквы"));

        fieldsLay.addComponents(surnameField, nameField, patronField, telField);

        Button okBut = new Button("OK");
        okBut.addClickListener(e -> {
            if(nameField.isValid() && surnameField.isValid() && patronField.isValid() && telField.isValid()) {
                PatientDAO.insertPatient(new Patient(nameField.getValue(), surnameField.getValue(), patronField.getValue(),
                        telField.getValue()));
                close();
            }
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
