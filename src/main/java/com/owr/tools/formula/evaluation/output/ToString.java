package com.owr.tools.formula.evaluation.output;

import com.owr.tools.formula.parser.elements.ParsedElement;
import com.owr.tools.formula.parser.elements.WithSign;
import com.owr.tools.formula.parser.step.ParserStep;
import com.owr.tools.formula.parser.step04.*;

import java.text.DecimalFormat;
import java.util.List;

public class ToString implements ParserStep<List<ParsedElement>, String> {

    @Override
    public String process(List<ParsedElement> data) {
        return convertListToStr(data, true);
    }

    private String convertListToStr(List<ParsedElement> data, boolean skipFirstSpace) {

        StringBuilder str = new StringBuilder();
        for (ParsedElement e : data) {
            str.append(skipFirstSpace ? "" : " ");
            if (e instanceof WithSign && !(e instanceof NumberElement))
                str.append(((WithSign) e).isSign() ? "" : "-");
            str.append(convertToStr(e));
            skipFirstSpace = false;
        }

        return str.toString();
    }

    private String convertToStr(ParsedElement e) {

        if (e instanceof NumberElement) {
            return new DecimalFormat("#.##").format(((NumberElement) e).getValue());
        }

        if (e instanceof VariableElement) {
            VariableElement ve = (VariableElement) e;
            return ve.getValue();
        }

        if (e instanceof OperatorElement) {
            return "" + ((OperatorElement) e).getValue().getCh();
        }

        if (e instanceof ParenthesesElements) {
            ParenthesesElements pe = (ParenthesesElements) e;
            return "(" + convertListToStr(pe.getValue(), true) + ")";
        }

        if (e instanceof MathFunctionElement) {
            MathFunctionElement me = (MathFunctionElement) e;
            return me.getFunction().toString() + "(" + convertListToStr(me.getValue(), true) + ")";
        }

        throw new RuntimeException();

    }
}
