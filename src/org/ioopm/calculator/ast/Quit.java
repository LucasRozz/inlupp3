package org.ioopm.calculator.ast;
import java.util.HashMap;

public class Quit extends Command{
    public SymbolicExpression eval(Environment vars) {return null;}
    private static final Quit theInstance = new Quit();
    private Quit() {}
    public static Quit instance() {
        return theInstance;
        }
    
    @Override
    public boolean isCommand() {
        return true;
    }
}