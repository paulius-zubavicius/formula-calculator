package com.owr.tools.formula.evaluation;

public class App {

    public static void main(String[] args) {
        FormulaEvaluator f = new FormulaEvaluator();
       System.out.println(f.parseToString(" (53.9+  -3*+s(2^3cos(+15))-M)"));

       System.out.println(f.parseToString("aaa(2^3)"));
    }
}
