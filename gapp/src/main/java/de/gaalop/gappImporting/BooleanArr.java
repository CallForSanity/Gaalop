/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.gappImporting;

import java.util.Arrays;

/**
 *
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

}
