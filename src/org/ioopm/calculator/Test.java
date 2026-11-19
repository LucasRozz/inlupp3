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

    /*
    public void testEvaluating(SymbolicExpression expected, SymbolicExpression e) {
    SymbolicExpression r = e.eval();
    if (r.equals(expected)) {
        System.out.println("Passed: " + e);
    } else {
        System.out.println("Error: expected '" + expected + "' but got '" + e + "'");
    }
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

        String a2 = new String("Hello");
        Object b2 = a2;
        
        if(c1.equals(c1)) {
            System.out.println("Constants are equal"); /// asking a string if it is equivalent to an object
        }
        if(a2.equals(b2)) {
            System.out.println("Strings equal to object"); /// asking a string if it is equivalent to an object
        }
        if(a2.equals(a2)) {
            System.out.println("Strings are equal"); /// asking a string if it is equivalent to a string
        }
        if(b2.equals(a2)) {
            System.out.println("Objects equal to string"); /// asking an object if it is equivalent to a string
        }
        if(b2.equals(b2)) {
            System.out.println("Objects are equal"); /// asking an object if it is equivalent to an object
        }

        SymbolicExpression a3 = new Addition(new Constant(5), new Constant(37));
        SymbolicExpression b3 = new Constant(42);
        if(a3.equals(b3)) {
            System.out.println("Constants are equal"); /// asking a string if it is equivalent to an object
        }
        //testEvaluating(b3, a3); /// Tests if reducing a returns b (it should!)
    }
}