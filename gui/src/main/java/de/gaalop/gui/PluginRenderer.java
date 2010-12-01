package de.gaalop.gui;

import de.gaalop.Plugin;

import javax.swing.*;
import java.awt.*;

/**
 * Creates JLabels that contain a Plugins name to display them in a list or combo box.
 */
public class PluginRenderer implements ListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        final String text;

        if (value instanceof Plugin) {
            text = ((Plugin) value).getName();
        } else {
            text = value.toString();
        }

        return new JLabel(text);
    }

}
