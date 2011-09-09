package de.gaalop.tba.cfgImport.optimization;

import java.util.HashSet;

/**
 * Manages the overview usage and definitions of variable componts
 * @author Christian Steinmetz
 */
public class VariableUsage {

    private HashSet<VariableComponent> usedComponents = new HashSet<VariableComponent>();

    /**
     * Adds a component to the used components set.
     * This method is called, if a component is used.
     *
     * @param component The component to add
     */
    public void addUsage(VariableComponent component) {
       usedComponents.add(component);
    }

    /**
     * Removes a component from the used components set.
     * This method is called, if a component is defined.
     *
     * @param component The component to remove
     */
    public void addDefinition(VariableComponent component) {
        usedComponents.remove(component);
    }

    /**
     * Determines, if the used components set contains a component.
     * This method is called to check, if a assignment is necessary
     * @param component The component to check
     * @return <value>true</value> if the set contains the given component, <value>false</value> otherwise
     */
    public boolean isUsed(VariableComponent component) {
        return usedComponents.contains(component);
    }

}
