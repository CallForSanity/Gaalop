package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.Variableset;
import de.gaalop.gapp.visitor.GAPPVisitor;

/**
 * Represents the assignInputsVector command in the GAPP language.
 *
 * By using the GAPPAssignInputsVector command the scalar input variables
 * are assigned to the vector 'inputsVector' at the beginning of the GAPP script
 * 
 * @author Christian Steinmetz
 */
public class GAPPAssignInputsVector extends GAPPBaseInstruction {

    private Variableset values;

    public GAPPAssignInputsVector(Variableset values) {
        this.values = values;
    }

    public Variableset getValues() {
        return values;
    }

    public void setValues(Variableset values) {
        this.values = values;
    }

    @Override
    public Object accept(GAPPVisitor visitor, Object arg) {
        return visitor.visitAssignInputsVector(this,arg);
    }

}
