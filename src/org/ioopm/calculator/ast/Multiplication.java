package org.ioopm.calculator.ast;
import java.util.HashMap;

public class Multiplication extends Binary{
    int priority;
    
    public Multiplication(SymbolicExpression left, SymbolicExpression right) {
        super(left, right);
        this.priority = 100;
    }

    @Override
    public String getName() {
        return "*";
    }

    public int getPriority(){
        return this.priority;
    }

    public SymbolicExpression eval(Environment vars) {
        SymbolicExpression left = this.lhs.eval(vars);
        SymbolicExpression right = this.rhs.eval(vars);

        if (left.isConstant() && right.isConstant()) {
            return new Constant(left.getValue() * right.getValue());
        } else {
            return new Multiplication(left, right);
        }
    }
}