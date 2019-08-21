package com.owr.tools.formula.evaluation.output;

import com.owr.tools.formula.parser.step.ParserStep;

import java.util.List;
import java.util.stream.Collectors;

@Deprecated
public class ToStringStep implements ParserStep<List<? extends Object>, String> {


    @Override
    public String process(List<? extends Object> data) {
        return data.stream().map(e -> e.toString()).collect(Collectors.joining("\n"));
    }


}
