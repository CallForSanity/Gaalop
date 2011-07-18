/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.gapp.variables;

/**
 * Class to store a contant with a float value
 * @author christian
 */
public class GAPPConstant extends GAPPValueHolder {

    private float value;

    public GAPPConstant(float value) {
        this.value = value;
    }
    
    @Override
    public boolean isVariable() {
        return false;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String prettyPrint() {
        return Float.toString(value);
    }

    @Override
    public Object accept(GAPPVariableVisitor visitor, Object arg) {
        return visitor.visitConstant(this, arg);
    }

}
