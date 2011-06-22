package de.gaalop.gapp.variables;

public class GAPPVariableBase {

    protected String name;

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
