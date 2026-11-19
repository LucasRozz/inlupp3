package org.ioopm.calculator.ast;

public class Addition extends Binary{
    public Addition(SymbolicExpression value1, SymbolicExpression value2){
        super(value1, value2);
    }

    @Override
    public String getName() {
        return "+";
    }
}