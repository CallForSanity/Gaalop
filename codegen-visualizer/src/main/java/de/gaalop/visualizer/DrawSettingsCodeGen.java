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
import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Implements a frame-facade for all drawing operations
 * @author christian
 */
public class DrawSettingsCodeGen extends DrawSettings implements CodeGenerator, Rendering, ChangeListener {
    
    public ControlFlowGraph graph;
    
    private Plugin plugin;
    
    public ZeroFinder finder;
    
    public HashMap<String, Color> colors;
    
    private LinkedList<String> inputs;
    
    public boolean available = false;
        
    public HashMap<String, PointCloud> dataSet = null;
    
    private HashMap<JSpinner, String> mapSpinners = new HashMap<JSpinner, String>();
    
    private JCheckBox autoRendering;
    private JTextField jTF_cubeLength, jTF_density;

    public DrawSettingsCodeGen(Plugin plugin) {
        super();
        this.plugin = plugin;
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaintCommand();
            }
        });
        
        JPanel panel1Inside = new JPanel(new GridLayout(6,1,5,5));
        panel1Inside.setSize(panel1Inside.getWidth(),3*25);
        autoRendering = new JCheckBox("Automatic Rendering");
        autoRendering.setSelected(false);
        jPanel1.setLayout(new GridLayout(1,1));
        jPanel1.add(panel1Inside);
        
        panel1Inside.add(autoRendering);
        
        JPanel panelCubeLength = new JPanel(new BorderLayout(5,5));
        panel1Inside.add(panelCubeLength);
        panelCubeLength.add(new JLabel("length"),BorderLayout.WEST);
        jTF_cubeLength = new JTextField("5");
        panelCubeLength.add(jTF_cubeLength, BorderLayout.CENTER);
        
        JPanel panelDensity = new JPanel(new BorderLayout(5,5));
        panel1Inside.add(panelDensity);
        panelDensity.add(new JLabel("density"),BorderLayout.WEST);
        jTF_density = new JTextField("0.1");
        panelDensity.add(jTF_density, BorderLayout.CENTER);  
        
        

        
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
        
        mapSpinners.clear();
        for (String input: inputs) {
            JPanel p = new JPanel(new BorderLayout(5,5));
            jPanelInputs.add(p);
            JLabel label = new JLabel(input+":");
            p.add(label, BorderLayout.WEST);
            JSpinner spinner = new JSpinner(new SpinnerNumberModel());
            label.setLabelFor(spinner);
            p.add(spinner, BorderLayout.CENTER);
            spinner.setToolTipText(input);
            mapSpinners.put(spinner, input);
            spinner.addChangeListener(this);
        }
        setVisible(true);
        setSize(getSize().width+1, getSize().height);
        setSize(getSize().width-1, getSize().height);
        
       
        SimpleLwJglRenderingEngine engine = new SimpleLwJglRenderingEngine(plugin.lwJglNativePath, this);
        
        engine.start();
        return new HashSet<OutputFile>();
    }
    
    /**
     * Repaint the visualization window
     */
    private void repaintCommand() {
        finder.cubeEdgeLength = Float.parseFloat(jTF_cubeLength.getText());
        finder.density = Float.parseFloat(jTF_density.getText());
        jLabel_Info.setText("Please wait while rendering ...");
        jLabel_Info.repaint();
        HashMap<MultivectorComponent, Double> globalValues = new HashMap<MultivectorComponent, Double>(); //Sliders, ...
        //fill global values
        for (JSpinner spinner: mapSpinners.keySet()) {
            String variable = mapSpinners.get(spinner);
            Number value = (Number) spinner.getValue();
            globalValues.put(new MultivectorComponent(variable, 0), value.doubleValue());
        }
        
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

    @Override
    public void stateChanged(ChangeEvent e) {
        if (autoRendering.isSelected()) repaintCommand();
    }


    
}
