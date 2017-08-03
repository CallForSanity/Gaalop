package de.gaalop.gui;

import de.gaalop.GlobalSettingsStrategyPlugin;
import de.gaalop.OutputFile;
import de.gaalop.Plugins;

import javax.swing.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Set;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * This form is used to display compilation results.
 */
public class ResultForm {
    private JTabbedPane tabbedPane;
    private JPanel contentPane;
    
    private Log log = LogFactory.getLog(ResultForm.class);

    public ResultForm(Set<OutputFile> files) {
        Font font = new Font("Arial", Font.PLAIN, FontSize.getGuiFontSize());
    	contentPane = new JPanel();
    	contentPane.setLayout(new BorderLayout(0, 0));
    	
    	JToolBar toolBar = new JToolBar();
    	toolBar.setBorderPainted(true);
    	toolBar.setFloatable(false);
    	contentPane.add(toolBar, BorderLayout.NORTH);
    	
    	JButton saveButton = new JButton("Save file");
        saveButton.setFont(font);
    	saveButton.setIcon(new ImageIcon(getClass().getResource("/de/gaalop/gui/document-save.png")));
    	saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Component component = tabbedPane.getSelectedComponent();
                if (component instanceof OutputFilePane) {
                    OutputFilePane outputFile = (OutputFilePane) component;
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setSelectedFile(new File(outputFile.getFile().getName()));
                    int result = fileChooser.showSaveDialog(contentPane);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        saveToFile(fileChooser.getSelectedFile(), outputFile);
                    }
                }
			}
		});
    	toolBar.add(saveButton);
    	
        tabbedPane = new JTabbedPane();
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        tabbedPane.setFont(font);

        for (OutputFile file : files) {
            JScrollPane filePane = new OutputFilePane(file);
            JTextPane textPane = new JTextPane();
            
            de.gaalop.globalSettings.Plugin globalSettings = null;
                for (GlobalSettingsStrategyPlugin p: Plugins.getGlobalSettingsStrategyPlugins()) 
                    if (p instanceof de.gaalop.globalSettings.Plugin)
                        globalSettings = (de.gaalop.globalSettings.Plugin) p;
                
                if (globalSettings != null) {
                    int size = globalSettings.getEditorFontSize();
                    textPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, size));
                } else {
                    textPane.setFont(Font.getFont(Font.SANS_SERIF));
                }
            
            textPane.setText(file.getContent());
            filePane.setViewportView(textPane);
            tabbedPane.add(file.getName(), filePane);
        }
    }

    public JPanel getContentPane() {
        return contentPane;
    }
    
    private void saveToFile(File toFile, OutputFilePane outputFilePanel) {
        try {
            PrintWriter printWriter = new PrintWriter(toFile);
            try {
                printWriter.print(outputFilePanel.getFile().getContent());
            } finally {
                printWriter.close();
            }
        } catch (FileNotFoundException e) {
            log.warn(e);
            JOptionPane.showMessageDialog(contentPane, "Unable to save to " +
                    toFile.getAbsolutePath() + "\n" + e, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
//        outputFilePanel.setSaved();
    }

}
