package org.ioopm.calculator.ast;
import java.util.HashMap;

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

    public SymbolicExpression eval(Environment vars) {
        SymbolicExpression left = this.lhs.eval(vars);
        SymbolicExpression right = this.rhs.eval(vars);

        if (left.isConstant() && right.isConstant()) {
            return new Constant(left.getValue() / right.getValue());
        } else {
            return new Division(left, right);
        }
    }
}