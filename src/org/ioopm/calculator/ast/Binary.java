package org.ioopm.calculator.ast;

public abstract class Binary extends SymbolicExpression {
    protected SymbolicExpression lhs = null;
    protected SymbolicExpression rhs = null;
    

    public Binary(SymbolicExpression lhs, SymbolicExpression rhs){
        this.lhs = lhs;
        this.rhs = rhs;
    }
    
    public String toString() {
        boolean leftNeedsParentheses = this.lhs.getPriority() < this.getPriority();
        boolean rightNeedsParentheses = this.rhs.getPriority() < this.getPriority()
                || (this.rhs.getPriority() == this.getPriority()
                    && (this.getName().equals("-") || this.getName().equals("/")));

        if (leftNeedsParentheses) {
            if (rightNeedsParentheses) {
                return "(" + this.lhs.toString() + ") " + this.getName() + " (" + this.rhs.toString() + ")";
            } else {
                return "(" + this.lhs.toString() + ") " + this.getName() + " " + this.rhs.toString();
            }
        } else if (rightNeedsParentheses) {
            return this.lhs.toString() + " " + this.getName() + " (" + this.rhs.toString() + ")";
        } else {
            return this.lhs.toString() + " " + this.getName() + " " + this.rhs.toString();
        }
    }
    
    public boolean equals(Object other) {
        if (other instanceof Binary) {
            return this.equals((Binary) other);
        } else {
            return false;
        }
    }
    
    public boolean equals(Binary other) {
        /// access a private field of other!
        return this.lhs == other.lhs && this.rhs == other.rhs && this.getName() == other.getName(); //// ????????
    }
    
}