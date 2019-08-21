package com.owr.tools.formula.parser.elements;

import java.util.Optional;

public enum Operator {
    PLUS('+'), MINUS('-'), DIVISION('/'), MULTIPLY('*'), POW('^'),
    ;

    private char ch;

    Operator(char ch) {
        this.ch = ch;
    }

    public char getCh() {
        return ch;
    }

    public static Optional<Operator> valueOfByChar(char ch) {

        for (Operator o : Operator.values()) {
            if (o.ch == ch) {
                return Optional.of(o);
            }
        }

        return Optional.empty();
    }
}
