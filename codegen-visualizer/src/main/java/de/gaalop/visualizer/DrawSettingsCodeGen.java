package de.gaalop.visualizer;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.visualizer.engines.RenderingEngine;
import de.gaalop.visualizer.engines.lwjgl.SimpleLwJglRenderingEngine;
import de.gaalop.visualizer.zerofinding.RayMethod;
import de.gaalop.visualizer.zerofinding.ZeroFinder;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import javax.swing.JSpinner;

/**
 * Implements a frame-facade for all drawing operations
 * @author christian
 */
public class DrawSettingsCodeGen extends DrawSettings implements CodeGenerator {
    
    private ControlFlowGraph graph;
    
    private Plugin plugin;
    
    private ZeroFinder finder;
    
    private HashMap<String, Color> colors;
    
    private LinkedList<String> inputs;
    
    private RenderingEngine engine;
        

    public DrawSettingsCodeGen(Plugin plugin) {
        super();
        this.plugin = plugin;
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaintCommand();
            }
        });
        setVisible(true);
        System.out.println("hier");
        engine = new SimpleLwJglRenderingEngine(plugin.lwJglNativePath);
        engine.start();
    }

    @Override
    public Set<OutputFile> generate(ControlFlowGraph in) throws CodeGeneratorException {
        this.graph = in;
        finder = new RayMethod(plugin.maximaCommand);
        colors = ColorEvaluater.getColors(graph);
        finder.prepareGraph(in);
        
        inputs = new LinkedList<String>();
        //Determine all input variables
        for (Variable inputVar: in.getInputVariables()) 
            if (!finder.isPositionVariable(inputVar.getName()))
                inputs.add(inputVar.getName());
        
        jPanelInputs.setLayout(new GridLayout(2, 1, 5, 5));
        jPanelInputs.add(new JSpinner());
        jPanelInputs.add(new JSpinner());
        jPanelInputs.add(new JSpinner());
        jPanelInputs.add(new JSpinner());
        
        jPanelInputs.setLayout(new GridLayout(4, 1, 5, 5));
        
        
        
        
        return new HashSet<OutputFile>();
    }
    
    /**
     * Repaint the visualization window
     */
    private void repaintCommand() {
        HashMap<MultivectorComponent, Double> globalValues = new HashMap<MultivectorComponent, Double>(); //Sliders, ...
        //fill global values
        
        HashMap<String, LinkedList<Point3d>> pointsToRender = finder.findZeroLocations(graph, globalValues);

        HashMap<String, PointCloud> clouds = new HashMap<String, PointCloud>();
        for (String key : pointsToRender.keySet()) {
            System.out.println(pointsToRender.get(key).size());
            String myKey = (key.endsWith("_S"))? key.substring(0, key.length()-2): key; 
            clouds.put(key, new PointCloud(colors.get(myKey), pointsToRender.get(key)));
        }

        engine.render(clouds);   
    }    
    
}
