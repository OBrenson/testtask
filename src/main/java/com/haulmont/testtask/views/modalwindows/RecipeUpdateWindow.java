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

public class RecipeUpdateWindow extends Window {

    public RecipeUpdateWindow(Recipe recipe, List<Doctor> doctors, List<Patient> patients) {
        super("Редактирование записи о рецепте");

        setModal(true);

        HorizontalLayout fieldsLay = new HorizontalLayout();
        fieldsLay.setMargin(true);

        TextField discripField = new TextField("Описание");
        discripField.setValue(recipe.getDescription());
        discripField.setNullSettingAllowed(false);
        discripField.addValidator(new StringLengthValidator("Введите описание", 1, 250, true));

        ListSelect patientFIOList= new ListSelect("Пациенты");
        patientFIOList.addItems(getFIOs(patients));
        patientFIOList.setRows(1);
        patientFIOList.setValue(getFIOById(patients, recipe.getPatientId()));

        ListSelect doctorsFIOList= new ListSelect("Доктора");
        doctorsFIOList.addItems(getFIOs(doctors));
        doctorsFIOList.setRows(1);
        doctorsFIOList.setValue(getFIOById(doctors, recipe.getDoctorId()));

        DateField date = new DateField("Дата создания");
        date.setValue(recipe.getDateRecipeCreation());

        TextField daysField = new TextField("Срок действия(в днях)");
        daysField.setNullSettingAllowed(false);
        daysField.setValue(Integer.toString(recipe.getValidityDays()));
        daysField.addValidator(new StringLengthValidator("Пожалуйста введите число дней", 0, 10, true));
        daysField.addValidator(new RegexpValidator("\\d", false, "Только цифры"));

        ListSelect priorityList = new ListSelect();
        priorityList.addItems(Priorities.NORMAL.toString(), Priorities.CITO.toString(), Priorities.STATIM.toString());
        priorityList.setValue(recipe.getPriority());

        fieldsLay.addComponents(discripField, patientFIOList, doctorsFIOList, date, daysField, priorityList);

        Button okBut = new Button("OK");
        okBut.addClickListener(e -> {
            try {
                if (discripField.isValid() && daysField.isValid()) {
                    RecipeDAO.updateRecipe(new Recipe(discripField.getValue(),patients.get(Integer.parseInt(patientFIOList.getValue().toString().substring(0,1))-1).getId(),
                            doctors.get(Integer.parseInt(doctorsFIOList.getValue().toString().substring(0,1))-1).getId(),
                            date.getValue(), Integer.parseInt(daysField.getValue()), priorityList.getValue().toString()), recipe.getId());
                    close();
                }
            } catch (AbsenceOfChangeException ex) {
                ex.printStackTrace();
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
            FIOs.add(Integer.toString(i) + ". " + user.getSurname()+" "+user.getName()+" "+user.getPatronymic());
            i++;
        }
        return FIOs;
    }

    private String getFIOById(List list, long id) {
        String FIO = "";
        int i = 1;
        for(IHospitalUser user : (List<IHospitalUser>)list){
            if(user.getId() == id){
                FIO = Integer.toString(i) + ". " + user.getSurname() + " " + user.getName() + " " + user.getPatronymic();
            }
            i++;
        }
        return FIO;
    }
}
