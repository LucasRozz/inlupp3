package org.ioopm.calculator.ast;
import java.util.HashMap;

public class Negation extends Unary{
    public Negation(SymbolicExpression e) {
        super(e);
    }

    @Override 
    public String getName(){
        return "-";
    }
    

    public SymbolicExpression eval(Environment vars){
        SymbolicExpression arg = this.argument.eval(vars);
        if (arg.isConstant()) {
            return new Constant(-arg.getValue());
        } else {
            return new Negation(arg);
        }
    }
}