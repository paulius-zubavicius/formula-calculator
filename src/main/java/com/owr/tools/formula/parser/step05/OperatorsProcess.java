package com.owr.tools.formula.parser.step05;

import com.owr.tools.formula.parser.elements.*;
import com.owr.tools.formula.parser.step.ParserStep;
import com.owr.tools.formula.parser.step04.OperatorElement;

import java.util.ArrayList;
import java.util.List;

import static com.owr.tools.formula.parser.elements.Operator.*;
import static com.owr.tools.formula.parser.elements.ParsedElementType.*;

public class OperatorsProcess extends OperatorsValidators implements ParserStep<List<ParsedElement>, List<ParsedElement>> {

    @Override
    public List<ParsedElement> process(List<ParsedElement> data) {

        // ... A--B => A+B
        data = twoOperatorsReplaceToOne(data);

        // inserting missed operands ...C(...) -> ...C*(...)
        data = insertMissedOperators(data);

        // firs operators convert as a sign of operand (- 15) -> ( {-15} )
        plusMinusAsASignInBeginning(data);

        return data;
    }

    private boolean plusMinusAsASignInBeginning(List<ParsedElement> data) {

        // Minimum 2 elements required

        if (data.size() < 2) {
            return false;
        }

        boolean found = false;
        if (data.get(0) instanceof OperatorElement && data.get(1) instanceof WithSign) {
            OperatorElement e1 = (OperatorElement) data.get(0);
            WithSign e2 = (WithSign) data.get(1);

            if (oneOf(e1.getValue(), PLUS, MINUS)) {
                if (oneOf(e1.getValue(), MINUS)) e2.changeSign();
                data.remove(0);
                found = true;
            }
        }

        boolean replaced = false;

        do {
            replaced = false;
            for (ParsedElement e : data) {
                if (e instanceof EnclosingParentheses) {
                    if (plusMinusAsASignInBeginning(((EnclosingParentheses) e).getValue())) {
                        replaced = true;
                        found = true;
                        break;
                    }
                }
            }
        } while (replaced);

        return found;

    }


    /**
     * inserting missed operators <pre>...C(...) -> ...C*(...)</pre>
     */
    private List<ParsedElement> insertMissedOperators(List<ParsedElement> data) {

        while (insertFirsMissedMultiplyOp(data)) ;

        return data;
    }

    private boolean insertFirsMissedMultiplyOp(List<ParsedElement> data) {
        ParsedElement lastElement = data.get(0);
        boolean foundMissed = false;
        for (int i = 0; i < data.size(); i++) {

            if (data.get(i) instanceof EnclosingParentheses) {
                if (insertFirsMissedMultiplyOp(((EnclosingParentheses) data.get(i)).getValue())) return true;
            }

            if (oneOf(lastElement, NUMBER, VARIABLE) && oneOf(data.get(i), PARENTHESES, FUNCTION)) {
                data.add(i, new OperatorElement(MULTIPLY));
                return true;
            }

            lastElement = data.get(i);
        }

        return false;
    }




    /**
     * <pre>
     * A--B => A+B
     * A+-B => A-B
     * A?+B => A?B
     * A++B => A+B
     * A-+B => A-B
     * A^+B => A^B
     * A*+B => A*B
     * A?-B => A?(-B)</pre>
     */
    private List<ParsedElement> twoOperatorsReplaceToOne(List<ParsedElement> data) {
        List<ParsedElement> forRemove = new ArrayList<>();
        List<OperatorElement> opers = new ArrayList<>();


        for (ParsedElement e : data) {

            if (OPERATOR.equals(e.getType())) {
                opers.add((OperatorElement) e);
            } else {
                if (opers.size() == 2) {
                    twoOpToOneOp(forRemove, opers, e);
                }

                opers.clear();
            }
        }

        data.removeAll(forRemove);
        return data;
    }

    private void twoOpToOneOp(List<ParsedElement> forRemove, List<OperatorElement> opers, ParsedElement operandAfter) {

        // A--B => A+B
        if (twoOperators(MINUS, MINUS, opers)) {
            opers.get(FIRST).setValue(PLUS);
            forRemove.add(opers.get(SECND));
            return;
        }

        // A+-B => A-B
        if (twoOperators(PLUS, MINUS, opers)) {
            forRemove.add(opers.get(FIRST));
            return;
        }

        // A?+B => A?B
        // A++B => A+B
        // A-+B => A-B
        // A^+B => A^B
        // A*+B => A*B
        // ...
        if (firsIsAnyAndSecondIs(PLUS, opers)) {
            forRemove.add(opers.get(SECND));
            return;
        }

        // A?-B => A?(-B)
        if (firsIsAnyAndSecondIs(MINUS, opers)) {
            ((WithSign) operandAfter).changeSign();
            forRemove.add(opers.get(SECND));
            return;
        }

        // If it throws - validation works wrong
        throw new RuntimeException();
    }

    private boolean firsIsAnyAndSecondIs(Operator op, List<OperatorElement> opers) {
        return op.equals(opers.get(SECND).getValue());
    }

    private boolean twoOperators(Operator op1, Operator op2, List<OperatorElement> opers) {
        return op1.equals(opers.get(FIRST).getValue()) && op2.equals(opers.get(SECND).getValue());
    }



}
