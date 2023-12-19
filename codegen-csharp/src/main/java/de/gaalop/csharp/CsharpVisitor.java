package de.gaalop.csharp;

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
public class CsharpVisitor extends DefaultCodeGeneratorVisitor {

    protected boolean standalone = true;

    protected Set<String> assigned = new HashSet<>();

    protected String variableType = "float";
    protected String mathLibrary = "MathF";
    protected Boolean useDouble = false;

    
    protected Set<String> libraries = new HashSet<>();

    public CsharpVisitor(boolean standalone) {
        this.standalone = standalone;
    }

    public CsharpVisitor(boolean standalone, boolean useDouble) {
        this.standalone = standalone;
        this.useDouble = useDouble;
        
        if (useDouble) {
            variableType = "double";
            mathLibrary = "Math";
        }
    }

    public CsharpVisitor(String variableType) {
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
        
        usedVariableNames.clear();
        
        if (standalone) {
            // Add class and method name
            String filename = graph.getSource().getName().split("\\.")[0];
            appendIndentation();
            code.append("public static class " + filename + "\n{\n");
            indentation++;
            appendIndentation();
            code.append("public static void Execute(");
            
            // Print parameters
            StringList parameters = new StringList();
            
            for (String var : graph.getInputs()) {
                parameters.add(variableType+" "+var); // The assumption here is that they all are normal scalars
            }
            
            for (String var : outputs) {
                parameters.add(variableType + "[] " + var );
            }
            
            code.append(parameters.join());

            code.append(")\n");
            appendIndentation();
            code.append("{\n");
            
            indentation++;
        } 

        for (String var : graph.getLocals()) 
            if (!outputs.contains(var)) {
                appendIndentation();
                code.append(variableType).append("[] ").append(var);
                code.append(" = new ").append(variableType).append("[").append(bladeCount).append("];\n");
            }

        if (graph.getScalarVariables().size() > 0) {
            appendIndentation();
            code.append(variableType).append(" ");
            code.append(graph.getScalars().join());
            code.append(";\n");
        }

        node.getSuccessor().accept(this);
    }

    HashSet<String> usedVariableNames = new HashSet<>();
                
    @Override
    public void visit(AssignmentNode node) {
        Variable variable = node.getVariable();
        String variableName = variable.getName();
        
        // What does this?
        if (assigned.contains(variableName)) {
            String message = "Variable " + variableName + " has been reset for reuse.";
            log.warn(message);
            Notifications.addWarning(message);
            
            appendIndentation();
            code.append("memset(");
            code.append(variableName);
            code.append(", 0, sizeof(");
            code.append(variableName);
            code.append(")); // Reset variable for reuse.\n");
            assigned.remove(variableName);
        }

        if (usedVariableNames.add(variableName)) {

//            usedVariableNames.add(variableName);
        }
        
        appendIndentation();
        code.append("float ");
        
//        variable.accept(this);

        if (node.getVariable() instanceof MultivectorComponent) {
            MultivectorComponent component = (MultivectorComponent) node.getVariable();
            String blade = graph.getBladeString(component).replaceAll(" ", "");
            print("blade1 = " + blade);
            blade = blade.replaceAll("^", "");  
            print("blade2 = " + blade);
            code.append(variableName + blade);
        }
        else
        {
            code.append(variableName);
        }
        
//        code.append(variableName);
        code.append(" = ");
        node.getValue().accept(this);
        code.append(";");

        if (node.getVariable() instanceof MultivectorComponent) {
            MultivectorComponent component = (MultivectorComponent) node.getVariable();
            code.append(" // ");
            code.append(graph.getBladeString(component));
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
            indentation--;
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
        libraries.add("using System;\n");
        String funcName;
        switch (mathFunctionCall.getFunction()) {
            case ABS:
                funcName = mathLibrary + ".Abs";
                break;
            case SQRT:
                funcName = mathLibrary + ".Sqrt";
                break;
            case COS:
                funcName = mathLibrary + ".Cos";
                break;
            case SIN:
                funcName = mathLibrary + ".Sin";
                break;
            case EXP:
                funcName = mathLibrary + ".Exp";
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
        String name = component.getName();
        int bladeIndex = component.getBladeIndex();
        code.append(name + bladeIndex);
//        code.append(name);
//        code.append('[');
//        code.append(bladeIndex);
//        code.append(']');
        System.out.println(name + "\n");
 }

    @Override
    public void visit(Exponentiation exponentiation) {
        if (isSquare(exponentiation)) {
            Multiplication m = new Multiplication(exponentiation.getLeft(), exponentiation.getLeft());
            m.accept(this);
        } else {
            libraries.add("using System;");
            code.append(mathLibrary + ".Pow(");
            exponentiation.getLeft().accept(this);
            code.append(',');
            exponentiation.getRight().accept(this);
            code.append(')');
        }
    }

    @Override
    public void visit(FloatConstant floatConstant) {
        double value = floatConstant.getValue();
        code.append(Double.toString(value));
        if (!useDouble)
        {
            code.append("f");
        }
    }

    @Override
    public void visit(Negation negation) {
        code.append('(');
        code.append('-');
        addChild(negation, negation.getOperand());
        code.append(')');
    }

    private void print(Object message)
    {
        System.out.println(message.toString());
    }
}
