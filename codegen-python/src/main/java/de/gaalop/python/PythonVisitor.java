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

    protected Boolean useArrays = true;

    protected String filename;

    private HashMap<String, Color> expressionStatements = new HashMap<>();

    private HashMap<String, LinkedList<Integer>> mvComponents = new HashMap<>();

    private HashSet<String> mvs = new HashSet<String>();

    protected Set<String> libraries = new HashSet<>();

    public PythonVisitor(boolean useDouble, String filename, boolean useArrays) {
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
        addCode("def " + filename.toLowerCase() + "(");

        // Print parameters
        addCode(graph.getInputs().join());

        addCode("):\n");
        indentation++;

        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(AssignmentNode node) {
        String varName = getNewName(node.getVariable());
        if (!mvs.contains(varName)) {
            addLine(varName + " = np.zeros(" + node.getGraph().getAlgebraDefinitionFile().blades2.length + ")");
            mvs.add(varName);
        }

        appendIndentation();
        node.getVariable().accept(this);
        addCode(" = ");
        node.getValue().accept(this);

        if (node.getVariable() instanceof MultivectorComponent) {
            addCode(" # ");
            MultivectorComponent component = (MultivectorComponent) node.getVariable();

            addCode(graph.getBladeString(component));

            if (!mvComponents.containsKey(component.getName()))
                mvComponents.put(component.getName(), new LinkedList<Integer>());

            mvComponents.get(component.getName()).add(component.getBladeIndex());
        }

        addCode('\n');
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
        addCode("return ");
        addCode(graph.getOutputs().join());

        // Insert libraries at script start
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

            String text = code.toString();
            if (text.contains("sin(") || text.contains("cos(")) {
                code.insert(0, "from math import sin, cos\n");
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
        addCode(funcName);
        addCode('(');
        mathFunctionCall.getOperand().accept(this);
        addCode(')');
    }

    @Override
    public void visit(Variable variable) {
        // usually there are no variables
        addCode(getNewName(variable));
    }

    @Override
    public void visit(MultivectorComponent component) {
        addCode(component.getName());
        addCode("[" + component.getBladeIndex() + "]");
    }

    @Override
    public void visit(Exponentiation exponentiation) {
        if (isSquare(exponentiation)) {
            Multiplication m = new Multiplication(exponentiation.getLeft(), exponentiation.getLeft());
            m.accept(this);
        } else {
            addCode("pow(");
            exponentiation.getLeft().accept(this);
            addCode(',');
            exponentiation.getRight().accept(this);
            addCode(')');
        }
    }

    @Override
    public void visit(FloatConstant floatConstant) {
        addCode(Double.toString(floatConstant.getValue()));
    }

    @Override
    public void visit(Negation negation) {
        addCode('(');
        addCode('-');
        addChild(negation, negation.getOperand());
        addCode(')');
    }

    @Override
    protected String getNewName(Variable variable) {
        return variable.getName();
        //        return variable.getNewName(graph, useArrays);
    }
}
