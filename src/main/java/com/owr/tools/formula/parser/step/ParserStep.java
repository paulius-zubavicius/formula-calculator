package com.owr.tools.formula.parser.step;

public interface ParserStep<IN, OUT> {
    OUT process(IN data);
}
