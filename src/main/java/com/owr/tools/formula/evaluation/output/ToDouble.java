package com.owr.tools.formula.evaluation.output;

import com.owr.tools.formula.parser.elements.ParsedElement;
import com.owr.tools.formula.parser.step.ParserStep;

import java.util.List;
import java.util.Optional;

public class ToDouble implements ParserStep<List<ParsedElement>, Optional<Double>> {

    @Override
    public Optional<Double> process(List<ParsedElement> data) {
        return null;
    }
}
