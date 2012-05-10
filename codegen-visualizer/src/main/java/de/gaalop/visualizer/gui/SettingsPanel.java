package de.gaalop.visualizer.gui;

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
    private JCheckBox render2d = new JCheckBox("Render2d", false);

    public SettingsPanel(JPanel panel) {
        panel.setLayout(new GridLayout(3,1,5,5));
        panel.add(autoRendering);
        panel.add(new LabeledComponent("cube length", jTF_cubeLength));
        panel.add(new LabeledComponent("density", jTF_density));
        panel.add(new LabeledComponent("render2d", render2d));
    }
    
    /**
     * Return the half length of the cube to render
     * @return The half length of the cube to render
     */
    public float getCubeLength() {
        return Float.parseFloat(jTF_cubeLength.getText());
    }
    
    /**
     * Returns the density of the rendering
     * @return The density
     */
    public float getDensity() {
        return Float.parseFloat(jTF_density.getText());
    }
    
    /**
     * Determines, if automatic rendering is active
     * @return <value>true</value> if automatic rendering is active, otherwise <value>false</value>
     */
    public boolean isAutoRendering() {
        return autoRendering.isSelected();
    }

    /**
     * Determines, if rendering in 2d is active
     * @return <value>true</value> if rendering in 2d is active, otherwise <value>false</value>
     */
    public boolean getRender2d() {
        return render2d.isSelected();
    }
    
    
    
}
