package de.gaalop.gapp.variables;

public class GAPPVariableBase {

    protected String name;

    public GAPPVariableBase() {
    }

    public GAPPVariableBase(String name) {
        this.name = name;
    }

    

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
