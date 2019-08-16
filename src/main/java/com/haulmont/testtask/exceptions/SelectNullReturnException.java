package com.haulmont.testtask.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SelectNullReturnException extends Exception{
    private static Logger LOGGER;

    public SelectNullReturnException(String queryParams){
        super("SELECT return NULL");
    }
}
