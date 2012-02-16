package de.gaalop.visualizer.gui;

import de.gaalop.dfg.Expression;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;

/**
 * Contains a panel which shows all visible objects
 * @author Christian
 */
public abstract class VisiblePanel implements ChangeListener {
    
    private JPanel panel;
    
    private HashMap<JCheckBox, String> boxes = new HashMap<JCheckBox, String>();

    public VisiblePanel(JPanel panel) {
        this.panel = panel;
    }

    /**
     * Sets all objects
     * @param names The names of the objects
     * @param renderingExpressions The expressions of the objects
     */
    public void setObjects(Set<String> names, HashMap<String, Expression> renderingExpressions) {
        panel.removeAll();
        
        panel.setSize(panel.getWidth(),25*names.size());
        panel.setLayout(new GridLayout((names.size() < 8) ? 8-names.size(): names.size(), 1, 5, 5));
        
        boxes.clear();
        for (String input: names) {
            JCheckBox box = new JCheckBox(renderingExpressions.get(input.substring(0, input.length()-2)).toString(), true);
            box.addChangeListener(this);
            boxes.put(box, input);
            panel.add(box);
        }
    }
    
    /**
     * Returns the set of visible objects
     * @return The set of visible objects
     */
    public HashSet<String> getVisibleObjects() {
        HashSet<String> result = new HashSet<String>();
        for (JCheckBox box: boxes.keySet()) 
            if (box.isSelected())
                result.add(boxes.get(box));
        return result;
    }
    
}
