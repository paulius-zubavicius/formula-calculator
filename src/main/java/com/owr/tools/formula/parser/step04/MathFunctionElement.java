package com.owr.tools.formula.parser.step04;

import com.owr.tools.formula.parser.elements.*;
import com.owr.tools.formula.parser.utils.ElementsUtil;

import java.util.List;

public class MathFunctionElement implements ParsedElement<List<ParsedElement>>, EnclosingFunction , WithSign {

    private MathFunction function;
    private List<ParsedElement> value;
    private boolean sign = true;


    public MathFunctionElement(MathFunction function, List<ParsedElement> value) {
        this.function = function;
        this.value = value;
    }

    @Override
    public List<ParsedElement> getValue() {
        return value;
    }

    @Override
    public ParsedElementType getType() {
        return ParsedElementType.FUNCTION;
    }


    @Override
    public MathFunction getFunction() {
        return function;
    }

    @Override
    public String toString() {
        return function + "(" + ElementsUtil.listToString(value) + ')';
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
