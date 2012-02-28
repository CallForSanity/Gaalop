package de.gaalop.visualizerC;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.OptimizationException;
import de.gaalop.OutputFile;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.*;
import de.gaalop.tba.cfgImport.optimization.maxima.MaximaDifferentiater;
import de.gaalop.visitors.CFGReplaceVisitor;
import de.gaalop.visitors.ReplaceVisitor;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.runtime.RecognitionException;

/**
 * Creates the code for including in the CVisualizer
 * @author Christian Steinmetz
 */
public class VisCCodeGenerator implements CodeGenerator {

    private ControlFlowGraph graph;
    
    private HashMap<String,Integer> mapInputNameToNr;
    private HashMap<Integer, String> mapInputNrToName;
    
    private LinkedList<AssignmentNode> nodes;
    
    public HashMap<String, Color> colors;
    
    private String[] outputNames;
    
    private Plugin plugin;

    public VisCCodeGenerator(Plugin plugin) {
        this.plugin = plugin;
    }
 
    @Override
    public Set<OutputFile> generate(ControlFlowGraph graph) throws CodeGeneratorException {
        this.graph = graph;
        colors = ColorEvaluater.getColors(graph);
        analyzeInputs();
        prepareGraph();
        
        
        HashSet<OutputFile> result = new HashSet<OutputFile>();
        String outContents = writePattern();
        result.add(new OutputFile("FromGaalop.h", outContents, Charset.forName("UTF-8")));
        if (!plugin.autoStorage.isEmpty()) {
            PrintWriter writer;
            try {
                writer = new PrintWriter(plugin.autoStorage);
                writer.print(outContents);
                
                writer.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(VisCCodeGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
        return result;
    }

    private String writePattern() {
        return
            "#pragma once\n"+
            "\n"+
            "#include \"Definitions.h\"\n"+
            "\n"+
            "#define INPUTCOUNT "+getInputCount()+"\n"+
            "#define OUTPUTCOUNT "+getOutputCount()+"\n"+
            "\n"+
            "void getOutputAttributes(int outputNo, std::string& name, float& colorR,float& colorG,float& colorB,float& colorA) {\n"+
            ((getOutputCount()>0) ? 
            ("	switch (outputNo) {\n"+
            getOutputAttributesCode()+
            "   }\n") : "") +
            "}\n"+
            "\n"+
            "void getInputName(int inputNo, std::string& name) {\n"+
            ((getInputCount()>0) ? 
            ("	switch (inputNo) {\n"+
            getInputNameCode()+
            "   }\n") : "") +
            "}\n"+
            "\n"+
            "void f(float& ox, float& oy, float& oz, float& t, float inputs[], float outputsF[]) {\n"+
            getFCode()+
            "}\n"+
            "\n"+  
            "void df(float& ox, float& oy, float& oz, float& t, float inputs[], float outputsDF[]) {\n"+
            getDFCode()+
            "}\n"+
            "\n"+  
            "void fpdf(I& ox, I& oy, I& oz, I& t, float inputs[], I outputsF[], I outputsDF[]) {\n"+
            getFPDFCode()+
            "}\n";
    }

    private String getOutputAttributesCode() {
        StringBuilder sb = new StringBuilder("");
        int count = getOutputCount();
        for (int output=0;output<count;output++) {
            Color color = getColorOfOutput(output);
            sb.append("		case ");sb.append(Integer.toString(output));sb.append(": \n");
            sb.append("		colorR = ");sb.append(Float.toString(color.getRed()/255.0f));sb.append("f; ");
            sb.append("	colorG = ");sb.append(Float.toString(color.getGreen()/255.0f));sb.append("f; ");
            sb.append("	colorB = ");sb.append(Float.toString(color.getBlue()/255.0f));sb.append("f; ");
            sb.append("	colorA = ");sb.append(Float.toString(color.getAlpha()/255.0f));sb.append("f;\n");
            sb.append("             name = \"");sb.append(getNameOfOutput(output));sb.append("\";\n");
            sb.append("		break;\n");
        }
        return sb.toString();
    }
    
    private String getInputNameCode() {
        StringBuilder sb = new StringBuilder("");
        int count = getInputCount();
        for (int input=0;input<count;input++) {
            sb.append("		case ");sb.append(Integer.toString(input));sb.append(": \n");
            sb.append("		name = \"");sb.append(getNameOfInput(input));sb.append("\";\n");
            sb.append("		break;\n");
        }
        return sb.toString();
    }
    
    private int getInputCount() {
        return mapInputNameToNr.size();
    }
    
    private String getNameOfInput(int no) {
        return mapInputNrToName.get(no);
    }

    private void analyzeInputs() {
        ArrayList<Variable> inputVariables = new ArrayList<Variable>(graph.getInputVariables());
        inputVariables.remove(new Variable("_V_X"));
        inputVariables.remove(new Variable("_V_Y"));
        inputVariables.remove(new Variable("_V_Z"));
        
        Collections.sort(inputVariables,new Comparator<Variable>() {
            @Override
            public int compare(Variable o1, Variable o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        
        mapInputNameToNr = new HashMap<String, Integer>();
        mapInputNrToName = new HashMap<Integer, String>();
        int i=0;
        for (Variable v: inputVariables) {
            mapInputNameToNr.put(v.getName(), new Integer(i));
            mapInputNrToName.put(new Integer(i), v.getName());
            i++;
        }
    }

    // ================ OUTPUTS =============
    
    public void prepareGraph() {
        //replace x=ox+t,y=oy,z=oz
        CFGReplaceVisitor replacer = new CFGReplaceVisitor(new ReplaceVisitor() {

            private void visitVar(Variable node) {
                if (node.getName().equals("_V_X"))
                    result = new Addition(new MultivectorComponent("_V_ox", 0), new MultivectorComponent("_V_t", 0));
                if (node.getName().equals("_V_Y"))
                    result = new MultivectorComponent("_V_oy", 0);
                if (node.getName().equals("_V_Z"))
                    result = new MultivectorComponent("_V_oz", 0);
                if (mapInputNameToNr.containsKey(node.getName()))
                    result = new MultivectorComponent("inputs", mapInputNameToNr.get(node.getName()));
            }
            
            @Override
            public void visit(MultivectorComponent node) {
                visitVar(node);
            }

            @Override
            public void visit(Variable node) {
                visitVar(node);
            }
            
        });
        graph.accept(replacer);

        //search _V_PRODUCT and apply the sum of the squares = _V_PRODUCT_S
        HashMap<String, LinkedList<AssignmentNode>> collect = MultivectorNodeCollector.collect(graph);
        
        LinkedList<String> listOutputNames = new LinkedList<String>();
        
        nodes = new LinkedList<AssignmentNode>();
        for (String s: collect.keySet()) 
            if (s.startsWith("_V_PRODUCT")) {
                Expression sumOfSquares = null; 
                
                for (AssignmentNode node: collect.get(s)) {
                    Expression square = new Multiplication(node.getValue(), node.getValue().copy());
                    
                    if (sumOfSquares == null) 
                        sumOfSquares = square;
                    else 
                        sumOfSquares = new Addition(sumOfSquares, square);
                }
                
                AssignmentNode newNode = new AssignmentNode(graph, new MultivectorComponent(s+"_S", 0), sumOfSquares);
                nodes.add(newNode);
                listOutputNames.add(s);
                collect.get(s).getFirst().insertBefore(newNode);
                
                graph.getLocalVariablesModifiable().remove(new Variable(s));

                for (AssignmentNode node: collect.get(s)) 
                    graph.removeNode(node);
            }
  
        outputNames = listOutputNames.toArray(new String[0]);

        //differentiate with respect to t with the help of maxima = _V_PRODUCT_SD
        MaximaDifferentiater differentiater = new MaximaDifferentiater();
        LinkedList<AssignmentNode> derived;
        try {
            derived = differentiater.differentiate(nodes, plugin.maximaCommand);
            ListIterator<AssignmentNode> listIt = nodes.listIterator();
            for (AssignmentNode d: derived) {
                d.setVariable(new MultivectorComponent(d.getVariable().getName()+"D", 0));
                listIt.next().insertAfter(d);
            }
        } catch (OptimizationException ex) {
            Logger.getLogger(VisCCodeGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RecognitionException ex) {
            Logger.getLogger(VisCCodeGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private int getOutputCount() {
        return nodes.size();
    }
    
    private Color getColorOfOutput(int no) {
        return colors.get(outputNames[no]);
    }
    
    private String getNameOfOutput(int no) {
        return graph.getRenderingExpressions().get(outputNames[no]).toString();
    }

    private String getFPDFCode() {
        CppVisitor visitor = new CppVisitor(false, nodes);
        graph.accept(visitor);
        return visitor.getCode();
    }

    private String getFCode() {
        CppVisitor visitor = new CppVisitor(false, nodes);
        visitor.ignoreAssignments("_SD");
        graph.accept(visitor);
        return visitor.getCode();
    }

    private String getDFCode() {
        CppVisitor visitor = new CppVisitor(false, nodes);
        visitor.ignoreAssignments("_S");
        graph.accept(visitor);
        return visitor.getCode();
    }

}
