package org.ioopm.calculator.ast;

public class Log extends Unary{
    public Log(SymbolicExpression e) {
        super(e);
    }

    @Override 
    public String getName(){
        return "log";
    }
}