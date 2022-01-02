package de.gaalop.gui;

import de.gaalop.*;
import de.gaalop.algebra.DefinedAlgebra;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author Christian Steinmetz
 */
public class PanelPluginSelection extends JPanel {
    
    
    private JComboBox globalSettings; 
    private JComboBox visualCodeInserter; 
    private JComboBox algebra; 
    private JComboBox optimization;
    private JComboBox generator;
    
    private String errorMessage;
    private String errorPlugin1;
    private String errorPlugin2;
    
    private Color defaultColor;
    
    private JTextArea errorTextArea = new JTextArea();
    
    private JComboBox algebraChooser = new JComboBox();
    
    public static String lastUsedAlgebra;
    public static boolean lastUsedAlgebraRessource;
    
    public static String lastUsedGenerator;
    public static String lastUsedVisualCodeInserter;
    public static String lastUsedOptimization;
    
    
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
        final Font font = new Font("Arial", Font.PLAIN, FontSize.getGuiFontSize());
        setLayout(new GridLayout(7,1,5,5));
        
        ListCellRenderer c = new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value == null) return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                String valueStr = ((Plugin) value).getName();
                JLabel label = (JLabel) super.getListCellRendererComponent(list, valueStr, index, isSelected, cellHasFocus);
                label.setFont(font);
                Image icon = (((Plugin) value).getIcon());
                if (icon != null)
                    label.setIcon(new ImageIcon(((Plugin) value).getIcon()));
                return label ;
            }
        };
        add(new JPanel());

        algebraChooser.setFont(font);
        addLabeledComponent("Algebra to use:", algebraChooser);
        
        PluginSorter comparator = new PluginSorter();
        
        
        GlobalSettingsStrategyPlugin[] globalPlugins = Plugins.getGlobalSettingsStrategyPlugins().toArray(new GlobalSettingsStrategyPlugin[0]);
        Arrays.sort(globalPlugins, comparator);
        globalSettings = new JComboBox(globalPlugins);
        globalSettings.setFont(font);
        globalSettings.setSelectedItem(search(globalPlugins, "de.gaalop.globalSettings.Plugin"));
        globalSettings.addItemListener(itemListener);
        globalSettings.setRenderer(c);
        if (Plugins.getGlobalSettingsStrategyPlugins().size() > 1)
            addLabeledComponent("Global Settings Plugin:", globalSettings);
        
        VisualCodeInserterStrategyPlugin[] visPlugins = Plugins.getVisualizerStrategyPlugins().toArray(new VisualCodeInserterStrategyPlugin[0]);
        Arrays.sort(visPlugins, comparator);
        visualCodeInserter = new JComboBox(visPlugins);
        visualCodeInserter.setFont(font);
        visualCodeInserter.setSelectedItem(search(visPlugins, "de.gaalop.visualCodeInserter.Plugin"));
        visualCodeInserter.addItemListener(itemListener);
        visualCodeInserter.setRenderer(c);
        if (Plugins.getVisualizerStrategyPlugins().size() > 1)
            addLabeledComponent("VisualCodeInserter:", visualCodeInserter);
        
        AlgebraStrategyPlugin[] algPlugins = Plugins.getAlgebraStrategyPlugins().toArray(new AlgebraStrategyPlugin[0]);
        Arrays.sort(algPlugins, comparator);
        algebra = new JComboBox(algPlugins);
        algebra.setSelectedItem(search(algPlugins, "de.gaalop.algebra.Plugin"));
        algebra.addItemListener(itemListener);
        algebra.setFont(font);
        algebra.setRenderer(c);
        if (Plugins.getAlgebraStrategyPlugins().size() > 1)
            addLabeledComponent("Algebra:", algebra);
        
        OptimizationStrategyPlugin[] optPlugins = Plugins.getOptimizationStrategyPlugins().toArray(new OptimizationStrategyPlugin[0]);
        Arrays.sort(optPlugins, comparator);
        optimization = new JComboBox(optPlugins);
        optimization.setFont(font);
        optimization.setSelectedItem(search(optPlugins, "de.gaalop.tba.Plugin"));
        optimization.addItemListener(itemListener);
        optimization.setRenderer(c);
        if (Plugins.getOptimizationStrategyPlugins().size() > 1)
            addLabeledComponent("Optimization:", optimization);
        
        CodeGeneratorPlugin[] codegenPlugins = Plugins.getCodeGeneratorPlugins().toArray(new CodeGeneratorPlugin[0]);
        Arrays.sort(codegenPlugins, comparator);
        generator = new JComboBox(codegenPlugins);
        generator.setFont(font);
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
        Font font = new Font("Arial", Font.PLAIN, FontSize.getGuiFontSize());
        JPanel panel = new JPanel(new GridLayout(2,1));
        JLabel l = new JLabel(label);
        l.setFont(font);
        panel.add(l);
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
        if (!depends(
                "de.gaalop.gappDebugger.Plugin",
                "de.gaalop.gapp.Plugin",
                "The GAPP debugger needs the GAPP Optimizer!",
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public void refreshAlgebras() {
        VisualCodeInserterStrategyPlugin[] visualCodeInserterStrategyPlugins = Plugins.getVisualizerStrategyPlugins().toArray(new VisualCodeInserterStrategyPlugin[0]);
        visualCodeInserter.setSelectedItem(search(visualCodeInserterStrategyPlugins, lastUsedVisualCodeInserter));
        
        OptimizationStrategyPlugin[] optimizationPlugins = Plugins.getOptimizationStrategyPlugins().toArray(new OptimizationStrategyPlugin[0]);
        optimization.setSelectedItem(search(optimizationPlugins, lastUsedOptimization));
        
        CodeGeneratorPlugin[] codegenPlugins = Plugins.getCodeGeneratorPlugins().toArray(new CodeGeneratorPlugin[0]);
        generator.setSelectedItem(search(codegenPlugins, lastUsedGenerator));
        
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        
        for (DefinedAlgebra definedAlgebra: de.gaalop.algebra.Plugin.getDefinedAlgebras())
            model.addElement(new AlgebraChooserItem(true, definedAlgebra.id, definedAlgebra.id+" - "+definedAlgebra.name));

        AlgebraStrategyPlugin algebra = Plugins.getAlgebraStrategyPlugins().iterator().next();
        
        try {
             Field field = algebra.getClass().getField("additionalBaseDirectory");
             String value = BeanUtils.getProperty(algebra, field.getName()).trim();
             
             if (!value.isEmpty()) {
                File file = new File(value);
                File[] dirs = file.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return pathname.isDirectory();
                    }
                });
                if (dirs != null)
                for (File dir: dirs) 
                    model.addElement(new AlgebraChooserItem(false, dir.getName(), "Own - "+dir.getName()));

                }
             
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PanelPluginSelection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(PanelPluginSelection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(PanelPluginSelection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(PanelPluginSelection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(PanelPluginSelection.class.getName()).log(Level.SEVERE, null, ex);
        }
        algebraChooser.setModel(model);
        
        if (lastUsedAlgebra == null)  {
            lastUsedAlgebra = "cga";
            lastUsedAlgebraRessource = true;
        }
        
        

        AlgebraChooserItem defaultItem = new AlgebraChooserItem(lastUsedAlgebraRessource, lastUsedAlgebra, "");
        
        FOR:
        for (int i=0;i<model.getSize();i++) {
            AlgebraChooserItem iItem = (AlgebraChooserItem) model.getElementAt(i);
            if (defaultItem.algebraName.equals(iItem.algebraName) && defaultItem.ressource == iItem.ressource) {
                defaultItem = iItem;
                break FOR;
            }
        }
        
        algebraChooser.setSelectedItem(defaultItem);
    }

    public AlgebraChooserItem getAlgebraToUse() {
        return (AlgebraChooserItem) algebraChooser.getSelectedItem();
    }

    public void updateLastUsedPlugins() {
        lastUsedGenerator = generator.getSelectedItem().getClass().getCanonicalName();
        lastUsedVisualCodeInserter = visualCodeInserter.getSelectedItem().getClass().getCanonicalName();
        lastUsedOptimization = optimization.getSelectedItem().getClass().getCanonicalName();
    }

}
