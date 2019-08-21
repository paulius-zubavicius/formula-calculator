package com.owr.tools.formula.parser.step01;

import com.owr.tools.formula.parser.elements.RawElement;

public class CharElement {

    private CharElementType type;
    private Character value;


    public CharElement(Character value) {
        this.type = CharElementType.valueOfByChar(value);
        this.value =  value;
    }

    public CharElement(int value) {
        this.type = CharElementType.valueOfByChar((char)value);
        this.value = (char) value;
    }

    public CharElementType getType() {
        return type;
    }

    public Character getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value + " : " + type;
    }
}
