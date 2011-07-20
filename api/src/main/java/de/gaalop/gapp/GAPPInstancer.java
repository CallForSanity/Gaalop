package de.gaalop.gapp;

import de.gaalop.gapp.visitor.InstructionType;
import de.gaalop.gapp.instructionSet.*;

/**
 * This class is used for instancing GAPP instructions
 * @author christian
 */
public class GAPPInstancer {

    /**
     * Creates a new instance of a given type of a GAPP instruction
     * @param type The type of the new instruction instance
     * @return The new instance
     */
    public static GAPPBaseInstruction instanciate(InstructionType type) {
        switch (type) {
            case resetMv:
                    return new GAPPResetMv();
            case assignMv:
                    return new GAPPAssignMv();
            case setMv:
                    return new GAPPSetMv();
            case addMv:
                    return new GAPPAddMv();
            case setVector:
                    return new GAPPSetVector();
            case dotVectors:
                    return new GAPPDotVectors();
            case calculate:
                    return new GAPPCalculate();
            default:
                System.err.println("GAPPInstancer: Type "+type+" isn't known, so it can't be instanced");
                return null;
            }
    }

}
