package de.gaalop.visualizer.gui;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.visualizer.*;
import de.gaalop.visualizer.engines.lwjgl.LwJglRenderingEngine2;
import de.gaalop.visualizer.engines.lwjgl.RenderingEngine;
import de.gaalop.visualizer.engines.lwjgl.SimpleLwJglRenderingEngine;
import de.gaalop.visualizer.zerofinding.DiscreteCubeMethod;
import de.gaalop.visualizer.zerofinding.RayMethod;
import de.gaalop.visualizer.zerofinding.ZeroFinder;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;

/**
 * Implements a frame-facade for all drawing operations
 * @author christian
 */
public class DrawSettingsCodeGen extends DrawSettings implements CodeGenerator, Rendering {
    
    public ControlFlowGraph graph;
    
    private Plugin plugin;
    
    public ZeroFinder finder;
    boolean rayMethod = false;
    
    public HashMap<String, Color> colors;
    
    public boolean available = false;
        
    public HashMap<String, PointCloud> dataSet = null;

    private InputsPanel inputsPanel;
    private SettingsPanel settingsPanel;
    private VisiblePanel visiblePanel;
    
    public LinkedList<Point3d> points = new LinkedList<Point3d>();
    
    public boolean engineStarted = false;
    
    private RenderingEngine engine;

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
        
        jButton_LoadPointCloud.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    BufferedReader reader = null;
                    try {
                        reader = new BufferedReader(new FileReader(chooser.getSelectedFile()));
                        points.clear();
                        String line;
                        try {
                            while ((line = reader.readLine()) != null) {
                                String[] parts = line.split(",");
                                points.add(new Point3d(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]), Double.parseDouble(parts[2])));
                            }
                            reader.close();
                        } catch (IOException ex) {
                            Logger.getLogger(DrawSettingsCodeGen.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        recalculateCommand();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(DrawSettingsCodeGen.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            reader.close();
                        } catch (IOException ex) {
                            Logger.getLogger(DrawSettingsCodeGen.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
        
        jButton_SavePointCloud.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    PrintWriter writer = null;
                    try {
                        writer = new PrintWriter(chooser.getSelectedFile());
                        for (String key: dataSet.keySet()) {
                            PointCloud cloud = dataSet.get(key);
                            cloud.print(writer);
                        }
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(DrawSettingsCodeGen.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        writer.close();
                    }
                }
            }
        });
    }
    
    @Override
    public Set<OutputFile> generate(ControlFlowGraph in) throws CodeGeneratorException {
        
        this.graph = in;

        if (plugin.zeroFindingMethod.equalsIgnoreCase("Ray method")) 
            finder = new RayMethod(in.globalSettings.maximaCommand);
        else
            if (plugin.zeroFindingMethod.equalsIgnoreCase("Discrete cube method")) 
                finder = new DiscreteCubeMethod();
        
        if (finder == null)
            finder = new RayMethod(in.globalSettings.maximaCommand);
        
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
        
        
        
        return new HashSet<OutputFile>();
    }
    
    /**
     * Is called, when finding is finished.
     * Shows all visible objects in the VisiblePanel
     */
    public void findingComplete() {
        if (!engineStarted) {
            
            if (settingsPanel.getRender2d()) 
                engine = new LwJglRenderingEngine2(plugin.lwJglNativePath, this);
            else 
                engine = new SimpleLwJglRenderingEngine(plugin.lwJglNativePath, this);
            
            engine.points = points;
            engine.start();
            engineStarted = true;
        }
        
        engine.pointSize = settingsPanel.getPointSize();
        
        
        visiblePanel.setObjects(dataSet.keySet(), graph.getRenderingExpressions(),rayMethod);
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
 
    public boolean isRendering2dActive() {
        return settingsPanel.getRender2d();
    }
    
}
