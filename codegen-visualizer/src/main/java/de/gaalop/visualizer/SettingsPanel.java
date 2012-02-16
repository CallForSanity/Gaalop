/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gaalop.visualizer;

import java.awt.GridLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Implements a settings panel, where rendering options are managed
 * @author Christian
 */
public class SettingsPanel {
    
    private JCheckBox autoRendering = new JCheckBox("Automatic Rendering", false);
    private JTextField jTF_cubeLength = new JTextField("5"); 
    private JTextField jTF_density = new JTextField("0.1");

    public SettingsPanel(JPanel panel) {
        panel.setLayout(new GridLayout(3,1,5,5));
        
        
        panel.add(autoRendering);
        panel.add(new LabeledComponent("cube length", jTF_cubeLength));
        panel.add(new LabeledComponent("density", jTF_density));
    }
    
    public float getCubeLength() {
        return Float.parseFloat(jTF_cubeLength.getText());
    }
    
    public float getDensity() {
        return Float.parseFloat(jTF_density.getText());
    }
    
    public boolean isAutoRendering() {
        return autoRendering.isSelected();
    }
    
}
