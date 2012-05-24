package de.gaalop.gui;

import de.gaalop.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;

/**
 *
 * @author Christian Steinmetz
 */
public class PanelPluginSelection extends JPanel {
    
    private JComboBox<GlobalSettingsStrategyPlugin> globalSettings; 
    private JComboBox<VisualCodeInserterStrategyPlugin> visualCodeInserter; 
    private JComboBox<AlgebraStrategyPlugin> algebra; 
    private JComboBox<OptimizationStrategyPlugin> optimization;
    private JComboBox<CodeGeneratorPlugin> generator;
    
    private String errorMessage;
    private String errorPlugin1;
    private String errorPlugin2;
    
    private Color defaultColor;
    
    private JTextArea errorTextArea = new JTextArea();
    
    private ItemListener itemListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (areConstraintsFulfilled()) {
                errorTextArea.setText("");
                setColorOnAllComboBoxes();
            } else {
                errorTextArea.setText(errorMessage);
                setColorOnAllComboBoxes();
            }
        }
    };
    
    private boolean isErrorPlugin(JComboBox comboBox) {
        String name = comboBox.getSelectedItem().getClass().getCanonicalName();
        return name.equals(errorPlugin1) || name.equals(errorPlugin2);
    }
    
    private void setColorOnAllComboBoxes() {
        if (isErrorPlugin(visualCodeInserter))
            visualCodeInserter.setBackground(Color.red);
        else
            visualCodeInserter.setBackground(defaultColor);
        
        if (isErrorPlugin(algebra))
            algebra.setBackground(Color.red); 
        else
            algebra.setBackground(defaultColor);
        
        if (isErrorPlugin(optimization))
            optimization.setBackground(Color.red); 
        else
            optimization.setBackground(defaultColor);
        
        if (isErrorPlugin(generator))
            generator.setBackground(Color.red); 
        else
            generator.setBackground(defaultColor);
    }
    
    public PanelPluginSelection() {
        setLayout(new GridLayout(7,1,5,5));
        
        ListCellRenderer c = new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value == null) return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                String valueStr = ((Plugin) value).getName();
                JLabel label = (JLabel) super.getListCellRendererComponent(list, valueStr, index, isSelected, cellHasFocus);
                Image icon = (((Plugin) value).getIcon());
                if (icon != null)
                    label.setIcon(new ImageIcon(((Plugin) value).getIcon()));
                return label ;
            }
        };
        add(new JPanel());
        
        PluginSorter comparator = new PluginSorter();
        
        
        GlobalSettingsStrategyPlugin[] globalPlugins = Plugins.getGlobalSettingsStrategyPlugins().toArray(new GlobalSettingsStrategyPlugin[0]);
        Arrays.sort(globalPlugins, comparator);
        globalSettings = new JComboBox<GlobalSettingsStrategyPlugin>(globalPlugins);
        globalSettings.setSelectedItem(search(globalPlugins, "de.gaalop.globalSettings.Plugin"));
        globalSettings.addItemListener(itemListener);
        globalSettings.setRenderer(c);
        if (Plugins.getGlobalSettingsStrategyPlugins().size() > 1)
            addLabeledComponent("Global Settings Plugin:", globalSettings);
        
        VisualCodeInserterStrategyPlugin[] visPlugins = Plugins.getVisualizerStrategyPlugins().toArray(new VisualCodeInserterStrategyPlugin[0]);
        Arrays.sort(visPlugins, comparator);
        visualCodeInserter = new JComboBox<VisualCodeInserterStrategyPlugin>(visPlugins);
        visualCodeInserter.setSelectedItem(search(visPlugins, "de.gaalop.visualCodeInserter.Plugin"));
        visualCodeInserter.addItemListener(itemListener);
        visualCodeInserter.setRenderer(c);
        if (Plugins.getVisualizerStrategyPlugins().size() > 1)
            addLabeledComponent("VisualCodeInserter:", visualCodeInserter);
        
        AlgebraStrategyPlugin[] algPlugins = Plugins.getAlgebraStrategyPlugins().toArray(new AlgebraStrategyPlugin[0]);
        Arrays.sort(algPlugins, comparator);
        algebra = new JComboBox<AlgebraStrategyPlugin>(algPlugins);
        algebra.setSelectedItem(search(algPlugins, "de.gaalop.algebra.Plugin"));
        algebra.addItemListener(itemListener);
        algebra.setRenderer(c);
        if (Plugins.getAlgebraStrategyPlugins().size() > 1)
            addLabeledComponent("Algebra:", algebra);
        
        OptimizationStrategyPlugin[] optPlugins = Plugins.getOptimizationStrategyPlugins().toArray(new OptimizationStrategyPlugin[0]);
        Arrays.sort(optPlugins, comparator);
        optimization = new JComboBox<OptimizationStrategyPlugin>(optPlugins);
        optimization.setSelectedItem(search(optPlugins, "de.gaalop.tba.Plugin"));
        optimization.addItemListener(itemListener);
        optimization.setRenderer(c);
        if (Plugins.getOptimizationStrategyPlugins().size() > 1)
            addLabeledComponent("Optimization:", optimization);
        
        CodeGeneratorPlugin[] codegenPlugins = Plugins.getCodeGeneratorPlugins().toArray(new CodeGeneratorPlugin[0]);
        Arrays.sort(codegenPlugins, comparator);
        generator = new JComboBox<CodeGeneratorPlugin>(codegenPlugins);
        generator.setSelectedItem(search(codegenPlugins, "de.gaalop.cpp.Plugin"));
        generator.addItemListener(itemListener);
        generator.setRenderer(c);
        if (Plugins.getCodeGeneratorPlugins().size() > 1)
            addLabeledComponent("CodeGenerator:", generator);
        
        errorTextArea.setLineWrap(true);
        errorTextArea.setWrapStyleWord(true);
        errorTextArea.setBackground(getBackground());
        add(errorTextArea);
        defaultColor = generator.getBackground();
    }
    
    private void addLabeledComponent(String label, JComboBox c) {
        JPanel panel = new JPanel(new GridLayout(2,1));
        panel.add(new JLabel(label));
        panel.add(c);
        add(panel);
    }
    
    public GlobalSettingsStrategyPlugin getGlobalSettingsStrategyPlugin() {
        return (GlobalSettingsStrategyPlugin) globalSettings.getSelectedItem();
    }
    
    public VisualCodeInserterStrategyPlugin getVisualizerStrategyPlugin() {
        return (VisualCodeInserterStrategyPlugin) visualCodeInserter.getSelectedItem();
    }
    
    public AlgebraStrategyPlugin getAlgebraStrategyPlugin() {
        return (AlgebraStrategyPlugin) algebra.getSelectedItem();
    }
    
    public OptimizationStrategyPlugin getOptimizationStrategyPlugin() {
        return (OptimizationStrategyPlugin) optimization.getSelectedItem();
    }
    
    public CodeGeneratorPlugin getCodeGeneratorPlugin() {
        return (CodeGeneratorPlugin) generator.getSelectedItem();
    }

    private Object search(Plugin[] plugins, String search) {
        for (Plugin p: plugins) 
            if (p.getClass().getCanonicalName().equals(search))
                return p;
        
        if (plugins.length>0)
            return plugins[0];
        
        return null;
    }
    
    public boolean areConstraintsFulfilled() {
        errorMessage = "";
        errorPlugin1 = null;
        errorPlugin2 = null;
        LinkedList<Plugin> plugins = new LinkedList<Plugin>();
        plugins.add(getVisualizerStrategyPlugin());
        plugins.add(getAlgebraStrategyPlugin());
        plugins.add(getOptimizationStrategyPlugin());
        plugins.add(getCodeGeneratorPlugin());
        
        if (!depends(
                "de.gaalop.codegenGapp.Plugin",
                "de.gaalop.gapp.Plugin",
                "The GAPPCodeGenerator needs the GAPP Optimizer!", 
                plugins)) 
            return false;
        if (!depends(
                "de.gaalop.gappopencl.Plugin",
                "de.gaalop.gapp.Plugin",
                "The GAPPOpenCL codeGenerator needs the GAPP Optimizer!",
                plugins)) 
            return false;
        if (!depends(
                "de.gaalop.vis2d.Plugin",
                "de.gaalop.visualCodeInserter2d.Plugin",
                "The Vis2d plugin needs the visualCodeInserter2d plugin!",
                plugins))
            return false;
        if (!depends(
                "de.gaalop.visualizer.Plugin",
                "de.gaalop.visualCodeInserter.Plugin",
                "The Visualizer plugin needs the visualCodeInserter plugin!",
                plugins)) 
            return false;
        
        return true;
    }

    private boolean contains(String plugin, LinkedList<Plugin> plugins) {
        for (Plugin p: plugins) 
            if (p.getClass().getCanonicalName().equals(plugin))
                return true;
        
        return false;
    }
    
    private boolean depends(String plugin, String needs, String message, LinkedList<Plugin> plugins) {
        if (contains(plugin, plugins))
            if (!contains(needs, plugins)) {
                errorMessage = message;
                errorPlugin1 = needs;
                errorPlugin2 = plugin;
                return false;
            }
        return true;
    }

}
