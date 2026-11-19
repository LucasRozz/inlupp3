package org.ioopm.calculator.ast;
import java.util.HashMap;

public class Sin extends Unary{
    
    public Sin(SymbolicExpression e) {
        super(e);
    }

    @Override 
    public String getName(){
        return "sin";
    }
    
    public SymbolicExpression eval(Environment vars) {
        SymbolicExpression arg = this.argument.eval(vars);
        if (arg.isConstant()) {
            return new Constant(Math.sin(arg.getValue()));
        } else {
            return new Sin(arg);
        }
    }
}