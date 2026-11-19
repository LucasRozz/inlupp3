package org.ioopm.calculator.ast;

public abstract class Unary extends SymbolicExpression{
    private SymbolicExpression argument;

    public Unary(SymbolicExpression argument){
        this.argument = argument;
    }
    protected String getArgument(){
        return this.argument.toString();
    }

    @Override
    public String toString() {
        /// Note how the call to toString() is not necessary
        return this.getName() + " " + this.argument.toString();
    }

    public boolean equals(Object other) {
        if (other instanceof Unary) {
            return this.equals((Unary) other);
        } else {
            return false;
        }
    }
    
    public boolean equals(Unary other) {
        /// access a private field of other!
        return this.argument == other.argument && this.getValue() == other.getValue();
    }
}