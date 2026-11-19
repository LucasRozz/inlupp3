package org.ioopm.calculator.ast;
import java.util.HashMap;

public class Log extends Unary{
    public Log(SymbolicExpression e) {
        super(e);
    }

    @Override 
    public String getName(){
        return "log";
    }

    public SymbolicExpression eval(Environment vars) {
        SymbolicExpression arg = this.argument.eval(vars);
        if (arg.isConstant()) {
            return new Constant(Math.log(arg.getValue()));
        } else {
            return new Log(arg);
        }
    }

}