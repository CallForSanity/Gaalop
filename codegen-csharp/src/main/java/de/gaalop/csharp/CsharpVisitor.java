package de.gaalop.csharp;

import static java.lang.System.out;
import de.gaalop.DefaultCodeGeneratorVisitor;
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
public class CsharpVisitor extends DefaultCodeGeneratorVisitor {

    private static String MethodModifiers = "public static void";
    private static String MethodName = "Execute";

    /*
      When true, the generated code will be embedded inside class and method.
     */
    protected boolean standalone = true;

    protected Set<String> assigned = new HashSet<>();

    protected String numberType = "float";
    protected String mathLibrary = "MathF";

    protected Boolean useDouble = true;
    protected Boolean useArrays = true;

    protected Boolean normalizeOutputs = true;


    protected Set<String> libraries = new HashSet<>();

    private final String newline = "\n";

    private HashSet<String> declaredVariableNames = new HashSet<>();

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
            node.getVariable().accept(this);
        } else {
            // Prefix type if the the variable was not declared yet: "float "
            if (declaredVariableNames.add(variableName)) {
                addCode(numberType + " ");
            }

            addCode(variableName);
        }

        addCode(" = ");
        node.getValue().accept(this);
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

        // If no arrays are used, we need to return variables
        if (!useArrays) {
            StringList gaalopOutputVariables = graph.getOutputs();
            ArrayList<String> allBladeVariables = new ArrayList<String>();

            for (String variable : gaalopOutputVariables) {
                // Iterate the current output variable marked by question mark (?)
                for (String componentVariable : declaredVariableNames) {
                    String variableFromComponent = GetVariableNameFromBladeVariable(componentVariable);
                    if (variableFromComponent.equals(variable)) {
                        allBladeVariables.add(componentVariable);
                    }
                }
            }

            // Add blank file
            addLine();

            // Normalize outputs afterwards?
            if (true) {// normalizeOutputs) {
                addLine("// Normalizing outputs:");
                for (String variable : gaalopOutputVariables) {
                    String magnitudeVariable = variable + "_magnitude";

                    // Collect all component variables for the current Gaalop variable
                    List<String> componentVariables = allBladeVariables.stream()
                            .filter(componentVariable -> GetVariableNameFromBladeVariable(componentVariable).equals(variable))
                            .collect(Collectors.toList());

                    // Add code to calculate magnitude
                    addLine();
                    addLine(numberType + " " + magnitudeVariable + " = " + getMathLibrary() + ".Sqrt("
                            + componentVariables.stream().map(it -> it + " * " + it).collect(Collectors.joining(" + ")) + ");");

                    // Add code to divide my magnitude
                    for (String componentVariable : componentVariables) {
                        addLine(componentVariable + " = " + componentVariable + " / " + magnitudeVariable + ";");
                    }
                }
            }

            addLine();

            Set<ReturnDefinition> definitions = graph.getReturnDefinitions();

            // The types that the function returns (as tuples)
            List<String> returnTypes = new LinkedList<String>();

            // Each list of string will be printed in an own line for grouping purposes
            List<String> returnValues = new ArrayList<String>();

            for (String variable : gaalopOutputVariables) {

                // Collect all component variables for the current Gaalop variable, e.g. p5_e01
                List<String> bladeVariables = allBladeVariables.stream()
                        .filter(var -> GetVariableNameFromBladeVariable(var).equals(variable))
                        .collect(Collectors.toList());

                // Check if any ReturnDefinition has the variableName equal to 'name'
                ReturnDefinition matchingDefinition = definitions.stream()
                        .filter(definition -> Arrays.stream(definition.variableNames).anyMatch(it -> it.equals(variable)))
                        .findFirst()
                        .orElse(null);

                // Apply return definitions defined by return pragma.
                if (matchingDefinition != null) {
                    // Set return type and values based on return definition
                    returnTypes.add(matchingDefinition.returnType);

                    // Create return text with each bladename exchanged by variable_bladename
                    String returnText = matchingDefinition.returnText;
                    for (String bladeVariable : bladeVariables) {
                        String bladeName = GetBladeNameFromBladeVariable(bladeVariable);
                        returnText = returnText.replace("<" + bladeName + ">", bladeVariable);
                    }

                    returnValues.add(returnText);
                } else {
                    if (allBladeVariables.size() == 1) {
                        // When using one return value, the type is sufficient
                        returnTypes.add(numberType);
                    } else {
                        // When having tuples (multiple returns), we wanna use NAMED tuples. So we add the name after each type.
                        returnTypes.addAll(bladeVariables.stream().map(var -> numberType + " " + var).collect(Collectors.toList()));
                    }
                    returnValues.add(bladeVariables.stream().collect(Collectors.joining(", ")));
                }
            }

            // Add brackets if tuples are used
            String openingBracket = returnTypes.size() > 1 ? "(" : "";
            String closingBracket = returnTypes.size() > 1 ? ")" : "";

            // Replace void by new return type
            replaceInCode(" void ", " " + openingBracket + String.join(", ", returnTypes) + closingBracket + " ");

            // Add return statement
            int size = returnValues.size();
            if (size == 1) {
                // Return single Gaalop variables in one line
                addLine("return " + openingBracket + String.join(", ", returnValues.getFirst()) + closingBracket + ";");
            } else {
                // Return multiple Gaalop variables in multiple lines
                addLine("return " + openingBracket);
                indentation++;
                int i = 0;
                for (String returnValue : returnValues) {
                    addLine(returnValue + (++i < size ? ", " : ""));
                }
                indentation--;
                addLine(closingBracket + ";");
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

    private String getNewName(Variable variable) {
        return variable.getNewName(graph, useArrays);
    }

    public void setStandalone(boolean standalone) {
        this.standalone = standalone;
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

    private StringBuilder addLine() {
        return addLine("");
    }

    /*
    Adds text with a previous indentation.
     */
    private StringBuilder addLine(String text) {
        appendIndentation();
        return addCode(text + newline);
    }

    private StringBuilder addCode(String text) {

        if (text.contains("M_1.0")) {
            addCode("");
        }

        out.println(text);

        code.append(text);
        return code;
    }

    /*
      Gets the math library and adds neccessary imports.
     */
    private String getMathLibrary() {
        libraries.add("using System;\n");
        return mathLibrary;
    }
    /*
      Takes in p5_e01 and returns p5 (the variables defined in Gaalop script).
     */
    private String GetVariableNameFromBladeVariable(String componentVariable) {
        int index = componentVariable.lastIndexOf("_");
        return componentVariable.substring(0, index);
    }

    /*
      Takes in p5_e01 and returns e01 (the component string).
     */
    private String GetBladeNameFromBladeVariable(String componentVariable) {
        int index = componentVariable.lastIndexOf("_") + 1;
        return componentVariable.substring(index);
    }

    /*
    Joings the list to a string using the given separator.
     */
    private String JoinString(ArrayList<String> componentVariables, String separator) {
        return componentVariables.stream().collect(Collectors.joining(separator));
    }
}
