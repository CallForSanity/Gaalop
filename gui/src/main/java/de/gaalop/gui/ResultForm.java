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
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;

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
                
            // Tab size to 2
            Font f = textPane.getFont();
            FontMetrics fm = textPane.getFontMetrics(f);
            int width = fm.charWidth(' ');
            int tabSize = 4;
            int n = 100;

            TabStop[] tabs = new TabStop[n];
            for (int i=1;i<=n;i++)
                tabs[i-1] = new TabStop(width*tabSize*i, TabStop.ALIGN_LEFT, TabStop.LEAD_NONE);
            TabSet tabset = new TabSet(tabs);

            StyleContext cont = StyleContext.getDefaultStyleContext();
            AttributeSet a = cont.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.TabSet, tabset);
            textPane.setParagraphAttributes(a, false);
            
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
