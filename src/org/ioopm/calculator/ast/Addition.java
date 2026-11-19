package org.ioopm.calculator.ast;
import java.util.HashMap;

public class Addition extends Binary{
    public Addition(SymbolicExpression value1, SymbolicExpression value2){
        super(value1, value2);
    }

    @Override
    public String getName() {
        return "+";
    }
    
    
    public SymbolicExpression eval(Environment vars) {
        SymbolicExpression left = this.lhs.eval(vars);
        SymbolicExpression right = this.rhs.eval(vars);
        if (left.isConstant() && right.isConstant()) {
            return new Constant(left.getValue() + right.getValue());
        } else {
            return new Addition(left, right);
        }
    }
}