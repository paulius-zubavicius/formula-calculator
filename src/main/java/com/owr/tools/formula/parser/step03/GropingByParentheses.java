package com.owr.tools.formula.parser.step03;

import com.owr.tools.formula.parser.step.ParserStep;
import com.owr.tools.formula.parser.step.ValidateAfterStep;
import com.owr.tools.formula.parser.step02.StringElement;
import com.owr.tools.formula.parser.step02.StringElementType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.owr.tools.formula.parser.step02.StringElementType.BR_CLOSE;
import static com.owr.tools.formula.parser.step02.StringElementType.BR_OPEN;

public class GropingByParentheses implements ParserStep<List<StringElement>, List<StringElement>>, ValidateAfterStep<List<StringElement>> {

    @Override
    public List<StringElement> process(List<StringElement> data) {

        removeAllEnclosingParentheses(data);

        Optional<Pos> bracketsPos = findParenthesesPos(data);
        while (bracketsPos.isPresent()) {

            StringElement hierarchical = new StringElement(removeAllEnclosingParentheses(copyElements(data, bracketsPos.get())));
            replaceFlatToHierarchical(data, bracketsPos.get(), hierarchical);

            bracketsPos = findParenthesesPos(data);
        }

        return data;
    }

    @Override
    public void validateAfter(List<StringElement> processedData) {
        // No brackets should be left here
        for (StringElement e : processedData) {
            if (BR_OPEN.equals(e.getType()) || BR_CLOSE.equals(e.getType()))
                throw new RuntimeException("Parentheses appears : " + e.getValue());
        }
    }


    private void replaceFlatToHierarchical(List<StringElement> data, Pos pos, StringElement hierarchical) {

        for (int i = pos.from; i <= pos.to; i++) {
            data.remove(pos.from);
        }

        data.add(pos.from, hierarchical);
    }

    private Optional<Pos> findParenthesesPos(List<StringElement> data) {
        int op = -1;
        int cl = -1;

        for (int i = 0; i < data.size(); i++) {
            if (StringElementType.BR_OPEN.equals(data.get(i).getType())) op = i;

            if (StringElementType.BR_CLOSE.equals(data.get(i).getType())) {
                cl = i;
                break;
            }
        }

        if (op >= 0 && cl >= 0) {
            return Optional.of(new Pos(op, cl));
        }

        return Optional.empty();
    }

    private List<StringElement> copyElements(List<StringElement> data, Pos pos) {
        return data.stream().skip(pos.from).limit(pos.to - pos.from + 1).collect(Collectors.toList());
    }

    private List<StringElement> removeAllEnclosingParentheses(List<StringElement> data) {

        while (removeOneEnclosingParentheses(data)) ;

        return data;
    }

    private boolean removeOneEnclosingParentheses(List<StringElement> data) {

        if (data.isEmpty()) return false;

        boolean op = StringElementType.BR_OPEN.equals(data.get(0).getType());
        boolean cl = StringElementType.BR_CLOSE.equals(data.get(data.size() - 1).getType());

        if (op && cl) {
            data.remove(0);
            data.remove(data.size() - 1);
            return true;
        }

        return false;
    }

    private class Pos {
        private int from;
        private int to;

        public Pos(int from, int to) {
            this.from = from;
            this.to = to;
        }
    }


}
