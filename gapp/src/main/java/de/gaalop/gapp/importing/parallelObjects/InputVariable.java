package de.gaalop.gapp.importing.parallelObjects;

import de.gaalop.dfg.Variable;

/**
 *
 * @author Christian Steinmetz
 */
public class InputVariable extends ParallelObject {

    private Variable variable;

    public InputVariable(Variable variable) {
        this.variable = variable;
    }

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    @Override
    public Object accept(ParallelObjectVisitor visitor, Object arg) {
        return visitor.visitInputVariable(this, arg);
    }

    @Override
    public String toString() {
        return (isNegated() ? "!" : "") + variable.toString();
    }

    @Override
    public boolean isTerminal() {
        return true;
    }

}
