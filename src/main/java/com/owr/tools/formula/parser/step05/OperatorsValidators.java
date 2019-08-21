package com.owr.tools.formula.parser.step05;

import com.owr.tools.formula.parser.elements.*;
import com.owr.tools.formula.parser.step.ValidateAfterStep;
import com.owr.tools.formula.parser.step.ValidateBeforeStep;
import com.owr.tools.formula.parser.step04.OperatorElement;

import java.util.List;

import static com.owr.tools.formula.parser.elements.Operator.MINUS;
import static com.owr.tools.formula.parser.elements.Operator.PLUS;
import static com.owr.tools.formula.parser.elements.ParsedElementType.OPERATOR;

public class OperatorsValidators implements ValidateAfterStep<List<ParsedElement>>, ValidateBeforeStep<List<ParsedElement>> {

    protected static final int FIRST = 0;
    protected static final int SECND = 1;

    @Override
    public void validateBefore(List<ParsedElement> data) {

        int opCounter = 0;
        Operator lastOp = null;

        for (ParsedElement e : data) {
            if (OPERATOR.equals(e.getType())) {
                opCounter++;
                lastOp = (Operator) e.getValue();
            } else {

                //1) not valid: +++ --- +-+ **/ ...
                if (opCounter >= 3) {
                    throw new RuntimeException("To many operators [" + opCounter + "] or operands missing");
                }

                //2) not valid: +* -^ ... valid: ++ *- /+ +- (second val will be operand sign, only + and - allowed)
                if (opCounter == 2 && !oneOf(lastOp, PLUS, MINUS)) {
                    throw new RuntimeException("Not interpreting two operators [" + lastOp + " " + (Operator) e.getValue() + "] or operand missing");
                }


                opCounter = 0;
                lastOp = null;
            }
        }

        if (opCounter > 0) {
            throw new RuntimeException("Meaningless operator(s) in the end");
        }


        //Recursive checks
        validateSingleFirstOperator(data);


    }

    protected boolean oneOf(ParsedElement e, ParsedElementType... types) {
        for (ParsedElementType type : types) {
            if (type.equals(e.getType())) return true;
        }
        return false;
    }

    protected boolean oneOf(Operator operator, Operator... ops) {
        for (Operator op : ops) {
            if (op.equals(operator)) return true;
        }
        return false;
    }

    private void validateSingleFirstOperator(List<ParsedElement> data) {

        if (data.isEmpty()){
            throw new RuntimeException("No elements found");
        }

        //4) single operator without any operand - meaningless
        if (data.size() == 1 && data.get(FIRST) instanceof OperatorElement) {
            Operator op = ((OperatorElement)data.get(FIRST)).getValue();
            throw new RuntimeException("Meaningless single \"" + op.getCh() +  "\" operator");
        }


        //3) statement should begin with + or - only if it operator and next must be operand with sign
        if (data.get(FIRST) instanceof OperatorElement && data.get(SECND) instanceof WithSign) {

            Operator op = ((OperatorElement)data.get(FIRST)).getValue();

            if (!oneOf(op, PLUS, MINUS)) {
                throw new RuntimeException("Meaningless operator in the beginning. + or - expected but \"" + op.getCh() + "\" found");
            }
        }

        for (ParsedElement e : data) {
            if (e instanceof EnclosingParentheses) {
                validateSingleFirstOperator(((EnclosingParentheses) e).getValue());
            }
        }


    }

    @Override
    public void validateAfter(List<ParsedElement> processedData) {

        if (processedData.size() <= 1) {
            return;
        }

        boolean operator = true;

        for (ParsedElement e : processedData) {

            if (e instanceof EnclosingParentheses) {
                validateAfter(((EnclosingParentheses) e).getValue());
            }

            if (OPERATOR.equals(e.getType())) {

                if (operator) {
                    throw new RuntimeException("Operand expected, operator [" + e.getValue() + "] found");
                }
                operator = true;
            } else {

                if (!operator) {
                    throw new RuntimeException("Operator expected, operand [" + e.getValue() + "] found");
                }
                operator = false;
            }
        }
    }


}
