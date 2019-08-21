package com.owr.tools.formula.parser.step01;

public enum CharElementType {

    BRACKET('(', ')'), OPERATOR('+', '-', '*', '/', '^'), DIGIT('.', ',', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'), ALPHA(), ELSE();


    private char[] chars;

    CharElementType(char... chars) {
        this.chars = chars;
    }


    public static CharElementType valueOfByChar(char chInput) {

        for (CharElementType t : CharElementType.values()) {
            for (char ch : t.chars) {
                if (ch == chInput) {
                    return t;
                }
            }
        }

        if (('A' <= chInput && 'Z' >= chInput) || ('a' <= chInput && 'z' >= chInput)) {
            return ALPHA;
        }

        return ELSE;
    }

}
