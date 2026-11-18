package org.ioopm.calculator.ast;

public class Multiplication extends Binary{
    int priority;
    
    public Multiplication(SymbolicExpression left, SymbolicExpression right) {
        super(left, right);
        this.priority = 100;
    }

    @Override
    public String getName() {
        return "*";
    }

    public int getPriority(){
        return this.priority;
    }
}