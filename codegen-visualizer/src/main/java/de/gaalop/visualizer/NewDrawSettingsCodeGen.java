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
import de.gaalop.dfg.Addition;
import de.gaalop.dfg.Exponentiation;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.FloatConstant;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.visitors.ReplaceVisitor;
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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
    private Differentiater differentiater;

    public PointClouds loadedPointClouds = new PointClouds();
    private PointClouds computedPointClouds = new PointClouds();
    
    private InputsPanel inputsPanel;
    private SettingsPanel settingsPanel;
    private VisiblePanel visiblePanel;
    
    public HashMap<String, Color> colors;
    private RenderingEngine renderingEngine;
    private boolean newDataSetAvailable = false;
    private HashMap<String, Expression> renderingExpressions;
    
    private boolean renderIn2d; //for cr4d

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
        
        /*
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
                    computedPointClouds.saveToFile(chooser.getSelectedFile());
                }
            }
        });
        */
        
        jComboBox_ZerofindingMethod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsPanel.setSettings(getSelectedZeroFinder().getSettings());
                repaint();
            }
        });
        
        jButton_DisplayEquations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Gaalop - Rendering equations");
                frame.setLayout(new BorderLayout(5,5));
                frame.setSize(500, 500);
                JScrollPane pane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                frame.add(pane, BorderLayout.CENTER);
                JTextArea area = new JTextArea(getDisplayEquationsAsString());
                area.setLineWrap(true);
                area.setEditable(false);
                pane.setViewportView(area);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JButton buttonCopy = new JButton("Copy content to clipboard");
                frame.add(buttonCopy, BorderLayout.SOUTH);
                buttonCopy.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents( new StringSelection(getDisplayEquationsAsString()), null );
                    }
                });
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
        renderIn2d = ("cr4d".equals(in.algebraName));
        
        renderingEngine = new SimpleLwJglRenderingEngine(lwJglNativePath, this);
        renderingEngine.start();
        
        //extract informations from the graph
        AssignmentNodeCollector collector = new AssignmentNodeCollector();
        in.accept(collector);
        graphAssignmentNodes = collector.getAssignmentNodes();
        
        differentiater = new DifferentiaterCreator(in.globalSettings.optMaxima, in.globalSettings.maximaCommand).createDifferentiater();
        renderingExpressions = in.getRenderingExpressions();
        colors = ColorEvaluater.getColors(in);
        
        //Determine all input variables
        LinkedList<String> inputs = new LinkedList<String>();
        for (Variable inputVar: in.getInputVariables()) 
            if (!isPositionVariable(inputVar.getName()))
                inputs.add(inputVar.getName());
        
        Collections.sort(inputs);
        inputsPanel.setInputs(inputs);

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
        curZeroFinder.setDifferentiater(differentiater);

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
                
        
                HashMap<String, LinkedList<Point3d>> pointsToRender = curZeroFinder.findZeroLocations(globalValues, list, settingsPanel.getSettings(), renderIn2d);
                
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
    
    private String getDisplayEquationsAsString() {
        //Copy List
        LinkedList<AssignmentNode> list = new LinkedList<AssignmentNode>();
        for (AssignmentNode node: graphAssignmentNodes) 
            list.add(node.copyElements());
                
        ReplaceVisitor visitor = new ReplaceVisitor() {

            private void visitVar(Variable node) {
                if (node.getName().equals("_V_X"))
                    result = new Variable("x");
                if (node.getName().equals("_V_Y"))
                    result = new Variable("y");
                if (node.getName().equals("_V_Z"))
                    result = new Variable("z");
            }
            
            @Override
            public void visit(MultivectorComponent node) {
                visitVar(node);
            }

            @Override
            public void visit(Variable node) {
                visitVar(node);
            }
            
        };
        for (AssignmentNode node: list) {
            node.setVariable((Variable) visitor.replace(node.getVariable()));
            node.setValue(visitor.replace(node.getValue()));
        }        
                
        //WithoutSums

        StringBuilder sb = new StringBuilder();
        sb.append("//All components that should be zero on the surface of the multivector");
        sb.append("\n");
        for (AssignmentNode node: list) {
            String name = node.getVariable().getName();
            String newName = renderingExpressions.get(name).toString();
            AssignmentNode nodeCpy = node.copyElements();
            nodeCpy.setVariable(new MultivectorComponent(newName, ((MultivectorComponent) nodeCpy.getVariable()).getBladeIndex()));
            sb.append(nodeCpy.toString());
            sb.append("\n");
        }
        
        list = createSumOfSquares(list);
        
        sb.append("\n");
        sb.append("//Sum of the squared components that should be zero on the surface of the multivector");
        sb.append("\n");
        for (AssignmentNode node: list) {
            sb.append(node.toString());
            sb.append("\n");
        }

        return sb.toString();
    }
    
    /**
     * Search _V_PRODUCT in a list of assignment nodes, 
     * applies the sum of the squares to _V_PRODUCT_S
     * and returns the result
     * @param nodes The list of assignment nodes
     * @return The new list of assignment nodes
     */
    private LinkedList<AssignmentNode> createSumOfSquares(LinkedList<AssignmentNode> nodes) {
        HashMap<String, LinkedList<AssignmentNode>> collect = new HashMap<String, LinkedList<AssignmentNode>>();
        
        for (AssignmentNode node: nodes) {
            MultivectorComponent m = (MultivectorComponent) node.getVariable();
            String name = m.getName();
            if (!collect.containsKey(name)) 
                collect.put(name, new LinkedList<AssignmentNode>());

            collect.get(name).add(node);
        }
        
        LinkedList<AssignmentNode> myNodes = new LinkedList<AssignmentNode>();
        for (String s: collect.keySet()) 
            if (s.startsWith("_V_PRODUCT")) {
                Expression sumOfSquares = null; 
                
                if (collect.get(s).size() > 1) {
                    for (AssignmentNode node: collect.get(s)) {
                        Expression square = new Exponentiation(node.getValue(), new FloatConstant(2));

                        if (sumOfSquares == null) 
                            sumOfSquares = square;
                        else 
                            sumOfSquares = new Addition(sumOfSquares, square);
                    }
                } else {
                    if (collect.get(s).size() == 1)
                        sumOfSquares = collect.get(s).getFirst().getValue();
                }
                
                if (sumOfSquares != null) {
                    AssignmentNode newNode = new AssignmentNode(null, new Variable(renderingExpressions.get(s).toString()), sumOfSquares);
                    myNodes.add(newNode);
                    listInsertBefore(myNodes, newNode, collect.get(s).getFirst());

                    for (AssignmentNode node: collect.get(s)) 
                        myNodes.remove(node);
                }
            }
        return myNodes;
    }
    
    /**
     * Inserts an element in a list before a certain element
     * @param list The list to insert in
     * @param toInsert The element to be insert
     * @param before The suceeding element
     */
    protected static void listInsertBefore(LinkedList<AssignmentNode> list, AssignmentNode toInsert, AssignmentNode before) {
        LinkedList<AssignmentNode> listCopy = new LinkedList<AssignmentNode>(list);
        list.clear();
        for (AssignmentNode node: listCopy) {
            if (node == before)
                list.add(toInsert);
            list.add(node);
        }
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
        pointClouds.putAll(computedPointClouds);
        return pointClouds;
    }
    
    @Override
    public PointClouds getLoadedPointClouds() {
        return loadedPointClouds;
    }

    @Override
    public HashSet<String> getVisibleObjects() {
        return visiblePanel.getVisibleObjects();
    }
    
    /**
     * Determines, if a variable with the given name is an input variable
     * @param name The name of the variable
     * @return true, if the variable with the given name is an input variable, otherwise false
     */
    public boolean isPositionVariable(String name) {
        if (name.equals("_V_X")) return true;
        if (name.equals("_V_Y")) return true;
        if (name.equals("_V_Z")) return true;
        
        return false;
    }
    
}
