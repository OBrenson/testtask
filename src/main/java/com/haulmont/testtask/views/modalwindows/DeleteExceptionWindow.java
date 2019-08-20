package com.haulmont.testtask.views.modalwindows;

import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class DeleteExceptionWindow extends Window {
    public DeleteExceptionWindow(){
        super("Ошибка");

        setModal(true);
        Button b = new Button("Обратно");
        b.addClickListener(e->{
            close();
        });
        Label l = new Label("Нельзя удалить запись о человеке указанную в рецепте.");
        VerticalLayout vl = new VerticalLayout();
        vl.addComponents(l, b);
        setContent(vl);
    }
}
