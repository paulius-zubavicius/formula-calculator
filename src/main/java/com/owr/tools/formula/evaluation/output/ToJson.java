package com.owr.tools.formula.evaluation.output;

import com.owr.tools.formula.parser.elements.EnclosingParentheses;
import com.owr.tools.formula.parser.elements.ParsedElement;
import com.owr.tools.formula.parser.elements.WithSign;
import com.owr.tools.formula.parser.step.ParserStep;
import com.owr.tools.formula.parser.step04.*;

import java.text.DecimalFormat;
import java.util.List;

public class ToJson implements ParserStep<List<ParsedElement>, String> {
    @Override
    public String process(List<ParsedElement> data) {
        return "[" + convertListToJson(data, true) + "]";
    }

    private String convertListToJson(List<ParsedElement> data, boolean skipFirstComma) {

        StringBuilder str = new StringBuilder();
        for (ParsedElement e : data) {

            str.append(skipFirstComma ? "" : ", ");

            str.append("{");

            str.append("\"type\" : ").append("\"").append(e.getType().toString()).append("\",");

            if (e instanceof WithSign && !(e instanceof NumberElement)){
                WithSign s = (WithSign) e;
                str.append("\"sign\" : \"").append(s.isSign() ? "+": "-").append("\",");
            }

            if (e instanceof MathFunctionElement ){
                MathFunctionElement f = (MathFunctionElement) e;
                str.append("\"function\" : \"").append(f.getFunction().toString()).append("\",");
            }

            str.append("\"value\" : ").append(convertToStr(e));

            str.append("}");

            skipFirstComma = false;
        }

        return str.toString();
    }

    private String convertToStr(ParsedElement e) {

        if (e instanceof NumberElement) {
            return new DecimalFormat("#.##").format(((NumberElement) e).getValue());
        }

        if (e instanceof VariableElement) {
            VariableElement ve = (VariableElement) e;
            return "\"" + (ve.isSign() ? "" : "-") + ve.getValue() + "\"";
        }

        if (e instanceof OperatorElement) {
            return "\"" + ((OperatorElement) e).getValue().getCh() + "\"";
        }

        if (e instanceof EnclosingParentheses) {
            EnclosingParentheses pe = (EnclosingParentheses) e;
            return  "[" + convertListToJson(pe.getValue(), true) + "]";
        }

        throw new RuntimeException();

    }
}
