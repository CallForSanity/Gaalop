package de.gaalop.gapp.variables;

/**
 * Represents a basic variable with a name in GAPP IR.
 *
 * @author christian
 */
public abstract class GAPPVariable extends GAPPValueHolder {

    protected String name;

    public GAPPVariable(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
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
        if (!(obj instanceof GAPPVariable)) return false;
        return name.equals(((GAPPVariable) obj).name);
    }

    @Override
    public boolean isVariable() {
        return true;
    }

}
