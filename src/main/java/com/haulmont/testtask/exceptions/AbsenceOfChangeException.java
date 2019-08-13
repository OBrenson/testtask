package com.haulmont.testtask.exceptions;

import java.util.logging.*;

public class AbsenceOfChangeException extends Exception {
    private static Logger LOGGER;
    public AbsenceOfChangeException(String queryName){
        super(queryName);
        LOGGER.log(Level.WARNING, queryName + " query has been executed but rows were not changed");
    }
}
