package de.gaalop.gapp.instructionSet;

import de.gaalop.gapp.visitor.GAPPVisitor;

public abstract class GAPPBaseInstruction {

    public abstract void accept(GAPPVisitor visitor, Object arg);
    
}
