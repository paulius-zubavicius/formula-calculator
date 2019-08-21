package com.owr.tools.formula.evaluation.output;

import com.owr.tools.formula.parser.elements.ParsedElement;
import com.owr.tools.formula.parser.step.ParserStep;

import java.util.List;

public class ToJavaMethod implements ParserStep<List<ParsedElement>, String> {
    private String formulaName;


    public ToJavaMethod(String formulaName) {
        this.formulaName = formulaName;
    }

    @Override
    public String process(List<ParsedElement> data) {
        return null;
    }
}
