package com.owr.tools.formula.parser.step04;

import com.owr.tools.formula.parser.elements.Operator;
import com.owr.tools.formula.parser.elements.ParsedElement;
import com.owr.tools.formula.parser.elements.ParsedElementType;

public class OperatorElement implements ParsedElement<Operator> {

    private Operator operator;

    public OperatorElement(String operator) {
        if (operator.length() != 1) throw new RuntimeException("Not a character: " + operator);
        this.operator = Operator.valueOfByChar(operator.charAt(0)).orElseThrow();
    }

    public OperatorElement(Operator operator) {
        this.operator = operator;
    }

    @Override
    public Operator getValue() {
        return operator;
    }

    public void setValue(Operator operator) {
        this.operator = operator;
    }

    @Override
    public ParsedElementType getType() {
        return ParsedElementType.OPERATOR;
    }

    @Override
    public String toString() {
        return "[" + operator + "]";
    }


}
