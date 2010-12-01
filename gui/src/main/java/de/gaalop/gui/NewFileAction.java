package de.gaalop.gui;

import de.gaalop.CodeParserPlugin;
import de.gaalop.gui.util.PluginIconUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * This action creates a new file associated with a given code parser.
 */
public class NewFileAction extends AbstractAction {

    private final JTabbedPane tabbedPanel;

    private final CodeParserPlugin plugin;

    public NewFileAction(CodeParserPlugin plugin, JTabbedPane tabbedPanel) {
        super(plugin.getName(), PluginIconUtil.getSmallIcon(plugin));
        this.tabbedPanel = tabbedPanel;
        this.plugin = plugin;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SourceFilePanel filePanel = new SourceFilePanel(plugin);
        tabbedPanel.addTab("", filePanel);
        int indexOfPanel = tabbedPanel.indexOfComponent(filePanel);
        tabbedPanel.setTabComponentAt(indexOfPanel, filePanel.getTabLabel());
        tabbedPanel.setSelectedIndex(indexOfPanel);
    }
}
