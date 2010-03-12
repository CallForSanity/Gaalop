package de.gaalop.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.*;

/**
 * This action closes the currently opened tab.
 */
public class CloseAction extends AbstractAction {

    private final JTabbedPane tabbedPane;

    private static Icon getIcon() {
        return new ImageIcon(CloseAction.class.getResource("emblem-unreadable.png"));
    }

    public CloseAction(JTabbedPane tabbedPane) {
        super("Close", getIcon());
        this.tabbedPane = tabbedPane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Component selected = tabbedPane.getSelectedComponent();
        if (selected instanceof SourceFilePanel) {
            tabbedPane.remove(selected);
        }
    }

}
