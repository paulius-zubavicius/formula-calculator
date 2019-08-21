package com.owr.tools.formula.parser.validate;

public class ValidationException extends RuntimeException {

    // TODO with references to original string

    public ValidationException(String msg) {
        super(msg);
    }
}
