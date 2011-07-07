package de.gaalop.gapp.variables;

/**
 * Represents a basic variable in GAPP IR.
 *
 * @author christian
 */
public class GAPPVariableBase {

    protected String name;

    public GAPPVariableBase(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Returns a pretty-formed string, which short outlines the contents
     * @return The pretty-formed string
     */
    public String prettyPrint() {
        return name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GAPPVariableBase)) return false;
        return name.equals(((GAPPVariableBase) obj).name);
    }




}
