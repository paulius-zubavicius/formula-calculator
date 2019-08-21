package com.owr.tools.formula.parser.elements;

public interface RawElement<V, T> {

    V getValue();

    T getType();
}
