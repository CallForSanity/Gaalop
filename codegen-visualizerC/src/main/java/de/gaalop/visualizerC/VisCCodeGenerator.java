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
    
    private String NAME_F = "outputsF";
    private String NAME_DF = "outputsDF";
    
    private String maximaCommand;
    
    private ControlFlowGraph graph;
    
    private String[] inputNames;
    
    private LinkedList<AssignmentNode> nodes;
    
    public HashMap<String, Color> colors;
    
    private String[] outputNames;

    public VisCCodeGenerator(String maximaCommand) {
        this.maximaCommand = maximaCommand;
    }
 
    @Override
    public Set<OutputFile> generate(ControlFlowGraph graph) throws CodeGeneratorException {
        this.graph = graph;
        colors = ColorEvaluater.getColors(graph);
        prepareGraph();
        
        analyzeInputs();
        HashSet<OutputFile> result = new HashSet<OutputFile>();
        result.add(new OutputFile("FromGaalop.h", writePattern(), Charset.forName("UTF-8")));
        return result;
    }

    private String writePattern() {
        return
            "#pragma once\n"+
            "\n"+
            "#include \"Definitions.h\"\n"+
            "\n"+
            "void getOutputAttributes(int outputNo, std::string& name, float& colorR,float& colorG,float& colorB,float& colorA) {\n"+
            "	switch (outputNo) {\n"+
            getOutputAttributesCode()+
            "   }\n"+
            "}\n"+
            "\n"+
            "int getInputCount() {\n"+
            "	return "+getInputCount()+";\n"+
            "}\n"+
            "void getInputName(int inputNo, std::string& name) {\n"+
            "	switch (inputNo) {\n"+
            getInputNameCode()+
            "   }\n"+
            "}\n"+
            "\n"+
            "int getOutputCount() {\n"+
            "	return "+getOutputCount()+";\n"+
            "}\n"+
            "\n"+
            "void fpdf(I& ox, I& oy, I& oz, I& t, double inputs[], I "+NAME_F+"[], I "+NAME_DF+"[]) {\n"+
            getFPDFCode()+
            "}\n";
    }

    private String getOutputAttributesCode() {
        StringBuilder sb = new StringBuilder("");
        int count = getOutputCount();
        for (int output=0;output<count;output++) {
            Color color = getColorOfOutput(count);
            sb.append("		case ");sb.append(Integer.toString(output));sb.append(": \n");
            sb.append("		colorR = ");sb.append(Float.toString(color.getRed()/255.0f));sb.append("f; ");
            sb.append("		colorG = ");sb.append(Float.toString(color.getGreen()/255.0f));sb.append("f; ");
            sb.append("		colorB = ");sb.append(Float.toString(color.getBlue()/255.0f));sb.append("f; ");
            sb.append("		colorA = ");sb.append(Float.toString(color.getAlpha()/255.0f));sb.append("f;\n");
            sb.append("		colorA = ");sb.append(getNameOfOutput(output));sb.append(";\n");
            sb.append("		break;\n");
        }
        return sb.toString();
    }
    
    private String getInputNameCode() {
        StringBuilder sb = new StringBuilder("");
        int count = getInputCount();
        for (int input=0;input<count;input++) {
            sb.append("		name = \"");sb.append(getNameOfInput(input));sb.append("\"");
            sb.append("		break;\n");
        }
        return sb.toString();
    }
    
    private int getInputCount() {
        return inputNames.length;
    }
    
    private String getNameOfInput(int no) {
        return inputNames[no];
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
        
        inputNames = new String[inputVariables.size()];
        int i=0;
        for (Variable v: inputVariables) {
            inputNames[i] = v.getName();
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

                for (AssignmentNode node: collect.get(s)) 
                    graph.removeNode(node);
            }
  
        outputNames = listOutputNames.toArray(new String[0]);
        
        //differentiate with respect to t with the help of maxima = _V_PRODUCT_SD
        MaximaDifferentiater differentiater = new MaximaDifferentiater();
        LinkedList<AssignmentNode> derived;
        try {
            derived = differentiater.differentiate(nodes, maximaCommand);
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

    private String getFPDFCode() {//TODO
        CppVisitor visitor = new CppVisitor(true);
        graph.accept(visitor);
        return visitor.getCode();
    }

}
