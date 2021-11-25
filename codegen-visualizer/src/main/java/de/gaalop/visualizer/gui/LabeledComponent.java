package de.gaalop.visualizer.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Implements a JPanel, which contains a label left to a arbitrary component
 * @author Christian
 */
public class LabeledComponent extends JPanel {
    
    public String text;
    public Component component;

    public LabeledComponent(String text, Component component) {
        this.text = text;
        this.component = component;
        setLayout(new BorderLayout(5,5));
        JLabel label = new JLabel(text);
        label.setLabelFor(component);
        add(label, BorderLayout.WEST);
        add(component, BorderLayout.CENTER);  
    }
}
