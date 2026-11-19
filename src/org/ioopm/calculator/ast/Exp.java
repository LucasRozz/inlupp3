package org.ioopm.calculator.ast;
import java.util.HashMap;

public class Exp extends Unary{
    public Exp(SymbolicExpression e) {
        super(e);
    }

    @Override 
    public String getName(){
        return "exp";
    }
    public int getPriority(){
        return 100;
    }
    
    public SymbolicExpression eval(Environment vars) {
        SymbolicExpression arg = this.argument.eval(vars);
        if (arg.isConstant()) {
            return new Constant(Math.exp(arg.getValue()));
        } else {
            return new Exp(arg);
        }
    }
}