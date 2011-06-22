package de.gaalop.gapp;

import de.gaalop.gapp.variables.GAPPMultivector;
import de.gaalop.gapp.variables.GAPPVariable;
import de.gaalop.gapp.variables.GAPPVector;

public interface VariableGetter {

    public GAPPVector parseVector(String string);

    public GAPPMultivector parseMultivector(String string);

    public GAPPVariable parseVariable(String trim);
}
