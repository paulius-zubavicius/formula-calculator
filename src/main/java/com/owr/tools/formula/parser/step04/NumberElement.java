package com.owr.tools.formula.parser.step04;

import com.owr.tools.formula.parser.elements.ParsedElement;
import com.owr.tools.formula.parser.elements.ParsedElementType;
import com.owr.tools.formula.parser.elements.WithSign;

public class NumberElement implements ParsedElement<Double>, WithSign {

    private Double number;

    public NumberElement(String number) {

        // Depends on locale
        this.number = Double.parseDouble(number);
    }

    @Override
    public ParsedElementType getType() {
        return ParsedElementType.NUMBER;
    }

    @Override
    public Double getValue() {
        return number;
    }

    @Override
    public String toString() {
        return "[" + number + "]";
    }


    @Override
    public boolean isSign() {
        return number > 0;
    }

    @Override
    public void changeSign() {
        number = -number;
    }
}
