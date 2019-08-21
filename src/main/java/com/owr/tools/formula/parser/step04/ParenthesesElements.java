package com.owr.tools.formula.parser.step04;

import com.owr.tools.formula.parser.elements.EnclosingParentheses;
import com.owr.tools.formula.parser.elements.ParsedElement;
import com.owr.tools.formula.parser.elements.ParsedElementType;
import com.owr.tools.formula.parser.elements.WithSign;
import com.owr.tools.formula.parser.utils.ElementsUtil;

import java.util.List;

public class ParenthesesElements implements ParsedElement<List<ParsedElement>>, EnclosingParentheses, WithSign {

    private List<ParsedElement> value;
    private boolean sign = true;

    public ParenthesesElements(List<ParsedElement> parameters) {
        this.value = parameters;
    }

    @Override
    public ParsedElementType getType() {
        return ParsedElementType.PARENTHESES;
    }

    @Override
    public List<ParsedElement> getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "(" + ElementsUtil.listToString(value) + ')';
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
