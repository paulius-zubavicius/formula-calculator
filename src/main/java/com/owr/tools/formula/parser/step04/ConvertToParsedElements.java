package com.owr.tools.formula.parser.step04;

import com.owr.tools.formula.parser.elements.MathFunction;
import com.owr.tools.formula.parser.elements.ParsedElement;
import com.owr.tools.formula.parser.step.ParserStep;
import com.owr.tools.formula.parser.step.ValidateBeforeStep;
import com.owr.tools.formula.parser.step02.StringElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.owr.tools.formula.parser.step02.StringElementType.*;

public class ConvertToParsedElements implements ParserStep<List<StringElement>, List<ParsedElement>>, ValidateBeforeStep<List<StringElement>> {

    @Override
    public void validateBefore(List<StringElement> data) {

        // Known functions must have brackets group on left
        for (int i = 0; i < data.size(); i++) {

            if (VARIABLE_FUNCTION.equals(data.get(i).getType())) {
                Optional<MathFunction> mFunctionOpt = MathFunction.valueOfByName(data.get(i).getValue());

                // Known function
                if (mFunctionOpt.isPresent()) {

                    //... next val ...if it exists at all
                    i = (i == data.size() - 1 ? i : i + 1);

                    // is it elements in parentheses?
                    if (!GROUP.equals(data.get(i).getType())) {
                        throw new RuntimeException("Parentheses expected <" + data.get(i).getValue() + ">");
                    }
                }
            }
        }
    }

    @Override
    public List<ParsedElement> process(List<StringElement> data) {
        return convertToParsed(data);
    }


    private List<ParsedElement> convertToParsed(List<StringElement> data) {

        List<ParsedElement> result = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {

            StringElement e = data.get(i);

            if (NUMBER.equals(e.getType())) result.add(new NumberElement(e.getValue()));
            else if (OPERATOR.equals(e.getType())) result.add(new OperatorElement(e.getValue()));
            else if (GROUP.equals(e.getType())) result.add(new ParenthesesElements(convertToParsed(e.getChild())));
            else if (VARIABLE_FUNCTION.equals(e.getType())) {
                Optional<MathFunction> mFunctionOpt = MathFunction.valueOfByName(e.getValue());

                if (mFunctionOpt.isPresent()) {

                    i++; // Must be GROUP with child

                    result.add(new MathFunctionElement(mFunctionOpt.get(), convertToParsed(data.get(i).getChild())));

                    e = data.get(i);
                } else {
                    result.add(new VariableElement(e.getValue()));
                }
            }
        }

        return result;
    }

}
