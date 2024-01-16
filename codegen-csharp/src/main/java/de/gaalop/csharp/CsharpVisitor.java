package de.gaalop.csharp;

import de.gaalop.DefaultCodeGeneratorVisitor;
import de.gaalop.Notifications;
import de.gaalop.StringList;
import de.gaalop.cfg.*;
import de.gaalop.dfg.*;
import java.util.stream.Collectors;
import java.util.*;

/**
 * This visitor traverses the control and data flow graphs and generates C/C++
 * code.
 *
 * To do: Checken ob Skalare auch für andere Programmiersprachen abstrahiert
 * werden können.
 */
public class CsharpVisitor extends DefaultCodeGeneratorVisitor {

    protected boolean standalone = true;

    protected Set<String> assigned = new HashSet<>();

    protected String variableType = "float";
    protected String mathLibrary = "MathF";

    protected Boolean useDouble = true;
    protected Boolean useArrays = true;
    protected Boolean normalizeOutputs = true;

    protected Set<String> libraries = new HashSet<>();

    private final String newline = "\n";

    public CsharpVisitor(boolean standalone) {
        this.standalone = standalone;
    }

    public CsharpVisitor(boolean standalone, boolean useDouble, boolean useArrays) {
        this.standalone = standalone;
        this.useDouble = useDouble;
        this.useArrays = useArrays;

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

    private static String MethodModifiers = "public static void";
    private static String MethodName = "Execute";

    // The first node
    @Override
    public void visit(StartNode node) {
        graph = node.getGraph();
        int bladeCount = graph.getAlgebraDefinitionFile().getBladeCount();

        StringList outputs = graph.getOutputs();

        // At each start, this set need to be cleared
        declaredVariableNames.clear();

        if (standalone) {
            // Add class and method name
            String filename = graph.getSource().getName().split("\\.")[0];
            appendIndentation();
            addCode("public static class " + filename + "\n{\n");
            indentation++;
            appendIndentation();
            addCode(MethodModifiers + " " + MethodName + "(");

            // Print parameters
            StringList parameters = new StringList();

            for (String inputVariable : graph.getInputs()) {
                parameters.add(variableType + " " + inputVariable); // The assumption here is that they all are normal scalars
            }

            // Add outputs to parameters if arrays are used
            if (useArrays) {
                for (String outputVariable : outputs) {
                    parameters.add(variableType + "[] " + outputVariable);
                }
            }


            addCode(parameters.join(", "));

            addCode(")\n");
            appendIndentation();
            addCode("{\n");

            indentation++;
        }

        // When using arrays, they need to be defined at the start
        if (useArrays) {
            for (String localVariable : graph.getLocals()) {
                if (!outputs.contains(localVariable)) {
                    appendIndentation();
                    addCode(variableType).append("[] ").append(localVariable);
                    addCode(" = new ").append(variableType).append("[").append(bladeCount).append("];\n");
                }
            }
        }

        if (graph.getScalarVariables().size() > 0) {
            appendIndentation();
            addCode(variableType).append(" ");
            addCode(graph.getScalars().join());
            addCode(";\n");
        }

        node.getSuccessor().accept(this);
    }

    private HashSet<String> declaredVariableNames = new HashSet<>();

    private String getNewName(Variable variable) {
        return variable.getNewName(graph, useArrays);
    }

    @Override
    public void visit(AssignmentNode node) {
        Variable variable = node.getVariable();
        if (getNewName(variable) == "M_1.0") {
            addCode("");
        }
        String variableName = getNewName(variable);

        // What does this?
        if (assigned.contains(variableName)) {
            String message = "Variable " + variableName + " has been reset for reuse.";
            log.warn(message);
            Notifications.addWarning(message);

            appendIndentation();
            addCode("memset(");
            addCode(variableName);
            addCode(", 0, sizeof(");
            addCode(variableName);
            addCode(")); // Reset variable for reuse.\n");
            assigned.remove(variableName);
        }

        appendIndentation();

        if (useArrays) {
            node.getVariable().accept(this);
            addCode(" = ");
            node.getValue().accept(this);
            addCode(";");
        } else {
            // Prefix type ("float ") if the the variable was not declared yet
            if (declaredVariableNames.add(variableName)) {
                addCode(variableType + " ");
            }

            addCode(variableName);
            addCode(" = ");
            Expression expression = node.getValue();
            expression.accept(this);
            addCode(";");

        }

        addCode("\n");

        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(ExpressionStatement node) {
        appendIndentation();
        node.getExpression().accept(this);
        addCode(";\n");

        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(StoreResultNode node) {
        assigned.add(node.getValue().getNewName(graph, useArrays));
        node.getSuccessor().accept(this);
    }

    // The last node
    @Override
    public void visit(EndNode node) {

        // We need to fill the array when the new name was used
        if (!useArrays) {
            StringList outputVariables = graph.getOutputs();
            ArrayList<String> componentNames = new ArrayList<String>();

            for (String outputName : outputVariables) {
                // Iterate the current output variable marked by question mark (?)
                for (String componentName : declaredVariableNames) {
                    int index = componentName.lastIndexOf("_");
                    String outputNameFromComponent = componentName.substring(0, index);
                    if (outputNameFromComponent.equals(outputName)) {
                        componentNames.add(componentName);
                    }
                }
            }

            // Add blank file
            addCode("\n");
            appendIndentation();

            // Add return statement
            if (componentNames.size() == 1) {
                String name = componentNames.get(0);
                addCode("return " + name + ";\n");
                replaceInCode(" void ", " float ");
            } else if (componentNames.size() > 1) {
                String tupleType = "(" + componentNames.stream().map(it -> variableType + " " + it).collect(Collectors.joining(", ")) + ")";
                replaceInCode(" void ", " " + tupleType + " ");

                String line = "return (" + String.join(", ", componentNames) + ");\n";
                addCode(line);
            }
        }

        // Add closing brackets
        if (standalone) {
            indentation--;
            appendIndentation();
            addCode("}\n");
            indentation--;
            addCode("}\n");
        }

        if (!libraries.isEmpty()) {
            LinkedList<String> libs = new LinkedList<>(libraries);
            libs.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o2.compareTo(o1);
                }
            });

            // Add libraries at start
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
        addCode(funcName);
        addCode('(');
        mathFunctionCall.getOperand().accept(this);
        addCode(')');
    }

    @Override
    public void visit(Variable variable) {
        addCode(getNewName(variable));
    }

    @Override
    public void visit(MultivectorComponent component) {
        if (component.toString().equals("s1[1]")) {
            addCode("");
        }
        String name = component.getNewName(graph, useArrays);
        addCode(name);
//        addCode(name);
//        addCode('[');
//        addCode(bladeIndex);
//        addCode(']');
        System.out.println(name + "\n");
 }

    @Override
    public void visit(Exponentiation exponentiation) {
        if (isSquare(exponentiation)) {
            Multiplication m = new Multiplication(exponentiation.getLeft(), exponentiation.getLeft());
            m.accept(this);
        } else {
            libraries.add("using System;");
            addCode(mathLibrary + ".Pow(");
            exponentiation.getLeft().accept(this);
            addCode(',');
            exponentiation.getRight().accept(this);
            addCode(')');
        }
    }

    @Override
    public void visit(FloatConstant floatConstant) {
        double value = floatConstant.getValue();
        addCode(Double.toString(value));
        if (!useDouble)
        {
            addCode("f");
        }
    }

    @Override
    public void visit(Negation negation) {
        addCode('(');
        addCode('-');
        addChild(negation, negation.getOperand());
        addCode(')');
    }

    private void print(Object message)
    {
        System.out.println(message.toString());
    }

    // Method to replace all occurrences of a substring in a StringBuilder
    public void replaceInCode(String target, String replacement) {
        int index = code.indexOf(target);
        while (index != -1) {
            code.replace(index, index + target.length(), replacement);
            index = code.indexOf(target, index + replacement.length());
        }
    }
    private StringBuilder addCode(char text) {
        return addCode(String.valueOf(text));
        }

    private StringBuilder addCode(String text) {
        if (text.contains("M_1.0")) {
            addCode("");
        }
        code.append(text);
        return code;
    }
}
