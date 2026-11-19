package org.ioopm.calculator.ast;
import java.util.HashMap;

public abstract class SymbolicExpression {
    private int priority = 50;
    
    public boolean isConstant(){
        return false;
    }
    public int getPriority(){
        return this.priority;
    }

    public String getName(){
        throw new RuntimeException("getName() called on expression with no operator");
    }
    public double getValue(){
        throw new RuntimeException("getValue() called on expression with no operator");
    }

    public abstract SymbolicExpression eval(Environment vars);

    public boolean isCommand() {
        return false;
    }
}
