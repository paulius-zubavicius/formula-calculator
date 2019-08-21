package com.owr.tools.formula.parser.step02;

import com.owr.tools.formula.parser.elements.RawElement;
import com.owr.tools.formula.parser.utils.ElementsUtil;

import java.util.List;
import java.util.stream.Collectors;

import static com.owr.tools.formula.parser.step02.StringElementType.GROUP;

public class StringElement implements RawElement<String, StringElementType> {


    private StringElementType type;
    private String value;
    private List<StringElement> child;

    public StringElement(StringElementType type, String value) {
        this.type = type;
        this.value = value;
    }

    public StringElement(List<StringElement> child) {
        this(GROUP, null);
        this.child = child;
    }

    @Override
    public StringElementType getType() {
        return type;
    }
    @Override
    public String getValue() {
        return value;
    }


    public List<StringElement> getChild() {
        return child;
    }

    @Override
    public String toString() {

        if (GROUP.equals(type)){
            return "(" +  ElementsUtil.listToString(child) + ")";
        }

        return value + " : " + type;
    }


}
