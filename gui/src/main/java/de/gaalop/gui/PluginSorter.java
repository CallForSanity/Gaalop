package de.gaalop.gui;

import de.gaalop.Plugin;

import java.util.Comparator;

/**
 * A comparator that can compare Plugins by name.
 */
public class PluginSorter implements Comparator<Plugin> {
    @Override
    public int compare(Plugin o1, Plugin o2) {
        return o1.getName().compareToIgnoreCase(o2.getName());
    }
}
