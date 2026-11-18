package org.ioopm.calculator.ast;

public class Subtraction extends Binary{
    public Subtraction(SymbolicExpression left, SymbolicExpression right) {
        super(left, right);
    }

    @Override
    public String getName() {
        return "-";
    }
}