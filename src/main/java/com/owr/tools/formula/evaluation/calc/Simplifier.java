package com.owr.tools.formula.evaluation.calc;

import com.owr.tools.formula.parser.elements.EnclosingParentheses;
import com.owr.tools.formula.parser.elements.ParsedElement;
import com.owr.tools.formula.parser.elements.RawElement;
import com.owr.tools.formula.parser.step.ParserStep;

import java.util.List;

public class Simplifier implements ParserStep<List<ParsedElement>, List<ParsedElement>>  {



    /*
    * 1.        Reiskiniai skliaustuose - (..)

2.        Kelimas laipsniu - ^

3.        Daugyba dalyba - *, /

4.        Sveika dalis ir liekana - \ ir MOD

5.        Sudetis ir atimtis -        +, -
    * */

    @Override
    public List<ParsedElement> process(List<ParsedElement> data) {
        return simplify(data);
    }


    private List<ParsedElement> simplify(List<ParsedElement> data) {


        if (data.size() == 1) {
            return data;
        }



        for (ParsedElement element : data){

            if (element instanceof EnclosingParentheses) {
                List<ParsedElement> result = simplify(((EnclosingParentheses) element).getValue());
//                replace(data, element, result);
            }





        }





        return null;
    }

}
