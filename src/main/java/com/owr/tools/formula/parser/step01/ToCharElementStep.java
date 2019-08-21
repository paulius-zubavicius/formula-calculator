package com.owr.tools.formula.parser.step01;

import com.owr.tools.formula.parser.step.ParserStep;
import com.owr.tools.formula.parser.step.ValidateAfterStep;
import com.owr.tools.formula.parser.step.ValidateBeforeStep;
import com.owr.tools.formula.parser.validate.ValidationException;

import java.util.List;
import java.util.stream.Collectors;

public class ToCharElementStep implements ParserStep<String, List<CharElement>> , ValidateBeforeStep<String>, ValidateAfterStep<List<CharElement>> {

    @Override
    public void validateBefore(String data) {
        if (data == null || data.isBlank()) throw new ValidationException("No data");


        //FIXME check invalid spaces: ABC 1
        data = data.replaceAll("\\s", " ");


        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == ' ') {
                if (isItInTheMiddle(data, i)) {
                    if (couldItBeOperand(data, i - 1) && couldItBeOperand(data, i + 1)) {

                    }
                }
            }

        }

    }

    private boolean isItInTheMiddle(String data, int i) {
        return i > 0 && i < (data.length() - 1);
    }

    @Override
    public List<CharElement> process(String data) {

        //FIXME invalid white spaces ignored

        return data
                .replaceAll("\\s", "")
                .chars()
                .mapToObj(CharElement::new)
                .collect(Collectors.toList());
    }

    @Override
    public void validateAfter(List<CharElement> processedData) {

        /*Brackets check*/

        int sum = processedData.stream()
                .filter(e -> CharElementType.BRACKET.equals(e.getType()))
                .mapToInt(e -> (e.getValue() == '(' ? 1 : -1))
                .reduce(0, (s, i) -> {
                    if (s < 0) throw new ValidationException("Bracket not opened");
                    return s + i;
                });

        if (sum > 0) {
            throw new ValidationException("Bracket not closed");
        }

        /*Not valid symbols*/

        if (processedData.stream().filter(e -> CharElementType.ELSE.equals(e.getType())).count() > 0) {
            throw new ValidationException("Unrecognized symbols");
        }


    }
}
