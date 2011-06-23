/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.gapp;

import de.gaalop.gapp.visitor.InstructionType;
import de.gaalop.gapp.instructionSet.*;


/**
 *
 * @author christian
 */
public class GAPPInstancer {

    public static GAPPBaseInstruction instanciate(InstructionType inst,String arguments,VariableGetter getter) {
        switch (inst) {
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
            default:
                System.err.println("GAPPInstancer: Type "+inst+" isn't known, so it can't be instanced");
                return null;
            }
    }

}
