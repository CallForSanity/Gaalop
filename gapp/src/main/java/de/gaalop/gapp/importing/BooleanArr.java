package de.gaalop.gapp.importing;

import java.util.Arrays;

/**
 * Stores an array of boolean
 * @author christian
 */
public class BooleanArr {

    private boolean[] components;

    public BooleanArr(int componentsCount) {
        components = new boolean[componentsCount];
        Arrays.fill(components,false);
    }

    public boolean[] getComponents() {
        return components;
    }

    public void setComponents(boolean[] components) {
        this.components = components;
    }

    /**
     * Returns the value of a component at a given blade index
     * @param blade The blade index
     * @return The value of a component at the given blade index
     */
    public boolean getComponent(int blade) {
        return components[blade];
    }

    /**
     * Sets a component to a specific value
     * @param blade The blade index of the component
     * @param value The value to be set
     */
    public void setComponent(int blade, boolean value) {
        components[blade] = value;
    }

    /**
     * Performing an OR-Operation with another BooleanArr instance.
     *
     * The OR-Operation is done element for element.
     *
     * @param booleanArr The other BooleanArr instance
     */
    public void or(BooleanArr booleanArr) {
        for (int blade=0;blade<components.length;blade++)
            components[blade] = components[blade] || booleanArr.getComponent(blade);
    }

}
