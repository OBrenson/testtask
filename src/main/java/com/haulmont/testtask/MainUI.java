package com.haulmont.testtask;

import com.haulmont.testtask.views.DoctorsView;
import com.haulmont.testtask.views.PatientsView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {
    public static final String DOCTORS = "doctors";
    public static final String PATIENTS = "patients";
    public static final String RECIPES = "recipes";

    private Navigator navigator = new Navigator(this, this);

    @Override
    protected void init(VaadinRequest request) {
        navigator.addView(DOCTORS, new DoctorsView());
        navigator.addView(PATIENTS, new PatientsView());
        navigator.navigateTo(DOCTORS);
    }
}