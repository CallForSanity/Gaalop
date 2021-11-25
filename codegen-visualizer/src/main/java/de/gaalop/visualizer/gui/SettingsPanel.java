package de.gaalop.visualizer.gui;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * Implements a settings panel, where rendering options are managed
 * @author Christian
 */
public class SettingsPanel {
    
    private JCheckBox autoRendering = new JCheckBox("Automatic Rendering", false);
    private JTextField jTF_pointSize = new JTextField("0.2");
    private LabeledComponent lcPointSize = new LabeledComponent("point size", jTF_pointSize);
    
    private LinkedList<LabeledComponent> settings = new LinkedList<LabeledComponent>();
    private JScrollPane scrollPane;
    private JPanel panelSettings;

    public SettingsPanel(JScrollPane scrollPane, JPanel panelSettings) {
        this.scrollPane = scrollPane;
        this.panelSettings = panelSettings;
    }
    
    /**
     * Determines, if automatic rendering is active
     * @return <value>true</value> if automatic rendering is active, otherwise <value>false</value>
     */
    public boolean isAutoRendering() {
        return autoRendering.isSelected();
    }
    
    /**
     * Returns the render size of the points
     * @return The size of the points that are rendered
     */
    public float getPointSize() {
        return Float.parseFloat(jTF_pointSize.getText());
    }

    /**
     * Returns a map containing all settings of the selected zero finder method with its values
     * @return The map
     */
    public HashMap<String, String> getSettings() {
        HashMap<String, String> result = new HashMap<String, String>();
        for (LabeledComponent c: settings) 
            result.put(c.text, ((JTextField) c.component).getText());
        return result;
    }
    
    /**
     * Sets a map containing all settings of the selected zero finder method with its default values
     * @param settings The settings
     */
    public void setSettings(HashMap<String, String> settings) {
        this.settings.clear();
        panelSettings.removeAll();
        int rows = settings.size()+2;
        panelSettings.setLayout(new GridLayout(Math.max(9,rows),1,5,5));
        panelSettings.add(autoRendering);
        panelSettings.add(lcPointSize);
        for (String key: settings.keySet()) {
            LabeledComponent lc = new LabeledComponent(key, new JTextField(settings.get(key)));
            panelSettings.add(lc);
            this.settings.add(lc);
        }
        scrollPane.setViewportView(panelSettings);
    }
    
}
