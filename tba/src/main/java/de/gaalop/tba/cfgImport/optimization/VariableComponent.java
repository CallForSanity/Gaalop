/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.tba.cfgImport.optimization;

/**
 * Implements a component of a variable
 * @author christian
 */
public class VariableComponent {

    private String name;
    private int bladeIndex;

    public VariableComponent(String name, int bladeIndex) {
        this.name = name;
        this.bladeIndex = bladeIndex;
    }

    public int getBladeIndex() {
        return bladeIndex;
    }

    public String getName() {
        return name;
    }

    public void setBladeIndex(int bladeIndex) {
        this.bladeIndex = bladeIndex;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return 87*name.hashCode()+bladeIndex;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof VariableComponent)) return false;
        VariableComponent comp = (VariableComponent) obj;

        if (comp.bladeIndex != this.bladeIndex) return false;
        if (!comp.name.equals(this.name)) return false;

        return true;
    }

}
