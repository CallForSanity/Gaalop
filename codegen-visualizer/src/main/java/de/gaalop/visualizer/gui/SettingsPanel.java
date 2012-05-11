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
    private JCheckBox render2d = new JCheckBox("render2d", false);
    private JTextField jTF_pointSize = new JTextField("0.2");

    public SettingsPanel(JPanel panel) {
        panel.setLayout(new GridLayout(5,1,5,5));
        panel.add(autoRendering);
        panel.add(new LabeledComponent("cube length", jTF_cubeLength));
        panel.add(new LabeledComponent("density", jTF_density));
        panel.add(render2d);
        panel.add(new LabeledComponent("point size", jTF_pointSize));
    }
    
    /**
     * Returns the half length of the cube to render
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
    
    /**
     * Returns the render size of the points
     * @return The size of the points that are rendered
     */
    public float getPointSize() {
        return Float.parseFloat(jTF_pointSize.getText());
    }
    
}
