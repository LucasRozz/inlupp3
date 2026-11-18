package org.ioopm.calculator.ast;

public class Sin extends Unary{
    public Sin(SymbolicExpression e) {
        super(e);
    }

    @Override 
    public String getName(){
        return "sin";
    }
}