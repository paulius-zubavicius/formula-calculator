package com.owr.tools.formula.parser.step04;

import com.owr.tools.formula.parser.elements.ParsedElement;
import com.owr.tools.formula.parser.elements.ParsedElementType;
import com.owr.tools.formula.parser.elements.WithSign;

public class VariableElement implements ParsedElement<String>, WithSign {

    private String var;
    private boolean sign = true;

    public VariableElement(String var) {
        this.var = var;
    }

    @Override
    public String getValue() {
        return var;
    }

    @Override
    public ParsedElementType getType() {
        return ParsedElementType.VARIABLE;
    }

    @Override
    public String toString() {
        return "[" + var + "]";
    }

    @Override
    public boolean isSign() {
        return sign;
    }

    @Override
    public void changeSign() {
        sign = !sign;
    }
}
