package de.gaalop.gapp;

import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPScalarVariable;
import de.gaalop.gapp.variables.GAPPVector;

/**
 * Provides an interface for parsing variables
 * @author christian
 */
public interface VariableGetter {

    /**
     * Parses a vector from a string
     * @param string The string
     * @return The parsed GAPPVector instance
     */
    public GAPPVector parseVector(String string);

    /**
     * Parses a multivector from a string
     * @param string The string
     * @return The parsed GAPPMultivector instance
     */
    public GAPPMultivector parseMultivector(String string);

     /**
     * Parses a variable from a string
     * @param string The string
     * @return The parsed GAPPVariable instance
     */
    public GAPPScalarVariable parseVariable(String string);
    
}
