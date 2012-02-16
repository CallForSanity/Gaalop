/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.gaalop.visualizer;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Christian
 */
public class LabeledComponent extends JPanel {

    public LabeledComponent(String text, Component component) {
        setLayout(new BorderLayout(5,5));
        JLabel label = new JLabel(text);
        label.setLabelFor(component);
        add(label, BorderLayout.WEST);
        add(component, BorderLayout.CENTER);  
    }
    
    
    
}
