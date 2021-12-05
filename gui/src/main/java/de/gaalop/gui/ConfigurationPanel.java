package de.gaalop.gui;

import de.gaalop.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
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

import javax.swing.JButton;
import javax.swing.JFileChooser;

/**
 * This class represents the configuration panel.
 */
public class ConfigurationPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7710406876645754914L;

	private JTabbedPane pluginPanes = new JTabbedPane();

	public ConfigurationPanel(JTabbedPane tabbedPanel) {
		tabbedPanel.addTab("Configuration", this);
		int indexOfPanel = tabbedPanel.indexOfComponent(this);
		tabbedPanel.setSelectedIndex(indexOfPanel);

		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout());
		add(pluginPanes, BorderLayout.CENTER);
                Font font = new Font("Arial", Font.PLAIN, FontSize.getGuiFontSize());
                pluginPanes.setFont(font);
                for (CodeParserPlugin parser: Plugins.getCodeParserPlugins())
                    addPluginConfig(parser);
                for (GlobalSettingsStrategyPlugin global: Plugins.getGlobalSettingsStrategyPlugins())
                    addPluginConfig(global);
                for (VisualCodeInserterStrategyPlugin visStrat: Plugins.getVisualizerStrategyPlugins())
                    addPluginConfig(visStrat);
                for (AlgebraStrategyPlugin algebra: Plugins.getAlgebraStrategyPlugins())
                    addPluginConfig(algebra);
		for (OptimizationStrategyPlugin strategy : Plugins.getOptimizationStrategyPlugins()) 
                    addPluginConfig(strategy);
		
		List<CodeGeneratorPlugin> sortedPlugins = new ArrayList<CodeGeneratorPlugin>(Plugins.getCodeGeneratorPlugins());
		Collections.sort(sortedPlugins, new PluginSorter());
		for (CodeGeneratorPlugin generator : sortedPlugins) {
			addPluginConfig(generator);
		}
		pluginPanes.setSelectedIndex(0);
	}

	private void addPluginConfig(final Plugin plugin) {
                Font font = new Font("Arial", Font.PLAIN, FontSize.getGuiFontSize());
		JPanel configPanel = new JPanel();
		configPanel.setLayout(new BorderLayout());
		List<Field> properties = getConfigurationProperties(plugin);
		JPanel labels = new JPanel();
		labels.setLayout(new GridLayout(properties.size(), 1));
		JPanel fields = new JPanel();
		fields.setLayout(new GridLayout(properties.size(), 1));
		configPanel.add(labels, BorderLayout.WEST);
		configPanel.add(fields, BorderLayout.CENTER);
		for (final Field property : properties) {
                        JLabel label = new JLabel(property.getName());
                        label.setFont(font);
			labels.add(label);
			try {
				String value = BeanUtils.getProperty(plugin, property.getName());
				ConfigurationProperty clazz = property.getAnnotation(ConfigurationProperty.class);
				switch (clazz.type()) {
				case BOOLEAN:
					final JCheckBox checkBox = new JCheckBox();
                                        checkBox.setFont(font);
					checkBox.setSelected(Boolean.parseBoolean(value));
					checkBox.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								BeanUtils.setProperty(plugin, property.getName(), checkBox.isSelected());
							} catch (IllegalAccessException e1) {
								e1.printStackTrace();
							} catch (InvocationTargetException e1) {
								e1.printStackTrace();
							}
						}
						
					});
					fields.add(checkBox);
					break;
				case NUMBER:
					final JTextField numberField = new JTextField(value);
                                        numberField.setFont(font);
					numberField.addKeyListener(new KeyAdapter() {
						
						@Override
						public void keyReleased(KeyEvent e) {
							int intVal;
							if (numberField.getText().length() > 0) {
								intVal = Integer.parseInt(numberField.getText());
							} else {
								intVal = 0;
							}
							try {
								BeanUtils.setProperty(plugin, property.getName(), intVal);
							} catch (IllegalAccessException e1) {
								e1.printStackTrace();
							} catch (InvocationTargetException e1) {
								e1.printStackTrace();
							}
						}
						
					});
					fields.add(numberField);
					break;
                                case DIRPATH:
                                        final JTextField textField3 = new JTextField(value);
                                        textField3.setFont(font);
					textField3.addKeyListener(new KeyAdapter() {
						@Override
						public void keyReleased(KeyEvent e) {
							try {
								BeanUtils.setProperty(plugin, property.getName(), textField3.getText());
							} catch (IllegalAccessException e1) {
								e1.printStackTrace();
							} catch (InvocationTargetException e1) {
								e1.printStackTrace();
							}
						}
					});
                                        JPanel subPanel2 = new JPanel(new GridLayout(1, 3, 5, 5));
                                        subPanel2.add(textField3);
					fields.add(subPanel2);
                                        final JButton button3 = new JButton("Choose directory path");
                                        subPanel2.add(button3);
                                        button3.setFont(font);
                                        button3.addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                JFileChooser jFC = new JFileChooser();
                                                jFC.setCurrentDirectory(new File(textField3.getText()));
                                                jFC.setSelectedFile(new File(textField3.getText()));
                                                jFC.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                                                if (jFC.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                                                    textField3.setText(jFC.getSelectedFile().getAbsolutePath());
                                                    try {
                                                            BeanUtils.setProperty(plugin, property.getName(), textField3.getText());
                                                    } catch (IllegalAccessException e1) {
                                                            e1.printStackTrace();
                                                    } catch (InvocationTargetException e1) {
                                                            e1.printStackTrace();
                                                    }
                                                }
                                            }
                                        });
					break;
                                case FILEPATH:
                                        final JTextField textField2 = new JTextField(value);
                                        textField2.setFont(font);
					textField2.addKeyListener(new KeyAdapter() {
						@Override
						public void keyReleased(KeyEvent e) {
							try {
								BeanUtils.setProperty(plugin, property.getName(), textField2.getText());
							} catch (IllegalAccessException e1) {
								e1.printStackTrace();
							} catch (InvocationTargetException e1) {
								e1.printStackTrace();
							}
						}
					});
                                        JPanel subPanel = new JPanel(new GridLayout(1, 3, 5, 5));
                                        subPanel.add(textField2);
					fields.add(subPanel);
                                        final JButton button = new JButton("Choose file path");
                                        subPanel.add(button);
                                        button.setFont(font);
                                        button.addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                JFileChooser jFC = new JFileChooser();
                                                jFC.setCurrentDirectory(new File(textField2.getText()));
                                                jFC.setSelectedFile(new File(textField2.getText()));
                                                if (jFC.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                                                    textField2.setText(jFC.getSelectedFile().getAbsolutePath());
                                                    try {
                                                            BeanUtils.setProperty(plugin, property.getName(), textField2.getText());
                                                    } catch (IllegalAccessException e1) {
                                                            e1.printStackTrace();
                                                    } catch (InvocationTargetException e1) {
                                                            e1.printStackTrace();
                                                    }
                                                }
                                            }
                                        });
					break;    
				case TEXT:
					// fall through to default
				default:
					final JTextField textField = new JTextField(value);
                                        textField.setFont(font);
					textField.addKeyListener(new KeyAdapter() {
						@Override
						public void keyReleased(KeyEvent e) {
							try {
								BeanUtils.setProperty(plugin, property.getName(), textField.getText());
							} catch (IllegalAccessException e1) {
								e1.printStackTrace();
							} catch (InvocationTargetException e1) {
								e1.printStackTrace();
							}
						}
					});
					fields.add(textField);
					break;
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(configPanel, BorderLayout.NORTH);
		panel.add(new JPanel(), BorderLayout.CENTER);
                if (!properties.isEmpty()) //Hide panels with none parameters
                    pluginPanes.add(plugin.getName(), panel);
	}

	private List<Field> getConfigurationProperties(Plugin plugin) {
		List<Field> configProperties = new ArrayList<Field>();
		for (Field field : plugin.getClass().getFields()) {
			if (field.isAnnotationPresent(ConfigurationProperty.class)) {
				configProperties.add(field);
			}
		}
		return configProperties;
	}

}
