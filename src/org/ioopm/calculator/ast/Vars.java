package org.ioopm.calculator.ast;
import java.util.HashMap;

public class Vars extends Command {
    public SymbolicExpression eval(Environment vars) {return null;}
    private static final Vars theInstance = new Vars();
    private Vars() {}
    public static Vars instance() {
        return theInstance;
        }
    
    
    
    @Override
    public boolean isCommand() {
        return true;
    }
    
}
