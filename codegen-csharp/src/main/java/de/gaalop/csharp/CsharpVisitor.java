package de.gaalop.csharp;

import static java.lang.System.out;
import de.gaalop.NonarrayCodeGeneratorVisitor;
import de.gaalop.Notifications;
import de.gaalop.StringList;
import de.gaalop.cfg.*;
import de.gaalop.dfg.*;
import java.util.stream.Collectors;
import java.util.*;
/**
 * This visitor traverses the control and data flow graphs and generates C#
 * code.
 *
 * To do: Checken ob Skalare auch für andere Programmiersprachen abstrahiert
 * werden können. // sqrt(A.~A)
 */
public class CsharpVisitor extends NonarrayCodeGeneratorVisitor {

    private static String MethodModifiers = "public static void";
    private static String MethodName = "Execute";

    /*
      When true, the generated code will be embedded inside class and method.
     */
    protected boolean standalone = true;

    protected Set<String> assigned = new HashSet<>();

    protected String mathLibrary = "MathF";

    protected Boolean useDouble = true;

//    protected Boolean normalizeOutputs = true;

    public CsharpVisitor(boolean standalone) {
        this.standalone = standalone;
    }

    public CsharpVisitor(String variableType) {
        this.standalone = false;
        this.numberType = variableType;
    }

    public CsharpVisitor(boolean standalone, boolean useDouble, boolean useArrays) {
        this.standalone = standalone;
        this.useDouble = useDouble;
        this.useArrays = useArrays;

        if (useDouble) {
            numberType = "double";
            mathLibrary = "Math";
        }
    }

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
                // The assumption here is that they all are normal scalars
                parameters.add(numberType + " " + inputVariable);
            }

            // Add outputs to parameters if arrays are used
            if (useArrays) {
                for (String outputVariable : outputs) {
                    parameters.add(numberType + "[] " + outputVariable);
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
                    addCode(numberType).append("[] ").append(localVariable);
                    addCode(" = new ").append(numberType).append("[").append(bladeCount).append("];\n");
                }
            }
        }

        if (graph.getScalarVariables().size() > 0) {
            appendIndentation();
            addCode(numberType).append(" ");
            addCode(graph.getScalars().join());
            addCode(";\n");
        }

        node.getSuccessor().accept(this);
    }

    @Override
    public void visit(AssignmentNode node) {
        // Get variable and its name from node
        Variable variable = node.getVariable();

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
            variable.accept(this);
        } else {
            // Prefix type if the the variable was not declared yet: "float "
            if (declaredVariableNames.add(variableName)) {
                addCode(numberType + " ");
            }

            addCode(variableName);
        }

        addCode(" = ");
        node.getValue().accept(this);
        // Add blades in comment

        if (useArrays && variable instanceof MultivectorComponent) {
            MultivectorComponent component = (MultivectorComponent) variable;
            addCode(" // " + graph.getBladeString(component));
        }

        addCode(";" + newline);

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

    @Override
    public void visit(EndNode node) {
        super.visit(node);

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
        String funcName;
        switch (mathFunctionCall.getFunction()) {
            case ABS:
                funcName = getMathLibrary() + ".Abs";
                break;
            case SQRT:
                funcName = getMathLibrary() + ".Sqrt";
                break;
            case COS:
                funcName = getMathLibrary() + ".Cos";
                break;
            case SIN:
                funcName = getMathLibrary() + ".Sin";
                break;
            case EXP:
                funcName = getMathLibrary() + ".Exp";
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

        out.println(name + "\n");
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

    public void setStandalone(boolean standalone) {
        this.standalone = standalone;
    }

    @Override
    protected String getNewName(Variable variable) {
        return variable.getNewName(graph, useArrays);
    }

    /*
      Gets the math library and adds neccessary imports.
     */
    private String getMathLibrary() {
        libraries.add("using System;\n");
        return mathLibrary;
    }

    @Override
    protected String getMethodName() {
        return MethodName;
    }

    @Override
    protected void addReturnType(List<String> returnTypes) {
        // Add brackets if tuples are used
        String openingBracket = returnTypes.size() > 1 ? "(" : "";
        String closingBracket = returnTypes.size() > 1 ? ")" : "";
        replaceInCode(" void ", " " + openingBracket + String.join(", ", returnTypes) + closingBracket + " ");
    }
}
