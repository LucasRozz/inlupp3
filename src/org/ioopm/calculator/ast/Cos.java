package org.ioopm.calculator.ast;

public class Cos extends Unary {
    public Cos(SymbolicExpression e) {
        super(e);
    }

    @Override 
    public String getName(){
        return "cos";
    }
}