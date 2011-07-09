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

    public boolean getComponent(int blade) {
        return components[blade];
    }

    public void setComponent(int blade, boolean value) {
        components[blade] = value;
    }

    /**
     * Performing an OR-Operation with another BooleanArr instance
     * @param booleanArr The other BooleanArr instance
     */
    public void or(BooleanArr booleanArr) {
        for (int blade=0;blade<components.length;blade++)
            components[blade] = components[blade] || booleanArr.getComponent(blade);
    }

}
