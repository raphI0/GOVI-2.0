package com.sncf.govi.service.exception;

public class ExceptionLectureFichier extends Exception{

    public ExceptionLectureFichier(String message){
        super(message);
    }

    // Un constructeur qui permet de conserver la trace de l'exception d'origine
    public ExceptionLectureFichier(String message, Throwable cause) {
        super(message, cause);
    }

}