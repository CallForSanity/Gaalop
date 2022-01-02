package de.gaalop.cpp;

import de.gaalop.DefaultCodeGeneratorVisitor;
import de.gaalop.Notifications;
import de.gaalop.StringList;
import de.gaalop.cfg.*;
import de.gaalop.dfg.*;

import java.util.*;

/**
 * This visitor traverses the control and data flow graphs and generates C/C++
 * code.
 */
public class CppVisitor extends DefaultCodeGeneratorVisitor {

    protected boolean standalone = true;

    protected Set<String> assigned = new HashSet<>();

    protected String variableType = "float";
    
    protected Set<String> libraries = new HashSet<>();

    public CppVisitor(boolean standalone) {
        this.standalone = standalone;
    }

    public CppVisitor(boolean standalone, boolean useDouble) {
        this.standalone = standalone;
        if (useDouble) {
            variableType = "double";
        }
    }

    public CppVisitor(String variableType) {
        this.standalone = false;
        this.variableType = variableType;
    }

    public void setStandalone(boolean standalone) {
        this.standalone = standalone;
    }

    @Override
    public void visit(StartNode node) {
        graph = node.getGraph();
        int bladeCount = graph.getAlgebraDefinitionFile().getBladeCount();
        
        StringList outputs = graph.getOutputs();
        
        if (standalone) {
            appendIndentation();
            code.append("void " + graph.getSource().getName().split("\\.")[0] + "(");
            
            // Print parameters
            StringList parameters = new StringList();
            
            for (String var : graph.getInputs()) {
                parameters.add(variableType+" "+var); // The assumption here is that they all are normal scalars
            }
            
            for (String var : outputs) {
                parameters.add(variableType+" "+var+"["+bladeCount+"]");
            }
            
            code.append(parameters.join());

            code.append(") {\n");
            indentation++;
        } 

        for (String var : graph.getLocals()) 
            if (!outputs.contains(var)) {
                appendIndentation();
                code.append(variableType).append(" ");
                code.append(var);
                code.append("[" + bladeCount + "] = { 0.0 };\n");
            }

        if (graph.getScalarVariables().size() > 0) {
            appendIndentation();
            code.append(variableType).append(" ");
            code.append(graph.getScalars().join());
            code.append(";\n");
        }

        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(AssignmentNode node) {
        String variable = node.getVariable().getName();
        if (assigned.contains(variable)) {
            String message = "Variable " + variable + " has been reset for reuse.";
            log.warn(message);
            Notifications.addWarning(message);
            appendIndentation();
            code.append("memset(");
            code.append(variable);
            code.append(", 0, sizeof(");
            code.append(variable);
            code.append(")); // Reset variable for reuse.\n");
            assigned.remove(variable);
        }

        appendIndentation();
        node.getVariable().accept(this);
        code.append(" = ");
        node.getValue().accept(this);
        code.append(";");

        if (node.getVariable() instanceof MultivectorComponent) {
            code.append(" // ");
            code.append(graph.getBladeString((MultivectorComponent) node.getVariable()));
        }

        code.append("\n");

        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(ExpressionStatement node) {
        appendIndentation();
        node.getExpression().accept(this);
        code.append(";\n");

        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(StoreResultNode node) {
        assigned.add(node.getValue().getName());
        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(EndNode node) {
        if (standalone) {
            indentation--;
            appendIndentation();
            code.append("}\n");
        }
        
        if (!libraries.isEmpty()) {
            LinkedList<String> libs = new LinkedList<>(libraries);
            libs.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o2.compareTo(o1);
                }
            });
            
            for (String lib: libs) {
                code.insert(0, "#"+lib+"\n");
            }
        }
    }

    @Override
    public void visit(ColorNode node) {
        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(MathFunctionCall mathFunctionCall) {
        libraries.add("include <math.h>");
        String funcName;
        switch (mathFunctionCall.getFunction()) {
            case ABS:
                funcName = "fabs";
                break;
            case SQRT:
                funcName = "sqrtf";
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
        code.append(variable.getName());
    }

    @Override
    public void visit(MultivectorComponent component) {
        code.append(component.getName());
        code.append('[');
        code.append(component.getBladeIndex());
        code.append(']');
    }

    @Override
    public void visit(Exponentiation exponentiation) {
        if (isSquare(exponentiation)) {
            Multiplication m = new Multiplication(exponentiation.getLeft(), exponentiation.getLeft());
            m.accept(this);
        } else {
            libraries.add("include <math.h>");
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
