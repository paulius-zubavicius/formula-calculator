package com.owr.tools.formula.parser.elements;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

public enum MathFunction {

    SIN(Math::sin, "sin"), COS(Math::cos, "cos");


    private int paramsCount = 0;
    private DoubleUnaryOperator f1;
    private DoubleBinaryOperator f2;
    private List<String> name;

    MathFunction(DoubleUnaryOperator f, String... name) {
        this.name = Arrays.asList(name);
        this.f1 = f;
        paramsCount = 1;
    }

    MathFunction(DoubleBinaryOperator f, String... name) {
        this.name = Arrays.asList(name);
        this.f2 = f;
        paramsCount = 2;
    }

    public int getParamsCount() {
        return paramsCount;
    }

    public static Optional<MathFunction> valueOfByName(String name) {

        for (MathFunction f : MathFunction.values()) {
            if (f.name.contains(name.toLowerCase())) {
                return Optional.of(f);
            }
        }
        return Optional.empty();
    }


    public DoubleUnaryOperator getUnary() {
        return f1;
    }

    public DoubleBinaryOperator getBinary() {
        return f2;
    }

}
