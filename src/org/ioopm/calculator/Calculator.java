package org.ioopm.calculator;
import org.ioopm.calculator.ast.*;

public class Calculator {
    public static void main(String [] args){
        System.out.println("test hej jeseper");
        new Addition(null, null);
        new Assignment(null, null);
        new Sin(null);
        new Constant(0);
        new Division(null, null);
        new Cos(null);
        new Negation(null);

        Constant hej = new Constant(42);
        if(hej.getValue() == 42){
            System.out.println("test1");
        }
        Variable test = new Variable("hej");
        try{
            test.getValue();
        }catch(RuntimeException e){
            System.out.println("test2");
        }

        Constant c1 = new Constant(5);
    Constant c2 = new Constant(2);
    Variable v = new Variable("x");
    Addition a = new Addition(c1, v);
    Multiplication m = new Multiplication(a, c2);
    }
}