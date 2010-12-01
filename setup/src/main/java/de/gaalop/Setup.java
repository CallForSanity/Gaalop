package de.gaalop;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Setup extends JFrame {

	private static final String CONFIG_FILE_NAME = "gaalop.xml";
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel panelBin = null;
	private JPanel panelJava = null;
	private JTextField tfJava = null;
	private JButton selectJava = null;
	private JTextField tfBin = null;
	private JButton selectBin = null;
	private JPanel panelButtons = null;
	private JButton buttonSave = null;
	private JButton buttonExit = null;
	private JLabel labelBin = null;
	private JLabel labelJava = null;
	private JFileChooser chooser = null;

	private boolean changed;
	
	public static void main(String[] args) {
		Setup form = new Setup();
		form.setVisible(true);
	}

	/**
	 * This is the default constructor
	 */
	public Setup() {
		super();
		initialize();
		chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		read();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(514, 221);
		this.setContentPane(getJContentPane());
		this.setTitle("Gaalop Setup");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BoxLayout(getJContentPane(), BoxLayout.Y_AXIS));
			jContentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			jContentPane.add(getPanelJava(), null);
			jContentPane.add(getPanelBin(), null);
			jContentPane.add(getPanelButtons(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes panelBin
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPanelBin() {
		if (panelBin == null) {
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.anchor = GridBagConstraints.WEST;
			gridBagConstraints6.gridy = 0;
			labelBin = new JLabel();
			labelBin.setText("Please enter the path to the Maple binaries:");
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridy = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			gridBagConstraints2.gridx = 0;
			panelBin = new JPanel();
			panelBin.setLayout(new GridBagLayout());
			panelBin.add(getTfBin(), gridBagConstraints2);
			panelBin.add(getSelectBin(), gridBagConstraints3);
			panelBin.add(labelBin, gridBagConstraints6);
		}
		return panelBin;
	}

	/**
	 * This method initializes panelJava
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPanelJava() {
		if (panelJava == null) {
			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.gridx = 0;
			gridBagConstraints41.anchor = GridBagConstraints.WEST;
			gridBagConstraints41.gridy = 0;
			labelJava = new JLabel();
			labelJava.setText("Please enter the path to the Maple Java directory:");
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints.gridy = 1;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.gridx = 0;
			panelJava = new JPanel();
			panelJava.setLayout(new GridBagLayout());
			panelJava.add(getTfJava(), gridBagConstraints);
			panelJava.add(getSelectJava(), gridBagConstraints1);
			panelJava.add(labelJava, gridBagConstraints41);
		}
		return panelJava;
	}

	/**
	 * This method initializes tfJava
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfJava() {
		if (tfJava == null) {
			tfJava = new JTextField();
			tfJava.setPreferredSize(new Dimension(400, 20));
			tfJava.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					changed = true;
				}
			});
		}
		return tfJava;
	}

	/**
	 * This method initializes selectJava
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getSelectJava() {
		if (selectJava == null) {
			selectJava = new JButton();
			selectJava.setText("Select");
			selectJava.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int status = chooser.showOpenDialog(selectJava.getParent());
					if (status == JFileChooser.APPROVE_OPTION) {
						changed = true;
						tfJava.setText(chooser.getSelectedFile().getAbsolutePath());
					}
				}

			});
		}
		return selectJava;
	}

	/**
	 * This method initializes tfBin
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfBin() {
		if (tfBin == null) {
			tfBin = new JTextField();
			tfBin.setPreferredSize(new Dimension(400, 20));
			tfBin.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					changed = true;
				}
			});
		}
		return tfBin;
	}

	/**
	 * This method initializes selectBin
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getSelectBin() {
		if (selectBin == null) {
			selectBin = new JButton();
			selectBin.setText("Select");
			selectBin.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int status = chooser.showOpenDialog(selectJava.getParent());
					if (status == JFileChooser.APPROVE_OPTION) {
						changed = true;
						tfBin.setText(chooser.getSelectedFile().getAbsolutePath());
					}
				}
			});
		}
		return selectBin;
	}

	/**
	 * This method initializes panelButtons
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPanelButtons() {
		if (panelButtons == null) {
			panelButtons = new JPanel();
			panelButtons.setLayout(new FlowLayout());
			panelButtons.add(getButtonSave(), null);
			panelButtons.add(getButtonExit(), null);
		}
		return panelButtons;
	}

	/**
	 * This method initializes buttonSave
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getButtonSave() {
		if (buttonSave == null) {
			buttonSave = new JButton();
			buttonSave.setText("Save");
			buttonSave.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					save();
				}
			});
		}
		return buttonSave;
	}

	/**
	 * This method initializes buttonExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getButtonExit() {
		if (buttonExit == null) {
			buttonExit = new JButton();
			buttonExit.setText("Exit");
			buttonExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (changed) {
						int result = JOptionPane.showConfirmDialog(getContentPane(),
								"Settings have not been saved. Save now?");
						if (result == JOptionPane.YES_OPTION) {
							save();
						} else if (result == JOptionPane.NO_OPTION) {
							dispose();
						}
					} else {
						dispose();
					}
				}
			});
		}
		return buttonExit;
	}
	
	private String getBinKey() throws SecurityException, NoSuchFieldException {
		String className = "de.gaalop.maple.Plugin";
		String bin = "mapleBinaryPath";
		String binKey = className + "." + bin;
		return binKey;
	}
	
	private String getJavaKey() throws SecurityException, NoSuchFieldException {
		String className = "de.gaalop.maple.Plugin";
		String java = "mapleJavaPath";
		String javaKey = className + "." + java;
		return javaKey;
	}

	private void save() {
		if (changed) {
			Properties config = new Properties();
			try {
				config.put(getBinKey(), tfBin.getText());
				config.put(getJavaKey(), tfJava.getText());
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
			try {
				FileOutputStream fos = new FileOutputStream(CONFIG_FILE_NAME);
				try {
					config.storeToXML(fos, "Gaalop Configuration File");
					System.out.println("Configuration saved to " + CONFIG_FILE_NAME);
				} finally {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		changed = false;
	}
	
	private void read() {
		try {
			FileInputStream fis = new FileInputStream(CONFIG_FILE_NAME);
			Properties config = new Properties();
			config.loadFromXML(fis);
			tfBin.setText(config.getProperty(getBinKey()));
			tfJava.setText(config.getProperty(getJavaKey()));
		} catch (FileNotFoundException e) {
			System.out.println(CONFIG_FILE_NAME + " not found. No configuration file read.");
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

} // @jve:decl-index=0:visual-constraint="10,10"
