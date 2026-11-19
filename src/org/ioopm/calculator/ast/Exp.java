package org.ioopm.calculator.ast;

public class Exp extends Unary{
    public Exp(SymbolicExpression e) {
        super(e);
    }

    @Override 
    public String getName(){
        return "exp";
    }
    public int getPriority(){
        return 100;
    }
    
}