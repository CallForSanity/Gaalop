package de.gaalop.visualizer;

import de.gaalop.api.cfg.AssignmentNodeCollector;
import de.gaalop.cfg.AssignmentNode;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.visualizer.engines.lwjgl.RenderingEngine;
import de.gaalop.visualizer.engines.lwjgl.SimpleLwJglRenderingEngine;
import de.gaalop.visualizer.gui.DrawSettings;
import de.gaalop.visualizer.gui.InputsPanel;
import de.gaalop.visualizer.gui.SettingsPanel;
import de.gaalop.visualizer.gui.VisiblePanel;
import de.gaalop.visualizer.zerofinding.DiscreteCubeMethod;
import de.gaalop.visualizer.zerofinding.GradientMethod;
import de.gaalop.visualizer.zerofinding.RayMethod;
import de.gaalop.visualizer.zerofinding.ZeroFinder;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;

/**
 * Extends the DrawSettings gui by implementing the actions.
 * Represents a Gaalop CodeGenerator.
 * 
 * @author Christian Steinmetz
 */
public class NewDrawSettingsCodeGen extends DrawSettings implements CodeGenerator, Rendering {
    
    private String lwJglNativePath;
    
    private LinkedList<AssignmentNode> graphAssignmentNodes;
    private String maximaCommand;

    private PointClouds loadedPointClouds = new PointClouds();
    private PointClouds computedPointClouds = new PointClouds();
    
    private InputsPanel inputsPanel;
    private SettingsPanel settingsPanel;
    private VisiblePanel visiblePanel;
    
    public HashMap<String, Color> colors;
    private RenderingEngine renderingEngine;
    private boolean newDataSetAvailable = false;
    private HashMap<String, Expression> renderingExpressions;

    public NewDrawSettingsCodeGen(String lwJglNativePath) {
        this.lwJglNativePath = lwJglNativePath;
        
        jButton_Repaint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recomputeCommand();
            }
        });
        
        inputsPanel = new InputsPanel(jPanel_Inputs) {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (settingsPanel.isAutoRendering()) 
                    recomputeCommand();
            }
        };
        settingsPanel = new SettingsPanel(jScrollPane_Settings, jPanel_Settings);
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
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
                    loadedPointClouds.loadFromFile(chooser.getSelectedFile());
            }
        });
        
        jButton_SavePointCloud.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    loadedPointClouds.saveToFile(chooser.getSelectedFile());
                }
            }
        });
        
        jComboBox_ZerofindingMethod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsPanel.setSettings(getSelectedZeroFinder().getSettings());
                repaint();
            }
        });
       
        //Add ZeroFinding methods
        LinkedList<ZeroFinder> zerofinderList = new LinkedList<ZeroFinder>();
        ZeroFinder defaultZeroFinder = new GradientMethod();
        zerofinderList.add(defaultZeroFinder);
        zerofinderList.add(new RayMethod());
        zerofinderList.add(new DiscreteCubeMethod());
        setZerofinderMethods(zerofinderList, defaultZeroFinder);
    }

    @Override
    public Set<OutputFile> generate(ControlFlowGraph in) throws CodeGeneratorException {
        renderingEngine = new SimpleLwJglRenderingEngine(lwJglNativePath, this);
        renderingEngine.start();
        
        //extract informations from the graph
        AssignmentNodeCollector collector = new AssignmentNodeCollector();
        in.accept(collector);
        graphAssignmentNodes = collector.getAssignmentNodes();
        maximaCommand = in.globalSettings.maximaCommand;
        
        renderingExpressions = in.getRenderingExpressions();
        colors = ColorEvaluater.getColors(in);

        //Make form visible and relayout it
        setVisible(true);
        setSize(getSize().width+1, getSize().height);
        setSize(getSize().width-1, getSize().height);

        return new HashSet<OutputFile>(); 
    }
    
    /**
     * Recomputes the point clouds by executing zerofinding methods
     */
    private void recomputeCommand() {
        computedPointClouds.clear();
        
        final ZeroFinder curZeroFinder = getSelectedZeroFinder();
        curZeroFinder.setMaximaCommand(maximaCommand);

        jLabel_Info.setText("Please wait while rendering ...");
        jLabel_Info.repaint();
        
        //fill global values from sliders
        final HashMap<MultivectorComponent, Double> globalValues = new HashMap<MultivectorComponent, Double>();
        HashMap<String, Double> inputValues = inputsPanel.getValues();
        for (String variable: inputValues.keySet()) 
            globalValues.put(new MultivectorComponent(variable, 0), inputValues.get(variable));

        //Start thread for finding zero points with the possiblility to cancel the search anytime
        new Thread() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                
                //Copy List
                LinkedList<AssignmentNode> list = new LinkedList<AssignmentNode>();
                for (AssignmentNode node: graphAssignmentNodes) 
                    list.add(node.copyElements());
                
        
                HashMap<String, LinkedList<Point3d>> pointsToRender = curZeroFinder.findZeroLocations(globalValues, list, settingsPanel.getSettings());
                
                long sum = 0;
                for (String key : pointsToRender.keySet()) {
                    LinkedList<Point3d> points = pointsToRender.get(key);
                    sum += points.size();
                    String myKey = (key.endsWith("_S"))? key.substring(0, key.length()-2): key; 
                    computedPointClouds.put(key, new PointCloud(key, colors.get(myKey), points));
                }
                
                findingComplete(sum, (System.currentTimeMillis()-start)/1000.0d);
            }
        }.start();
    }
    
    /**
     * This method is called, when finding has finished.
     * It shows all visible objects in the VisiblePanel.
     */
    private void findingComplete(long sum, double tokenTime) {
        newDataSetAvailable = true;  
        jLabel_Info.setText(sum + " points, time = "+tokenTime+" s");
        visiblePanel.setObjects(getDataSet().keySet(), renderingExpressions);   
        renderingEngine.pointSize = settingsPanel.getPointSize();
    }
    
    /**
     * Request repainting
     */
    private void repaintCommand() {
        newDataSetAvailable = true;
    }

    @Override
    public boolean isNewDataSetAvailable() {
        return newDataSetAvailable;
    }

    @Override
    public HashMap<String, PointCloud> getDataSet() {
        PointClouds pointClouds = new PointClouds();
        pointClouds.putAll(loadedPointClouds);
        pointClouds.putAll(computedPointClouds);
        return pointClouds;
    }

    @Override
    public HashSet<String> getVisibleObjects() {
        return visiblePanel.getVisibleObjects();
    }

}
