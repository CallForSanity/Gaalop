package de.gaalop.visualizer.gui;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.visualizer.ColorEvaluater;
import de.gaalop.visualizer.FindPointsThread;
import de.gaalop.visualizer.Plugin;
import de.gaalop.visualizer.PointCloud;
import de.gaalop.visualizer.Rendering;
import de.gaalop.visualizer.engines.lwjgl.SimpleLwJglRenderingEngine;
import de.gaalop.visualizer.zerofinding.RayMethod;
import de.gaalop.visualizer.zerofinding.ZeroFinder;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.event.ChangeEvent;

/**
 * Implements a frame-facade for all drawing operations
 * @author christian
 */
public class DrawSettingsCodeGen extends DrawSettings implements CodeGenerator, Rendering {
    
    public ControlFlowGraph graph;
    
    private Plugin plugin;
    
    public ZeroFinder finder;
    
    public HashMap<String, Color> colors;
    
    public boolean available = false;
        
    public HashMap<String, PointCloud> dataSet = null;

    private InputsPanel inputsPanel;
    private SettingsPanel settingsPanel;
    private VisiblePanel visiblePanel;

    public DrawSettingsCodeGen(Plugin plugin) {
        super();
        this.plugin = plugin;
        jButton_Repaint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recalculateCommand();
            }
        });
        
        inputsPanel = new InputsPanel(jPanel_Inputs) {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (settingsPanel.isAutoRendering()) recalculateCommand();
            }
        };
        settingsPanel = new SettingsPanel(jPanel_Settings);
        visiblePanel = new VisiblePanel(jPanel_Visible) {
            @Override
            public void stateChanged(ChangeEvent e) {
               repaintCommand();
            }
        };
    }
    
    @Override
    public Set<OutputFile> generate(ControlFlowGraph in) throws CodeGeneratorException {

        this.graph = in;
        finder = new RayMethod(plugin.maximaCommand);
        colors = ColorEvaluater.getColors(graph);
        finder.prepareGraph(in);
        
        //Determine all input variables
        LinkedList<String> inputs = new LinkedList<String>();
        for (Variable inputVar: in.getInputVariables()) 
            if (!finder.isPositionVariable(inputVar.getName()))
                inputs.add(inputVar.getName());
        
        Collections.sort(inputs);
        inputsPanel.setInputs(inputs);
        
        setVisible(true);
        setSize(getSize().width+1, getSize().height);
        setSize(getSize().width-1, getSize().height);
        
        // start engine
        SimpleLwJglRenderingEngine engine = new SimpleLwJglRenderingEngine(plugin.lwJglNativePath, this);
        
        engine.start();
        return new HashSet<OutputFile>();
    }
    
    /**
     * Is called, when finding is finished.
     * Shows all visible objects in the VisiblePanel
     */
    public void findingComplete() {
        visiblePanel.setObjects(dataSet.keySet(), graph.getRenderingExpressions());
    }
    
    /**
     * Recalculate points
     */
    private void recalculateCommand() {
        finder.cubeEdgeLength = settingsPanel.getCubeLength();
        finder.density = settingsPanel.getDensity();
        jLabel_Info.setText("Please wait while rendering ...");
        jLabel_Info.repaint();
        HashMap<MultivectorComponent, Double> globalValues = new HashMap<MultivectorComponent, Double>(); //Sliders, ...
        //fill global values
        HashMap<String, Double> inputValues = inputsPanel.getValues();
        for (String variable: inputValues.keySet()) 
            globalValues.put(new MultivectorComponent(variable, 0), inputValues.get(variable));

        FindPointsThread thread = new FindPointsThread(globalValues, this);
        thread.start();
    }
    
    /**
     * Repaint the visualization window
     */
    private void repaintCommand() {
        available = true;
    }

    @Override
    public boolean isNewDataSetAvailable() {
        return available;
    }

    @Override
    public HashMap<String, PointCloud> getDataSet() {
        available = false;
        return dataSet;
    }

    @Override
    public HashSet<String> getVisibleObjects() {
        return visiblePanel.getVisibleObjects();
    }
 
}
