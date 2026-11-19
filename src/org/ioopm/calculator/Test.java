package org.ioopm.calculator;

import org.ioopm.calculator.ast.*;
    
public class Test {

    private static int passed = 0;
    private static int failed = 0;

    // ================================================================
    // Hjälpmetoder
    // ================================================================

    private static void check(boolean ok, String description, String failMessage) {
        if (ok) {
            passed++;
            System.out.println("Passed: " + description);
        } else {
            failed++;
            System.out.println("Error:  " + failMessage);
        }
        assert ok : failMessage;
    }

    private static void test(String expectedPrint, SymbolicExpression expected,
                             SymbolicExpression e, Environment vars) {
        String printed = "" + e;
        check(expectedPrint.equals(printed),
              "print  " + printed,
              "print: expected '" + expectedPrint + "' but got '" + printed + "'");
        try {
            SymbolicExpression r = e.eval(vars);
            check(r.equals(expected),
                  "eval   " + printed + "  ==>  " + r,
                  "eval:  " + printed + "  ==>  expected '" + expected + "' but got '" + r + "'");
        } catch (Exception ex) {
            check(false, "", "eval:  " + printed + "  ==>  unexpected exception: " + ex);
        }
    }

    /** Samma sak, men i en ny tom miljö och med ett tal som förväntat värde. */
    private static void test(String expectedPrint, double expectedValue, SymbolicExpression e) {
        test(expectedPrint, new Constant(expectedValue), e, new Environment());
    }

    /** Med en given miljö och ett tal som förväntat värde. */
    private static void test(String expectedPrint, double expectedValue,
                             SymbolicExpression e, Environment vars) {
        test(expectedPrint, new Constant(expectedValue), e, vars);
    }

    // Korta namn så att träden blir läsbara
    private static Constant c(double v) { return new Constant(v); }
    private static Variable v(String n) { return new Variable(n); }
    private static SymbolicExpression add(SymbolicExpression a, SymbolicExpression b) { return new Addition(a, b); }
    private static SymbolicExpression sub(SymbolicExpression a, SymbolicExpression b) { return new Subtraction(a, b); }
    private static SymbolicExpression mul(SymbolicExpression a, SymbolicExpression b) { return new Multiplication(a, b); }
    private static SymbolicExpression div(SymbolicExpression a, SymbolicExpression b) { return new Division(a, b); }
    private static SymbolicExpression neg(SymbolicExpression a) { return new Negation(a); }

    // ================================================================
    // 1. Multiplikation och division före addition och subtraktion
    // ================================================================

    public static void testMultiplicationBeforeAddition() {
        System.out.println("\n=== * and / before + and - ===");

        // 2 + 3 * 4 = 14   jämfört med   (2 + 3) * 4 = 20
        test("2.0 + 3.0 * 4.0", 14, add(c(2), mul(c(3), c(4))));
        test("(2.0 + 3.0) * 4.0", 20, mul(add(c(2), c(3)), c(4)));

        // 2 * 3 + 4 = 10   jämfört med   2 * (3 + 4) = 14
        test("2.0 * 3.0 + 4.0", 10, add(mul(c(2), c(3)), c(4)));
        test("2.0 * (3.0 + 4.0)", 14, mul(c(2), add(c(3), c(4))));

        // 10 - 6 / 2 = 7   jämfört med   (10 - 6) / 2 = 2
        test("10.0 - 6.0 / 2.0", 7, sub(c(10), div(c(6), c(2))));
        test("(10.0 - 6.0) / 2.0", 2, div(sub(c(10), c(6)), c(2)));

        // 12 / 3 + 1 = 5   jämfört med   12 / (3 + 1) = 3
        test("12.0 / 3.0 + 1.0", 5, add(div(c(12), c(3)), c(1)));
        test("12.0 / (3.0 + 1.0)", 3, div(c(12), add(c(3), c(1))));

        // 2 * 3 + 4 * 5 = 26
        test("2.0 * 3.0 + 4.0 * 5.0", 26, add(mul(c(2), c(3)), mul(c(4), c(5))));

        // 1 + 2 * 3 - 4 / 2 = 5
        test("1.0 + 2.0 * 3.0 - 4.0 / 2.0", 5,
             sub(add(c(1), mul(c(2), c(3))), div(c(4), c(2))));
    }

    // ================================================================
    // 2. Vänster till höger inom samma prioritet
    // ================================================================

    public static void testLeftToRight() {
        System.out.println("\n=== left to right within the same priority ===");

        // 10 - 4 - 3 = 3   jämfört med   10 - (4 - 3) = 9
        test("10.0 - 4.0 - 3.0", 3, sub(sub(c(10), c(4)), c(3)));

        // 100 / 10 / 2 = 5   jämfört med   100 / (10 / 2) = 20
        test("100.0 / 10.0 / 2.0", 5, div(div(c(100), c(10)), c(2)));
        test("100.0 / (10.0 / 2.0)", 20, div(c(100), div(c(10), c(2))));

        // 10 - 2 + 3 = 11   jämfört med   10 - (2 + 3) = 5
        test("10.0 - 2.0 + 3.0", 11, add(sub(c(10), c(2)), c(3)));

        // 8 / 2 * 4 = 16   jämfört med   8 / (2 * 4) = 1
        test("8.0 / 2.0 * 4.0", 16, mul(div(c(8), c(2)), c(4)));
        test("8.0 / (2.0 * 4.0)", 1, div(c(8), mul(c(2), c(4))));

        // + och * är associativa: båda trädformerna ger samma värde, och
        // ingen parentes behövs på högersidan
        test("1.0 + 2.0 + 3.0", 6, add(add(c(1), c(2)), c(3)));
        test("1.0 + 2.0 + 3.0", 6, add(c(1), add(c(2), c(3))));
        test("2.0 * 3.0 * 4.0", 24, mul(mul(c(2), c(3)), c(4)));
        test("2.0 * 3.0 * 4.0", 24, mul(c(2), mul(c(3), c(4))));

        // * och / blandat: 2 * 3 / 4 = 1.5, oavsett trädform
        test("2.0 * 3.0 / 4.0", 1.5, div(mul(c(2), c(3)), c(4)));
        test("2.0 * 3.0 / 4.0", 1.5, mul(c(2), div(c(3), c(4))));
    }

    // ================================================================
    // 3. Flera och nästlade parenteser
    // ================================================================

    public static void testNestedParentheses() {
        System.out.println("\n=== several and nested parentheses ===");

        // (1 + 2) * (3 + 4) = 21
        test("(1.0 + 2.0) * (3.0 + 4.0)", 21, mul(add(c(1), c(2)), add(c(3), c(4))));

        // (2 - 5) * (2 - 5) = 9
        test("(2.0 - 5.0) * (2.0 - 5.0)", 9, mul(sub(c(2), c(5)), sub(c(2), c(5))));

        // (1 + 2) * (3 - 4) / 2 = -1.5
        test("(1.0 + 2.0) * (3.0 - 4.0) / 2.0", -1.5,
             div(mul(add(c(1), c(2)), sub(c(3), c(4))), c(2)));

        // 2 * (3 + 4) * 5 = 70
        test("2.0 * (3.0 + 4.0) * 5.0", 70, mul(mul(c(2), add(c(3), c(4))), c(5)));

        // ((1 + 2) * 3 + 4) * 5 = 65
        test("((1.0 + 2.0) * 3.0 + 4.0) * 5.0", 65,
             mul(add(mul(add(c(1), c(2)), c(3)), c(4)), c(5)));

        // 2 * (3 * (4 + 1)) = 30. Den yttre parentesen behövs inte vid utskrift
        test("2.0 * 3.0 * (4.0 + 1.0)", 30, mul(c(2), mul(c(3), add(c(4), c(1)))));

        // (8 - (2 + 1)) * 2 = 10
        test("(8.0 - (2.0 + 1.0)) * 2.0", 10, mul(sub(c(8), add(c(2), c(1))), c(2)));

        // 1 / (1 / (1 + 1)) = 2
        test("1.0 / (1.0 / (1.0 + 1.0))", 2, div(c(1), div(c(1), add(c(1), c(1)))));

        // (6 - 2) / (1 + 1) - 1 = 1
        test("(6.0 - 2.0) / (1.0 + 1.0) - 1.0", 1,
             sub(div(sub(c(6), c(2)), add(c(1), c(1))), c(1)));
    }

    // ================================================================
    // 4. Negation
    // ================================================================

    public static void testNegation() {
        System.out.println("\n=== negation ===");

        // -2 * 3 och -(2 * 3) ger samma värde, men olika träd och utskrift
        test("-2.0 * 3.0", -6, mul(neg(c(2)), c(3)));
        test("-(2.0 * 3.0)", -6, neg(mul(c(2), c(3))));

        // -2 + 3 = 1   jämfört med   -(2 + 3) = -5
        test("-2.0 + 3.0", 1, add(neg(c(2)), c(3)));
        test("-(2.0 + 3.0)", -5, neg(add(c(2), c(3))));

        // -(2 + 3) * 4 = -20
        test("-(2.0 + 3.0) * 4.0", -20, mul(neg(add(c(2), c(3))), c(4)));

        // 5 - -2 = 7 och 2 * -3 = -6
        test("5.0 - -2.0", 7, sub(c(5), neg(c(2))));
        test("2.0 * -3.0", -6, mul(c(2), neg(c(3))));

        // Flera negationer: - - 3 = 3 och - - - 3 = -3
        test("--3.0", 3, neg(neg(c(3))));
        test("---3.0", -3, neg(neg(neg(c(3)))));

        // -(1 - 4) = 3
        test("-(1.0 - 4.0)", 3, neg(sub(c(1), c(4))));
    }

    // ================================================================
    // 5. Funktioner (sin, cos, exp, log)
    // ================================================================

    public static void testFunctions() {
        System.out.println("\n=== functions bind harder than * and + ===");

        // sin 0 + 1 = 1   jämfört med   sin (0 + 1) = sin 1
        test("sin(0.0) + 1.0", 1, add(new Sin(c(0)), c(1)));
        test("sin(0.0 + 1.0)", Math.sin(1), new Sin(add(c(0), c(1))));

        // cos 0 * 5 = 5   jämfört med   cos (0 * 5) = 1
        test("cos(0.0) * 5.0", 5, mul(new Cos(c(0)), c(5)));
        test("cos(0.0 * 5.0)", 1, new Cos(mul(c(0), c(5))));

        // exp 0 + exp 0 = 2   jämfört med   exp (1 + 1) = e^2
        test("exp(0.0) + exp(0.0)", 2, add(new Exp(c(0)), new Exp(c(0))));
        test("exp(1.0 + 1.0)", Math.exp(2), new Exp(add(c(1), c(1))));

        // log 1 * 3 = 0   jämfört med   log (1 * 3) = log 3
        test("log(1.0) * 3.0", 0, mul(new Log(c(1)), c(3)));
        test("log(1.0 * 3.0)", Math.log(3), new Log(mul(c(1), c(3))));

        // 2 * sin (3 - 3) = 0
        test("2.0 * sin(3.0 - 3.0)", 0, mul(c(2), new Sin(sub(c(3), c(3)))));

        // Funktion av funktion: log exp 2
        test("log(exp(2.0))", Math.log(Math.exp(2)), new Log(new Exp(c(2))));

        // Negation av en funktion: -cos 0 = -1   jämfört med   -(cos 0 + 1) = -2
        test("-cos(0.0)", -1, neg(new Cos(c(0))));
        test("-(cos(0.0) + 1.0)", -2, neg(add(new Cos(c(0)), c(1))));
    }

    // ================================================================
    // 6. Division med noll (Java sköter det, som uppgiften säger)
    // ================================================================

    public static void testDivisionByZero() {
        System.out.println("\n=== division by zero ===");
        test("1.0 / 0.0", Double.POSITIVE_INFINITY, div(c(1), c(0)));
        test("-1.0 / 0.0", Double.NEGATIVE_INFINITY, div(neg(c(1)), c(0)));
        test("1.0 / (2.0 - 2.0)", Double.POSITIVE_INFINITY, div(c(1), sub(c(2), c(2))));
        // 0/0 ger NaN, och NaN == NaN är falskt i Java, så det går inte att
        // testa med equals. Det är därför det inte finns något test för det här.
    }

    // ================================================================
    // 8. Tilldelning har lägst prioritet
    // ================================================================

    public static void testAssignmentPriority() {
        System.out.println("\n=== assignment binds weakest ===");

        // 2 * 3 = b tilldelar 6, inte bara 3
        Environment env = new Environment();
        test("2.0 * 3.0 = b", 6, new Assignment(mul(c(2), c(3)), v("b")), env);
        check(c(6).equals(env.get(v("b"))), "afterwards b is 6.0", "expected b to be 6.0 but it was " + env.get(v("b")));

        // (2 + 3 = a) * 2 = 10, och a blir 5
        env = new Environment();
        test("(2.0 + 3.0 = a) * 2.0", 10, mul(new Assignment(add(c(2), c(3)), v("a")), c(2)), env);
        check(c(5).equals(env.get(v("a"))), "afterwards a is 5.0", "expected a to be 5.0 but it was " + env.get(v("a")));

        // (1 = x) + (2 = y) * x = 3. Vänster till höger: x blir 1 innan det används
        env = new Environment();
        test("(1.0 = x) + (2.0 = y) * x", 3,
             add(new Assignment(c(1), v("x")), mul(new Assignment(c(2), v("y")), v("x"))), env);

        // 1 + 2 * 3 = z, alltså z = 7
        env = new Environment();
        test("1.0 + 2.0 * 3.0 = z", 7, new Assignment(add(c(1), mul(c(2), c(3))), v("z")), env);
    }

    // ================================================================

    public static void main(String[] args) {
        testMultiplicationBeforeAddition();
        testLeftToRight();
        testNestedParentheses();
        testNegation();
        testFunctions();
        testDivisionByZero();
        testAssignmentPriority();

        System.out.println("\n=================================");
        System.out.println("Passed: " + passed + "   Failed: " + failed);
        System.out.println("=================================");
        new Variable("quit");
        Vars.instance();
    }
}
