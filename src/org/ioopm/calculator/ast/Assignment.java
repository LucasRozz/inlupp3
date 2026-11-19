package org.ioopm.calculator.ast;
import java.util.HashMap;

public class Assignment extends Binary{

    public Assignment(SymbolicExpression lhs, SymbolicExpression rhs){
        super(lhs, rhs);
    }

    @Override
    public String getName() {
        return "=";
    }
    public int getPriority(){
        return 10;
    }
   public SymbolicExpression eval(Environment vars) {
        SymbolicExpression left = this.lhs.eval(vars);
        SymbolicExpression right = this.rhs.eval(vars);
        if(left.isConstant() && !right.isConstant()){
            vars.put((Variable) right, left);
            return new Constant(left.getValue());
        }
        return null;
    }
    
}