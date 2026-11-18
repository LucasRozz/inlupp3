package org.ioopm.calculator.ast;

public class Division extends Binary{
    public Division(SymbolicExpression left, SymbolicExpression right) {
        super(left, right);
    }

    @Override 
    public String getName(){
        return "/";
    }
    public int getPriority(){
        return 100;
    }
}