package com.haulmont.testtask.views.modalwindows;

import com.haulmont.testtask.DAO.DoctorDAO;
import com.vaadin.ui.*;

import java.math.BigInteger;
import java.util.List;

public class DoctorStatisticWindow extends Window {

    public DoctorStatisticWindow() {
        super("Статистика количества рецептов докторов");
        setModal(true);
        HorizontalLayout fieldsLay = new HorizontalLayout();
        fieldsLay.setMargin(true);

        Button closeBut = new Button("Закрыть");
        closeBut.addClickListener(e->{
            close();
        });

        Table table = createAndFillStatisticTable();
        table.setSizeFull();

        VerticalLayout vl = new VerticalLayout();
        vl.addComponent(table);
        vl.addComponent(closeBut);

        setContent(vl);
    }

    private Table createAndFillStatisticTable(){

        Table table = new Table("Количество рецептов выписанных каждым доктором");

        table.addContainerProperty("Имя", String.class, "НЕ УКАЗАНО");
        table.addContainerProperty("Фамилия", String.class, "НЕ УКАЗАНО");
        table.addContainerProperty("Отчество", String.class, "НЕ УКАЗАНО");
        table.addContainerProperty("Специлизация", String.class, "НЕ УКАЗАНО");
        table.addContainerProperty("Количество рецептов", BigInteger.class, 0);

        int i = 0;
        for(Object[] ob : DoctorDAO.getNumRecipes()){
            table.addItem(new Object[]{ob[0].toString(), ob[1].toString(), ob[2].toString(), ob[3].toString(), (BigInteger)ob[4]}, i);
            i++;
        }
        return table;
    }

}
