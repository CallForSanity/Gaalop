package de.gaalop.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.apache.commons.beanutils.BeanUtils;

import de.gaalop.CodeGeneratorPlugin;
import de.gaalop.ConfigurationProperty;
import de.gaalop.Plugins;

/**
 * This class represents the configuration panel.
 */
public class ConfigurationPanel extends JPanel {

	private JTabbedPane tabbedPanel;

	/**
	 * 
	 */
	private static final long serialVersionUID = 7710406876645754914L;
	
	private JTabbedPane pluginPanes = new JTabbedPane();

	public ConfigurationPanel(JTabbedPane tabbedPanel) {
		this.tabbedPanel = tabbedPanel;
		tabbedPanel.addTab("Configuration", this);
		int indexOfPanel = tabbedPanel.indexOfComponent(this);
		tabbedPanel.setSelectedIndex(indexOfPanel);
		
		initialize();
	}
	
	private void initialize() {
		setLayout(new BorderLayout());
		add(pluginPanes, BorderLayout.CENTER);		
		List<CodeGeneratorPlugin> sortedPlugins = new ArrayList<CodeGeneratorPlugin>(Plugins.getCodeGeneratorPlugins());
		Collections.sort(sortedPlugins, new PluginSorter());
		for (final CodeGeneratorPlugin generator : sortedPlugins) {
			JPanel panel = new JPanel();
			List<Field> configProperties = new ArrayList<Field>();
			for (Field field : generator.getClass().getFields()) {
				if (field.isAnnotationPresent(ConfigurationProperty.class)) {
					configProperties.add(field);
				}
			}
			for (final Field property : configProperties) {
				panel.add(new JLabel(property.getName()));
				// TODO: add text field / check box etc. according to type
				try {
					String value = BeanUtils.getProperty(generator, property.getName());
					if (property.getType().getName().equals("boolean")) {
						final JCheckBox checkBox = new JCheckBox();
						checkBox.setSelected(Boolean.parseBoolean(value));
						checkBox.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								try {
									BeanUtils.setProperty(generator, property.getName(), checkBox.isSelected());
								} catch (IllegalAccessException e1) {
									e1.printStackTrace();
								} catch (InvocationTargetException e1) {
									e1.printStackTrace();
								}
							}
						});
						panel.add(checkBox);
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
			pluginPanes.add(generator.getName(), panel);
		}
		pluginPanes.setSelectedIndex(0);
	}

}
