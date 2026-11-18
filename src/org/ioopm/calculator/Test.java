package org.ioopm.calculator;
import org.ioopm.calculator.ast.*;

public class Test{

    static public void testPrinting(String expected, SymbolicExpression e) {
    if (expected.equals("" + e)) {
        System.out.println("Passed: " + e);
    } else {
        System.out.println("Error: expected '" + expected + "' but got '" + e + "'");
    }
    }

    /*static public void testPrinting(String expected, SymbolicExpression e) {
        assert expected.equals("" + e ) : "expected " + expected + " but got " + e  + "";
        System.out.println("test passed");
    }*/

    public static void main(String[] args){
        Constant c1 = new Constant(5);
        Constant c2 = new Constant(2);
        Variable v = new Variable("x");
        Addition a = new Addition(c1, v);
        Multiplication m = new Multiplication(a, c2);

        System.out.println("(5.0 + x) * 2 ==> " + m);
        testPrinting("5.0 + x", a);
        testPrinting("(5.0 + x) * 2", m);
        testPrinting("5.0", c1);
    }
}