package de.gaalop.gui;

import de.gaalop.*;
import java.awt.event.ItemEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * The bean that represents the main form of the Gaalop GUI.
 */
public class MainForm {
    private JPanel contentPane;
    private JButton optimizeButton;
    private JButton configureButton;
    private JLabel logoIcon;
    private JTabbedPane tabbedPane;
    private JEditorPane welcomeToGaalopWelcomeEditorPane;
    private JButton newFileButton;
    private JButton openFileButton;
    private JButton saveFileButton;
    private JButton closeButton;
    private StatusBar statusBar;
   
    private final String ScriptFileExtension = "clu";

    public PanelPluginSelection panelPluginSelection;

    private Log log = LogFactory.getLog(MainForm.class);

    public MainForm() {
        $$$setupUI$$$();
        
        //contentPane.setPreferredSize(new Dimension(900, 480));

        // The optimize button shows a menu with available output formats
        optimizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                Optimize();
            }
        });

        configureButton.addActionListener(new ActionListener() {
          
          @Override
          public void actionPerformed(ActionEvent e) {
            new ConfigurationPanel(tabbedPane);
          }
        });

        // New file shows a menu with available code parsers
        newFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<CodeParserPlugin> plugins = new ArrayList<CodeParserPlugin>();
                plugins.addAll(Plugins.getCodeParserPlugins());
                Collections.sort(plugins, new PluginSorter());
                if (plugins.size() == 1) {
                    NewFileAction action = new NewFileAction(plugins.get(0), tabbedPane);
                    action.actionPerformed(null);
                } else {
                    JPopupMenu menu = new JPopupMenu("New File");
                    for (CodeParserPlugin plugin : plugins) {
                        menu.add(new NewFileAction(plugin, tabbedPane));
                    }
                    menu.show(newFileButton, 0, newFileButton.getBounds().height);
                }
            }
        });

        // Save file opens up a save file dialog
        saveFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Save(false);
            }
        });

        // The tabbed pane needs to enable/disable the save/optimize buttons based on the selected tab
        // The welcome tab cannot be saved and optimized
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (tabbedPane.getSelectedComponent() instanceof SourceFilePanel) {
                    saveFileButton.setEnabled(true);
                    optimizeButton.setEnabled(true);
                    closeButton.setEnabled(true);
                } else if (tabbedPane.getSelectedComponent() instanceof ConfigurationPanel) {
                	saveFileButton.setEnabled(false);
                	optimizeButton.setEnabled(false);
                	closeButton.setEnabled(true);
                } else {
                    saveFileButton.setEnabled(false);
                    optimizeButton.setEnabled(false);
                    closeButton.setEnabled(false);
                }
            }
        });

        // The open file button should open a menu with available code parsers
        openFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                List<CodeParserPlugin> plugins = new ArrayList<CodeParserPlugin>();
                plugins.addAll(Plugins.getCodeParserPlugins());
                Collections.sort(plugins, new PluginSorter());
                
                if (plugins.size() == 1) {
                    OpenFileAction action = new OpenFileAction(plugins.get(0), tabbedPane);
                    action.actionPerformed(null);
                } else {
                    JPopupMenu menu = new JPopupMenu("Open File");
                    for (CodeParserPlugin plugin : plugins) {
                        menu.add(new OpenFileAction(plugin, tabbedPane));
                    }
                    menu.show(openFileButton, 0, openFileButton.getBounds().height);
                }
            }
        });

        closeButton.setAction(new CloseAction(tabbedPane));

        // Make the welcome tab open hyperlinks in a new browser window
        welcomeToGaalopWelcomeEditorPane.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    URL u = e.getURL();
                    try {
                        Desktop.getDesktop().browse(u.toURI());
                    } catch (IOException e1) {
                        log.error(e1);
                    } catch (URISyntaxException e1) {
                        log.error(e1);
                    }
                }
            }
        });

        // Fill the welcome tab with content from welcome.html
        try {
            welcomeToGaalopWelcomeEditorPane.read(getClass().getResourceAsStream("welcome.html"), null);
        } catch (IOException e) {
            log.error("Unable to read welcome document.");
        }
    }
    
    private void Save(Boolean allowQuickSave)
    {
        Component component = tabbedPane.getSelectedComponent();
        if (component instanceof SourceFilePanel) {
            SourceFilePanel filePanel = (SourceFilePanel) component;
            File file = filePanel.getFile();

            // Check if file ends with correct extension => file was already saved manually
            String path = file.getAbsolutePath();
            if (allowQuickSave && path.endsWith(ScriptFileExtension)){
                statusBar.setStatus("Saved source: " + path);
                saveToFile(file, filePanel);
                return;
            }

            Main.lastDirectory = file.getParentFile();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(Main.lastDirectory);
            fileChooser.setSelectedFile(file);
            // Create a file filter to specify the extension
            fileChooser.setFileFilter(new FileNameExtensionFilter("CLUcalc files", ScriptFileExtension));

            int result = fileChooser.showSaveDialog(contentPane);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();

                // Check if the selected file already has the .glu extension or append it
                if (!filePath.toLowerCase().endsWith("." + ScriptFileExtension)) {
                    selectedFile = new File(filePath + "." + ScriptFileExtension);
                }

                saveToFile(selectedFile, filePanel);
            }
        }    
    }
    
    /*
    Is called after the Optimize button was clicked.
    */
    private void Optimize()
    {
        if (tabbedPane.getSelectedComponent() instanceof SourceFilePanel) {
            if (panelPluginSelection.areConstraintsFulfilled()) {
                panelPluginSelection.updateLastUsedPlugins();
                SourceFilePanel sourcePanel = (SourceFilePanel) tabbedPane.getSelectedComponent();
                CompileAction action = new CompileAction(sourcePanel, statusBar, panelPluginSelection);
                action.actionPerformed(null);
            } else {
                ErrorDialog.show(new CompilationException(panelPluginSelection.getErrorMessage()));
            }
        }
    }
    
    private void saveToFile(File toFile, SourceFilePanel sourceFilePanel) {
        try {
            PrintWriter printWriter = new PrintWriter(toFile);
            try {
                printWriter.print(sourceFilePanel.getInputFile().getContent());
            } finally {
                printWriter.close();
            }
        } catch (FileNotFoundException e) {
            log.warn(e);
            JOptionPane.showMessageDialog(contentPane, "Unable to save to " +
                    toFile.getAbsolutePath() + "\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        sourceFilePanel.setFile(toFile);
        sourceFilePanel.setSaved();
    }

    public Container getContentPane() {
        return contentPane;
    }
    
    public StatusBar getStatusBar() {
		return statusBar;
	}

    public void loadOpenedFiles() {
        Preferences prefs = Preferences.userNodeForPackage(MainForm.class);
        int count = prefs.getInt("count", 0);
        for (int i = 0; i < count; ++i) {
            String filename = prefs.get("file" + i, "");
            String pluginClass = prefs.get("plugin" + i, "");
            if (!filename.isEmpty()) {
                File file = new File(filename);
                CodeParserPlugin plugin = getParserPluginByName(pluginClass);
                if (plugin != null) {
                    String content  = getContentOfFile(filename);
                    if (content != null) {
                        SourceFilePanel panel = new SourceFilePanel(plugin, file, content);
                        tabbedPane.add(panel);
                        tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent(panel), panel.getTabLabel());
                    }
                } else {
                    log.warn("Code Parser Plugin of MRU file not found: " + pluginClass + " file: " + filename);
                }
            }
        }
    }

    private String getContentOfFile(String filename) {
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader reader = new BufferedReader(fileReader);

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
                result.append('\n');
            }

            return result.toString();
        } catch (IOException e) {
            log.warn("Unable to open MRU file " + filename, e);
            return null;
        }
    }

    private CodeParserPlugin getParserPluginByName(String pluginClass) {
        for (CodeParserPlugin plugin : Plugins.getCodeParserPlugins()) {
            if (plugin.getClass().getName().equals(pluginClass)) {
                return plugin;
            }
        }
        return null;
    }

    public void saveOpenedFiles() {
        List<SourceFilePanel> panels = new ArrayList<SourceFilePanel>(tabbedPane.getTabCount());
        for (int i = 0; i < tabbedPane.getTabCount(); ++i) {
            Component component = tabbedPane.getComponentAt(i);
            if (component instanceof SourceFilePanel) {
                panels.add((SourceFilePanel) component);
            }
        }

        Preferences prefs = Preferences.userNodeForPackage(MainForm.class);
        try {
            prefs.clear();
            int count = 0;

            for (SourceFilePanel panel : panels) {
                if (panel.getFileState() != FileState.UNSAVED) {
                    File file = panel.getFile();
                    prefs.put("file" + count, file.getAbsolutePath());
                    CodeParserPlugin plugin = panel.getParserPlugin();
                    prefs.put("plugin" + count, plugin.getClass().getName());
                    count++;
                }
            }

            prefs.putInt("count", count);
        } catch (BackingStoreException e) {
            log.warn("Unable to save opened files.", e);
        }
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        Font font = new Font("Arial", Font.PLAIN, FontSize.getGuiFontSize());

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        contentPane.add(panel1, BorderLayout.NORTH);
        logoIcon = new JLabel();
        logoIcon.setIcon(new ImageIcon(getClass().getResource("/de/gaalop/gui/logo.png")));
        logoIcon.setPreferredSize(new Dimension(660, 75));
        logoIcon.setText("");
        panel1.add(logoIcon, BorderLayout.WEST);
        final GaalopLogoFiller gaalopLogoFiller1 = new GaalopLogoFiller();
        panel1.add(gaalopLogoFiller1, BorderLayout.CENTER);
        final JToolBar toolBar1 = new JToolBar();
        toolBar1.setBorderPainted(true);
        toolBar1.setFloatable(false);
        panel1.add(toolBar1, BorderLayout.SOUTH);
        newFileButton = new JButton();
        newFileButton.setIcon(new ImageIcon(getClass().getResource("/de/gaalop/gui/document-new.png")));
        newFileButton.setText("New File");
        newFileButton.setMnemonic('N');
        newFileButton.setFont(font);
        newFileButton.setDisplayedMnemonicIndex(0);
        toolBar1.add(newFileButton);
        openFileButton = new JButton();
        openFileButton.setIcon(new ImageIcon(getClass().getResource("/de/gaalop/gui/document-open.png")));
        openFileButton.setText("Open File");
        openFileButton.setFont(font);
        openFileButton.setMnemonic('O');
        openFileButton.setDisplayedMnemonicIndex(0);
        toolBar1.add(openFileButton);
        saveFileButton = new JButton();
        saveFileButton.setEnabled(false);
        saveFileButton.setFont(font);
        saveFileButton.setIcon(new ImageIcon(getClass().getResource("/de/gaalop/gui/document-save.png")));
        saveFileButton.setText("Save File");
        saveFileButton.setMnemonic('S');
        saveFileButton.setDisplayedMnemonicIndex(0);
        toolBar1.add(saveFileButton);
        closeButton = new JButton();
        closeButton.setIcon(new ImageIcon(getClass().getResource("/de/gaalop/gui/emblem-unreadable.png")));
        closeButton.setFont(font);
        closeButton.setText("Close File");
        toolBar1.add(closeButton);
        final JToolBar.Separator toolBar$Separator2 = new JToolBar.Separator();
        toolBar1.add(toolBar$Separator2);
        configureButton = new JButton();
        configureButton.setFont(font);
        configureButton.setEnabled(true);
        configureButton.setIcon(new ImageIcon(getClass().getResource("/de/gaalop/gui/preferences-system.png")));
        configureButton.setText("Configure");
        configureButton.setMnemonic('C');
        configureButton.setDisplayedMnemonicIndex(3);
        toolBar1.add(configureButton);

        final JToolBar.Separator toolBar$Separator1 = new JToolBar.Separator();
        toolBar1.add(toolBar$Separator1);
        optimizeButton = new JButton();
        optimizeButton.setEnabled(false);
        optimizeButton.setIcon(new ImageIcon(getClass().getResource("/de/gaalop/gui/applications-system.png")));
        optimizeButton.setText("Optimize");
        optimizeButton.setFont(font);
        optimizeButton.setMnemonic('I');
        optimizeButton.setDisplayedMnemonicIndex(3);
        toolBar1.add(optimizeButton);
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(font);
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        panelPluginSelection = new PanelPluginSelection();
        contentPane.add(panelPluginSelection, BorderLayout.EAST);
//        final JPanel panel2 = new JPanel();
//        panel2.setLayout(new BorderLayout(0, 0));
//        tabbedPane.addTab("Welcome", panel2);
        welcomeToGaalopWelcomeEditorPane = new JEditorPane();
        welcomeToGaalopWelcomeEditorPane.setContentType("text/html");
        welcomeToGaalopWelcomeEditorPane.setFont(font);
        welcomeToGaalopWelcomeEditorPane.setEditable(false);
        welcomeToGaalopWelcomeEditorPane.setText("<html>\r\n  <head>\r\n    \r\n  </head>\r\n  <body>\r\n  </body>\r\n</html>\r\n");
//        panel2.add(welcomeToGaalopWelcomeEditorPane, BorderLayout.CENTER);
        statusBar = new StatusBar();
        contentPane.add(statusBar, BorderLayout.SOUTH);
        
                
        // Bind Ctrl+S to the save action
         Action actionOnCtrlS = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Save(true);
                Optimize();
            }
        };
        contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
            KeyStroke.getKeyStroke("control S"), "saveAction");
        contentPane.getActionMap().put("saveAction", actionOnCtrlS);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
