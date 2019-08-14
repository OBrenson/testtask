package com.haulmont.testtask.UI;

import com.haulmont.testtask.DAO.DoctorDAO;
import com.haulmont.testtask.entities.Doctor;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {
    private static final String DOCTORS = "doctors";

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);

        layout.addComponent(new Label("Main UI"));

        setContent(layout);

        Doctor doc = new Doctor();
        doc.setName("Oleg");
        doc.setSurname("Brenev");
        doc.setPatronymic("Igorevich");
        doc.setSpecialization("Proctolog");
        DoctorDAO.insertDoctor(doc);

        Navigator navigator = new Navigator(this, this);
        navigator.addView(DOCTORS, new DoctorsView());
        navigator.navigateTo(DOCTORS);
    }
}