package de.gaalop.gui;

import de.gaalop.Plugin;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.*;

/**
 * ComboBox Model
 */
public class PluginModel<T extends Plugin> implements ComboBoxModel {

    private List<T> plugins;

    private int selected = 0;

    private Class<T> clazz;

    public PluginModel(Class<T> clazz, Set<T> pluginSet) {
        plugins = new ArrayList<T>(pluginSet);
        Collections.sort(plugins, PluginComparator.INSTANCE);
        this.clazz = clazz;
    }

    @Override
    public void setSelectedItem(Object anItem) {
        T ourItem = clazz.cast(anItem);
        selected = plugins.indexOf(ourItem);
    }

    @Override
    public Object getSelectedItem() {
        if (selected < 0 || selected >= plugins.size()) {
            return null;
        } else {
            return plugins.get(selected);
        }
    }

    @Override
    public int getSize() {
        return plugins.size();
    }

    @Override
    public Object getElementAt(int index) {
        return plugins.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
    }
}

enum PluginComparator implements Comparator<Plugin> {
    INSTANCE;

    @Override
    public int compare(Plugin o1, Plugin o2) {
        return o1.getName().compareToIgnoreCase(o2.getName());
    }
}