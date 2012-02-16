/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gaalop.visualizer;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Christian
 */
public abstract class InputsPanel implements ChangeListener {
    
    private JPanel panel;
    
    private HashMap<JSpinner, String> mapSpinners = new HashMap<JSpinner, String>();

    public InputsPanel(JPanel panel) {
        this.panel = panel;
    }
    
    public void setInputs(LinkedList<String> inputs) {
        panel.removeAll();
        
        panel.setSize(panel.getWidth(),25*inputs.size());
        panel.setLayout(new GridLayout((inputs.size() < 8) ? 8-inputs.size(): inputs.size(), 1, 5, 5));
        
        mapSpinners.clear();
        for (String input: inputs) {
            JSpinner spinner = new JSpinner(new SpinnerNumberModel());
            mapSpinners.put(spinner, input);
            panel.add(new LabeledComponent(input+":", spinner));
            spinner.addChangeListener(this);
        }
    }
    
    public HashMap<String, Double> getValues() {
        HashMap<String, Double> result = new HashMap<String, Double>();
        for (JSpinner spinner: mapSpinners.keySet()) 
            result.put(mapSpinners.get(spinner), ((Number) spinner.getValue()).doubleValue());
        return result;
    }
    
}
