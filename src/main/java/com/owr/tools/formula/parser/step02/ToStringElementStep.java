package com.owr.tools.formula.parser.step02;

import com.owr.tools.formula.parser.step.ParserStep;
import com.owr.tools.formula.parser.step.ValidateBeforeStep;
import com.owr.tools.formula.parser.step01.CharElement;
import com.owr.tools.formula.parser.step01.CharElementType;

import java.util.ArrayList;
import java.util.List;

public class ToStringElementStep implements ParserStep<List<CharElement>, List<StringElement>>, ValidateBeforeStep<List<CharElement>> {
    @Override
    public void validateBefore(List<CharElement> data) {
        if (data.isEmpty()) throw new RuntimeException("No formula elements");
    }

    @Override
    public List<StringElement> process(List<CharElement> data) {


        //FIXME defect: ABC1 -> ABC 1

        CharElementType t = data.get(0).getType();
        String buff = "";
        List<StringElement> result = new ArrayList<>();

        for (CharElement chEl : data) {

            if (buff != "") {
                if (chEl.getType() != t || singleSymbolElement(chEl.getType())) {
                    result.add(createVarElement(buff, t));
                    t = chEl.getType();
                    buff = "";
                }
            }
            buff += chEl.getValue();
        }

        result.add(createVarElement(buff, t));

        return result;
    }





    private StringElement createVarElement(String buff, CharElementType type) {
        return new StringElement(convertType(type, buff), buff);
    }

    private boolean singleSymbolElement(CharElementType type) {
        switch (type) {
            case OPERATOR:
            case BRACKET:
                return true;
            default:
                return false;
        }
    }


    private StringElementType convertType(CharElementType type, String buff) {
        switch (type) {
            case BRACKET: {
                if ("(".equals(buff)) return StringElementType.BR_OPEN;
                if (")".equals(buff)) return StringElementType.BR_CLOSE;
                throw new RuntimeException("No case for: " + buff);
            }

            case OPERATOR:
                return StringElementType.OPERATOR;
            case DIGIT:
                return StringElementType.NUMBER;
            case ALPHA:
                return StringElementType.VARIABLE_FUNCTION;
            default:
                throw new RuntimeException("No case for: " + type);
        }
    }


}
