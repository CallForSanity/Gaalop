package de.gaalop.python;

import de.gaalop.DefaultCodeGeneratorVisitor;
import de.gaalop.cfg.*;
import de.gaalop.dfg.*;
import java.awt.Color;
import java.util.*;


/**
 * This visitor traverses the control and data flow graphs and generates C/C++
 * code.
 */
public class PythonVisitor extends DefaultCodeGeneratorVisitor {

    protected String variableType = "float";
    
    protected String filename;
    
    private HashMap<String, Color> expressionStatements = new HashMap<>();
    
    private HashMap<String, LinkedList<Integer>> mvComponents = new HashMap<>();
    
    private HashSet<String> mvs = new HashSet<String>();
    
    protected Set<String> libraries = new HashSet<>();

    public PythonVisitor(boolean useDouble, String filename) {
        this.filename = filename;
        if (useDouble) {
            variableType = "double";
        }
    }

    public PythonVisitor(String variableType) {
        this.variableType = variableType;
    }
    
    @Override
    public void visit(StartNode node) {
        graph = node.getGraph();
        libraries.add("import numpy as np");
        
        appendIndentation();
        code.append("def "+filename.toLowerCase()+"("); 
        
        // Print parameters
        code.append(graph.getInputs().join());

        code.append("):\n");
        indentation++;
 
        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(AssignmentNode node) {
        String varName = node.getVariable().getName();
        if (!mvs.contains(varName)) {
            appendIndentation();
            code.append(varName+" = np.zeros("+node.getGraph().getAlgebraDefinitionFile().blades2.length+")\n");
            mvs.add(varName);
        }
        
        appendIndentation();
        node.getVariable().accept(this);
        code.append(" = ");
        node.getValue().accept(this);

        if (node.getVariable() instanceof MultivectorComponent) {
            code.append(" # ");
            MultivectorComponent component = (MultivectorComponent) node.getVariable();
            
            code.append(graph.getBladeString(component));
            
            if (!mvComponents.containsKey(component.getName())) 
                mvComponents.put(component.getName(), new LinkedList<Integer>());
            
            mvComponents.get(component.getName()).add(component.getBladeIndex());
        }

        code.append('\n');

        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(ExpressionStatement node) {
        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(StoreResultNode node) {
        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(EndNode node) {
        
        appendIndentation();
        code.append("return ");
        code.append(graph.getOutputs().join());
 
        if (!libraries.isEmpty()) {
            LinkedList<String> libs = new LinkedList<>(libraries);
            libs.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o2.compareTo(o1);
                }
            });
            
            for (String lib: libs) {
                code.insert(0, lib+"\n");
            }
        }
    }

    @Override
    public void visit(ColorNode node) {
        node.getSuccessor().accept(this);
    }

    
    @Override
    public void visit(MathFunctionCall mathFunctionCall) {
        libraries.add("import math");
        String funcName;
        switch (mathFunctionCall.getFunction()) {
            case ABS:
                funcName = "abs";
                break;
            case SQRT:
                funcName = "math.sqrt";
                break;
            default:
                funcName = mathFunctionCall.getFunction().toString().toLowerCase();
        }
        code.append(funcName);
        code.append('(');
        mathFunctionCall.getOperand().accept(this);
        code.append(')');
    }

    @Override
    public void visit(Variable variable) {
        // usually there are no variables
        code.append(variable.getName());
    }

    @Override
    public void visit(MultivectorComponent component) {
        code.append(component.getName());
        code.append("["+component.getBladeIndex()+"]");  
    }

    @Override
    public void visit(Exponentiation exponentiation) {
        if (isSquare(exponentiation)) {
            Multiplication m = new Multiplication(exponentiation.getLeft(), exponentiation.getLeft());
            m.accept(this);
        } else {
            code.append("pow(");
            exponentiation.getLeft().accept(this);
            code.append(',');
            exponentiation.getRight().accept(this);
            code.append(')');
        }
    }

    @Override
    public void visit(FloatConstant floatConstant) {
        code.append(Double.toString(floatConstant.getValue()));
    }

    @Override
    public void visit(Negation negation) {
        code.append('(');
        code.append('-');
        addChild(negation, negation.getOperand());
        code.append(')');
    }
}
