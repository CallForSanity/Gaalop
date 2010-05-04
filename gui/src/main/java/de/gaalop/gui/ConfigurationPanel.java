package de.gaalop.gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * This class represents the configuration panel.
 */
public class ConfigurationPanel extends JPanel {

	private JTabbedPane tabbedPanel;

	/**
	 * 
	 */
	private static final long serialVersionUID = 7710406876645754914L;

	public ConfigurationPanel(JTabbedPane tabbedPanel) {
		add(new JLabel("One common panel for global settings plus one tab per plugin..."), BorderLayout.CENTER);
		this.tabbedPanel = tabbedPanel;
		tabbedPanel.addTab("Configuration", this);
		int indexOfPanel = tabbedPanel.indexOfComponent(this);
		tabbedPanel.setSelectedIndex(indexOfPanel);
	}

}
