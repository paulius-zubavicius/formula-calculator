package com.owr.tools.formula.evaluation;

import com.owr.tools.formula.parser.elements.ParsedElement;
import com.owr.tools.formula.parser.step.ParserStep;
import com.owr.tools.formula.parser.step.ValidateAfterStep;
import com.owr.tools.formula.parser.step.ValidateBeforeStep;
import com.owr.tools.formula.parser.step01.ToCharElementStep;
import com.owr.tools.formula.parser.step02.ToStringElementStep;
import com.owr.tools.formula.parser.step03.GropingByParentheses;
import com.owr.tools.formula.parser.step04.ConvertToParsedElements;
import com.owr.tools.formula.parser.step05.OperatorsProcess;
import com.owr.tools.formula.evaluation.calc.Simplifier;
import com.owr.tools.formula.evaluation.output.ToDouble;
import com.owr.tools.formula.evaluation.output.ToJavaMethod;
import com.owr.tools.formula.evaluation.output.ToJson;
import com.owr.tools.formula.evaluation.output.ToString;

import java.util.List;
import java.util.Optional;

public class FormulaEvaluator {

    //FIXME locale
    //FIXME custom functions pass
    //FIXME pass parameters map

    //FIXME tests

    public String evaluateToString(String data) {
        return executeStep(new ToString(), evaluate(data));
    }

    public Optional<Double> evaluateToDouble(String data) {
        return executeStep(new ToDouble(), evaluate(data));
    }

    public String evaluateToJavaMethod(String data, String formulaName) {
        return executeStep(new ToJavaMethod(formulaName), evaluate(data));
    }

    public String parseToString(String data) {
        return executeStep(new ToString(), parse(data));
    }

    public String parseToJson(String data) {
        return executeStep(new ToJson(), parse(data));
    }


    private List<ParsedElement> evaluate(String data) {
        return executeStep(new Simplifier(), parse(data));
    }

    private List<ParsedElement> parse(String data) {
        return executeStep(new OperatorsProcess(), executeStep(new ConvertToParsedElements(), executeStep(new GropingByParentheses(), executeStep(new ToStringElementStep(), executeStep(new ToCharElementStep(), data)))));
    }


    private <D, R> R executeStep(ParserStep<D, R> parser, D data) {
        if (parser instanceof ValidateBeforeStep) ((ValidateBeforeStep<D>) parser).validateBefore(data);
        R result = parser.process(data);
        if (parser instanceof ValidateAfterStep) ((ValidateAfterStep<R>) parser).validateAfter(result);
        return result;
    }

}
