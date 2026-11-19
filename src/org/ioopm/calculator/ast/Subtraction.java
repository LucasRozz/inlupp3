package org.ioopm.calculator.ast;
import java.util.HashMap;

public class Subtraction extends Binary{
    public Subtraction(SymbolicExpression left, SymbolicExpression right) {
        super(left, right);
    }

    @Override
    public String getName() {
        return "-";
    }
    public SymbolicExpression eval(Environment vars) {
        SymbolicExpression left = this.lhs.eval(vars);
        SymbolicExpression right = this.rhs.eval(vars);

        if (left.isConstant() && right.isConstant()) {
            return new Constant(left.getValue() - right.getValue());
        } else {
            return new Subtraction(left, right);
        }
    }
}