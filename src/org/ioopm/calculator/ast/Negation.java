package org.ioopm.calculator.ast;

public class Negation extends Unary{
    public Negation(SymbolicExpression e) {
        super(e);
    }

    @Override 
    public String getName(){
        return "-";
    }
    public String toString(){
        return "-" + this.getArgument();
    }
}