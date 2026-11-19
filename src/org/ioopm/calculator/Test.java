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
public static void testAtoms() {
        System.out.println("--- Atomer ---");
        testPrinting("42.0", new Constant(42));
        testPrinting("3.14", new Constant(3.14));
        testPrinting("0.0", new Constant(0));
        testPrinting("x", new Variable("x"));
        testPrinting("ans", new Variable("ans"));
    }

    // ------------------------------------------------------------
    // Enkla binära uttryck: ett av varje operator
    // ------------------------------------------------------------
    public static void testBinary() {
        System.out.println("--- Binära uttryck ---");
        Constant five = new Constant(5);
        Constant two = new Constant(2);
        Variable x = new Variable("x");
        Variable y = new Variable("y");

        testPrinting("5.0 + 2.0", new Addition(five, two));
        testPrinting("5.0 - x", new Subtraction(five, x));
        testPrinting("x * y", new Multiplication(x, y));
        testPrinting("2.0 / x", new Division(two, x));
    }

    // ------------------------------------------------------------
    // Unära uttryck
    // ------------------------------------------------------------
    public static void testUnary() {
        System.out.println("--- Unära uttryck ---");
        Variable x = new Variable("x");
        Constant one = new Constant(1);

        testPrinting("sin x", new Sin(x));
        testPrinting("cos x", new Cos(x));
        testPrinting("exp 1.0", new Exp(one));
        testPrinting("log x", new Log(x));
        testPrinting("-x", new Negation(x));
        testPrinting("-1.0", new Negation(one));
    }

    // ------------------------------------------------------------
    // Nästlade uttryck där inga parenteser behövs
    // ------------------------------------------------------------
    public static void testNestedNoParentheses() {
        System.out.println("--- Nästlade, utan parenteser ---");
        Constant one = new Constant(1);
        Constant two = new Constant(2);
        Constant three = new Constant(3);
        Variable x = new Variable("x");

        // 1 + 2 * x  (multiplikationen binder hårdare, inga parenteser behövs)
        testPrinting("1.0 + 2.0 * x",
                     new Addition(one, new Multiplication(two, x)));

        // 1 + 2 + 3  (vänsterassociativt: (1 + 2) + 3)
        testPrinting("1.0 + 2.0 + 3.0",
                     new Addition(new Addition(one, two), three));

        // 2 * x - 1
        testPrinting("2.0 * x - 1.0",
                     new Subtraction(new Multiplication(two, x), one));

        // sin x + cos x
        testPrinting("sin x + cos x",
                     new Addition(new Sin(x), new Cos(x)));

        // -x * 2
        testPrinting("-x * 2.0",
                     new Multiplication(new Negation(x), two));

        // sin exp x  (funktion av funktion)
        testPrinting("sin exp x",
                     new Sin(new Exp(x)));

        // - - x  (dubbel negation)
        testPrinting("--x",
                     new Negation(new Negation(x)));
    }

    // ------------------------------------------------------------
    // Tilldelningar
    // ------------------------------------------------------------
    public static void testAssignments() {
        System.out.println("--- Tilldelningar ---");
        Constant two = new Constant(2);
        Constant three = new Constant(3);
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Variable z = new Variable("z");

        // 3 = x
        testPrinting("3.0 = x",
                     new Assignment(three, x));

        // x + y = z
        testPrinting("x + y = z",
                     new Assignment(new Addition(x, y), z));

        // 2 = x = y  (kedjad tilldelning: (2 = x) = y)
        testPrinting("2.0 = x = y",
                     new Assignment(new Assignment(two, x), y));

        // 2 * x = y
        testPrinting("2.0 * x = y",
                     new Assignment(new Multiplication(two, x), y));

        // sin x = z
        testPrinting("sin x = z",
                     new Assignment(new Sin(x), z));
    }

    // ------------------------------------------------------------
    // Parenteser. Dessa misslyckas tills du har implementerat
    // parenteser (t.ex. med en prioritetsmetod). Det är meningen.
    // ------------------------------------------------------------
    public static void testParentheses() {
        System.out.println("--- Parenteser (misslyckas tills det är implementerat) ---");
        Constant one = new Constant(1);
        Constant two = new Constant(2);
        Constant five = new Constant(5);
        Variable x = new Variable("x");
        Variable y = new Variable("y");
        Variable z = new Variable("z");

        // (5 + x) * 2
        testPrinting("(5.0 + x) * 2.0",
                     new Multiplication(new Addition(five, x), two));

        // 2 * (x - 1)
        testPrinting("2.0 * (x - 1.0)",
                     new Multiplication(two, new Subtraction(x, one)));

        // 5 - (2 - x)  (högerledet måste ha parentes, annars ändras betydelsen)
        testPrinting("5.0 - (2.0 - x)",
                     new Subtraction(five, new Subtraction(two, x)));

        // x / (y * 2)
        testPrinting("x / (y * 2.0)",
                     new Division(x, new Multiplication(y, two)));

        // sin (x + 1)
        testPrinting("sin (x + 1.0)",
                     new Sin(new Addition(x, one)));

        // -(x + y)
        testPrinting("-(x + y)",
                     new Negation(new Addition(x, y)));

        // (x + y = z) - 1  (tilldelning inuti ett uttryck)
        testPrinting("(x + y = z) - 1.0",
                     new Subtraction(new Assignment(new Addition(x, y), z), one));
    }

    // ------------------------------------------------------------
    // Kommandon. Avkommentera när Quit och Vars har en toString().
    // Använd Quit.instance()/Vars.instance() om du har singletons,
    // annars new Quit()/new Vars().
    // ------------------------------------------------------------
    public static void testCommands() {
        System.out.println("--- Kommandon ---");
        // testPrinting("quit", Quit.instance());
        // testPrinting("vars", Vars.instance());
    }

    public static void main(String[] args) {
        testAtoms();
        testBinary();
        testUnary();
        testNestedNoParentheses();
        testAssignments();
        testParentheses();
        testCommands();
    }
}