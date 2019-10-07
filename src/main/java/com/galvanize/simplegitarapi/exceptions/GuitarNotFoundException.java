package com.galvanize.simplegitarapi.exceptions;

public class GuitarNotFoundException  extends RuntimeException {
    public GuitarNotFoundException(String message) {
        super(message);
    }

    public GuitarNotFoundException() {
        super("erorr");
    }

}
