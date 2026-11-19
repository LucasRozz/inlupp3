package org.ioopm.calculator.ast;
import java.util.HashMap;

public class Variable extends Atom {
    private String identifier;

    public Variable(String identifier) {
        this.identifier = identifier;
    }

    @Override 
    public String toString(){
        return this.identifier;
    }
    public int hashCode(){
        return this.identifier.hashCode();
    }

    public boolean equals(Object other) {
        if (other instanceof Variable) {
            return this.equals((Variable) other);
        } else {
            return false;
        }
    }

    public int getPriority(){
        return 150;
    }
    
    public boolean equals(Variable other) {
        /// access a private field of other!
        return this.identifier == other.identifier; 
    }

    public boolean isCommand(){
        if(this.identifier.toUpperCase() == "QUIT" || this.identifier.toUpperCase() == "VARS"){
            return true;
        }
        return false;
    }

    public SymbolicExpression eval(Environment vars) {
        SymbolicExpression var = vars.get(new Variable(this.identifier));
        if(var != null) {
            return new Constant(var.getValue());
        } else {
            return new Variable(this.identifier);
        }
    }
}