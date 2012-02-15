package de.gaalop.visualizer;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.visualizer.engines.lwjgl.SimpleLwJglRenderingEngine;
import de.gaalop.visualizer.zerofinding.RayMethod;
import de.gaalop.visualizer.zerofinding.ZeroFinder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

/**
 * Implements a frame-facade for all drawing operations
 * @author christian
 */
public class DrawSettingsCodeGen extends DrawSettings implements CodeGenerator, Rendering {
    
    public ControlFlowGraph graph;
    
    private Plugin plugin;
    
    public ZeroFinder finder;
    
    public HashMap<String, Color> colors;
    
    private LinkedList<String> inputs;
    
    public boolean available = false;
        
    public HashMap<String, PointCloud> dataSet = null;

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
        SimpleLwJglRenderingEngine engine = new SimpleLwJglRenderingEngine(plugin.lwJglNativePath, this);
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
        
        Collections.sort(inputs);
        
        jPanelInputs.setSize(jPanelInputs.getWidth(),25*inputs.size());
        jPanelInputs.setLayout(new GridLayout((inputs.size() < 8) ? 8-inputs.size(): inputs.size(), 1, 5, 5));
        for (String input: inputs) {
            JPanel p = new JPanel(new BorderLayout(5,5));
            jPanelInputs.add(p);
            JLabel label = new JLabel(input+":");
            p.add(label, BorderLayout.WEST);
            JSpinner spinner = new JSpinner();
            label.setLabelFor(spinner);
            p.add(spinner, BorderLayout.CENTER);
            spinner.setToolTipText(input);
        }

        setSize(getSize().width+1, getSize().height);
        setSize(getSize().width-1, getSize().height);
 
        return new HashSet<OutputFile>();
    }
    
    /**
     * Repaint the visualization window
     */
    private void repaintCommand() {
        jLabel_Info.setText("Please wait while rendering ...");
        jLabel_Info.repaint();
        HashMap<MultivectorComponent, Double> globalValues = new HashMap<MultivectorComponent, Double>(); //Sliders, ...
        //fill global values
        
        FindPointsThread thread = new FindPointsThread(globalValues,this);
        thread.start();
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
    
}
