package com.haulmont.testtask.views.modalwindows;

import com.haulmont.testtask.DAO.PatientDAO;
import com.haulmont.testtask.DAO.RecipeDAO;
import com.haulmont.testtask.Priorities;
import com.haulmont.testtask.entities.Doctor;
import com.haulmont.testtask.entities.Patient;
import com.haulmont.testtask.entities.Recipe;
import com.haulmont.testtask.exceptions.AbsenceOfChangeException;
import com.haulmont.testtask.interfaces.IHospitalUser;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;

public class RecipeAddWindow extends Window {
    public RecipeAddWindow(List<Doctor> doctors, List<Patient> patients) {
        super("Добавление записи о рецепте");

        setModal(true);

        HorizontalLayout fieldsLay = new HorizontalLayout();
        fieldsLay.setMargin(true);

        TextArea discripArea = new TextArea("Описание");
        discripArea.setNullSettingAllowed(false);
        discripArea.addValidator(new StringLengthValidator("Введите описание", 1, 250, true));

        ListSelect patientFIOList= new ListSelect("Пациенты");
        patientFIOList.addItems(getFIOs(patients));
        patientFIOList.setRows(1);

        ListSelect doctorsFIOList= new ListSelect("Доктора");
        doctorsFIOList.addItems(getFIOs(doctors));
        doctorsFIOList.setRows(1);

        DateField date = new DateField("Дата создания");

        TextField daysField = new TextField("Срок действия(в днях)");
        daysField.setNullSettingAllowed(false);
        daysField.addValidator(new StringLengthValidator("Пожалуйста введите число дней", 0, 10, true));
        daysField.addValidator(new RegexpValidator("\\d", false, "Только цифры"));

        ListSelect priorityList = new ListSelect();
        priorityList.addItems(Priorities.NORMAL.toString(), Priorities.CITO.toString(), Priorities.STATIM.toString());

        fieldsLay.addComponents(discripArea, patientFIOList, doctorsFIOList, date, daysField, priorityList);

        Button okBut = new Button("OK");
        okBut.addClickListener(e -> {
            if (discripArea.isValid() && daysField.isValid()) {
                RecipeDAO.insertRecipe(new Recipe(discripArea.getValue(),patients.get(Integer.parseInt(patientFIOList.getValue().toString().substring(0,1))-1).getId(),
                        doctors.get(Integer.parseInt(doctorsFIOList.getValue().toString().substring(0,1))-1).getId(),
                        date.getValue(), Integer.parseInt(daysField.getValue()), priorityList.getValue().toString()));
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

        setHeight("400px");
        setWidth("1500px");

        center();
    }

    private List<String> getFIOs(List list){
        List<String> FIOs = new ArrayList<String>();
        int i = 1;
        for(IHospitalUser user : (List<IHospitalUser>)list){
            FIOs.add(Integer.toString(i)+ ". " + user.getSurname()+" "+user.getName()+" "+user.getPatronymic());
            i++;
        }
        return FIOs;
    }
}
