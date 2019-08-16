package com.haulmont.testtask.views.modalwindows;

import com.haulmont.testtask.DAO.DoctorDAO;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.exceptions.AbsenceOfChangeException;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

public class DoctorAddWindow extends Window {
    public DoctorAddWindow() {
        super("Редактирование записи о докторе");

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

        TextField specField = new TextField("Специлизация");
        specField.setNullSettingAllowed(false);
        specField.addValidator(new StringLengthValidator("Отчество должно быть более 2 симолов",2,20,true));
        specField.addValidator(new RegexpValidator("[A-z]|[А-я]",false, "Только буквы"));

        fieldsLay.addComponents(nameField, surnameField, patronField, specField);

        Button okBut = new Button("OK");
        okBut.addClickListener(e -> {
            if(nameField.isValid() && surnameField.isValid() && patronField.isValid() && specField.isValid()) {
                DoctorDAO.insertDoctor(new Doctor(nameField.getValue(), surnameField.getValue(), patronField.getValue(),
                        specField.getValue()));
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
