package de.gaalop.gui;

import de.gaalop.CodeParserPlugin;
import de.gaalop.InputFile;
import de.gaalop.gui.util.PluginIconUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * This class represents a panel in a tabbed panel that contains a source file.
 */
public class SourceFilePanel extends JPanel {

    private final CodeParserPlugin parserPlugin;

    private final JTextPane textPane;

    private final JLabel tabLabel;

    private File file;

    private FileState fileState;

    private String savedContent;

    public SourceFilePanel(CodeParserPlugin plugin) {
        this(plugin, new File("New File"), "");
        fileState = FileState.UNSAVED;
    }

    public SourceFilePanel(CodeParserPlugin plugin, File file, String content) {
        super(new BorderLayout(), true);

        this.parserPlugin = plugin;
        this.file = file;
        this.fileState = FileState.SAVED;
        this.savedContent = content;

        // The text editor for our source code
        textPane = new JTextPane();
        textPane.setText(content);
        textPane.addKeyListener(new SetChangedStateListener());

        // Add the scroll pane
        JScrollPane scrollPane = new JScrollPane(textPane);
        add(scrollPane, BorderLayout.CENTER);

        // Create a component for the Tab Label
        if (plugin.getIcon() != null) {
            tabLabel = new JLabel("", PluginIconUtil.getSmallIcon(plugin), JLabel.LEFT);
        } else {
            tabLabel = new JLabel("", JLabel.LEFT);
        }

        updateTabLabel();
    }

    /**
     * Creates an input file that represents this tabs current content.
     *
     * @return A new input file that represents this tabs content.
     */
    public InputFile getInputFile() {
        return new InputFile(file.getName(), textPane.getText());
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
        updateTabLabel();
    }

    private void updateTabLabel() {
        if (fileState != FileState.SAVED) {
            tabLabel.setText(file.getName() + "*");
        } else {
            tabLabel.setText(file.getName());
        }
    }

    public CodeParserPlugin getParserPlugin() {
        return parserPlugin;
    }

    public JLabel getTabLabel() {
        return tabLabel;
    }

    public void setSaved() {
        fileState = FileState.SAVED;
        savedContent = textPane.getText();
        updateTabLabel();
    }

    public FileState getFileState() {
        return fileState;
    }

    private class SetChangedStateListener extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            if (fileState != FileState.UNSAVED) {
                if (textPane.getText().equals(savedContent)) {
                    fileState = FileState.SAVED;
                    updateTabLabel();
                } else {
                    fileState = FileState.CHANGED;
                    updateTabLabel();
                }
            }
        }
    }
}
