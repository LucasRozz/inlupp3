package org.ioopm.calculator.ast;
import java.util.HashMap;

public class Cos extends Unary {
    public Cos(SymbolicExpression e) {
        super(e);
    }

    @Override 
    public String getName(){
        return "cos";
    }

    public SymbolicExpression eval(Environment vars) {
        SymbolicExpression arg = this.argument.eval(vars);
        if (arg.isConstant()) {
            return new Constant(Math.cos(arg.getValue()));
        } else {
            return new Cos(arg);
        }
    }
}