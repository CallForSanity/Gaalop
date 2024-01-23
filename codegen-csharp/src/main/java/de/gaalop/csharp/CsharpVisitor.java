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
        // Get variable and its name from node
        Variable variable = node.getVariable();
        if (getNewName(variable) == "M") {
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
            // Prefix type if the the variable was not declared yet: "float "
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

    /*
      A normal variable is for instance p5 (like defined in Gaalop script) and
      one of its component variables is p5_e01.
     */
    private String GetVariableFromComponentVariable(String componentVariable) {
        int index = componentVariable.lastIndexOf("_");
        return componentVariable.substring(0, index);
    }

    private String JoinString(ArrayList<String> componentVariables, String separator) {
        return componentVariables.stream().collect(Collectors.joining(separator));
    }

    // The last node
    @Override
    public void visit(EndNode node) {

        // If no arrays are used, we need to return variables
        if (!useArrays) {
            StringList gaalopOutputVariables = graph.getOutputs();
            ArrayList<String> allComponentVariables = new ArrayList<String>();

            for (String variable : gaalopOutputVariables) {
                // Iterate the current output variable marked by question mark (?)
                for (String componentVariable : declaredVariableNames) {
                    String variableFromComponent = GetVariableFromComponentVariable(componentVariable);
                    if (variableFromComponent.equals(variable)) {
                        allComponentVariables.add(componentVariable);
                    }
                }
            }

            // Add blank file
            addLine();

            if (normalizeOutputs) {
                addLine("// Normalizing outputs:");
                for (String variable : gaalopOutputVariables) {
                    String magnitudeVariable = variable + "_magnitude";

                    // Collect all component variables for the current Gaalop variable
                    List<String> componentVariables = allComponentVariables.stream()
                            .filter(componentVariable -> GetVariableFromComponentVariable(componentVariable).equals(variable))
                            .collect(Collectors.toList());

                    // Add code to calculate magnitude
                    addLine();
                    addLine(variableType + " " + magnitudeVariable + " = " + getMathLibrary() + ".Sqrt("
                            + componentVariables.stream().map(it -> it + " * " + it).collect(Collectors.joining(" + ")) + ");");

                    // Add code to divide my magnitude
                    for (String componentVariable : componentVariables) {
                        addLine(componentVariable + " = " + componentVariable + " / " + magnitudeVariable + ";");
                    }
                }
            }

            addLine();

            Set<ReturnDefinition> definitions = graph.getPragmaReturns();

            LinkedList<String> returnTypes = new LinkedList<String>();
            LinkedList<String> returnValues = new LinkedList<String>();

            for (String variable : gaalopOutputVariables) {

                // Collect all component variables for the current Gaalop variable
                List<String> componentVariables = allComponentVariables.stream()
                        .filter(componentVariable -> GetVariableFromComponentVariable(componentVariable).equals(variable))
                        .collect(Collectors.toList());

                // Check if any ReturnDefinition has the variableName equal to 'name'
                ReturnDefinition matchingDefinition = definitions.stream()
                        .filter(definition -> definition.variableName.equals(variable))
                        .findFirst()
                        .orElse(null);

                /* Apply return definitions defined by pragmas. Consider following Gaalop script:

                     //#pragma return line Line(e1, e2)
                     ?line = e1 + e2

                   The pragma return statement will alter the generated return statement in C#:

                     return (line_e2, line_e1);         // without pragma
                     return new Line(line_e1, line_e2)  // with pragma
                 */
                if (matchingDefinition != null) {
                    returnTypes.add(matchingDefinition.className);
                    returnValues.add("new " + matchingDefinition.className + "("
                            + Arrays.stream(matchingDefinition.variables).map(var -> variable + "_" + var).collect(Collectors.joining(", ")) + ")");
                } else {
                    if (allComponentVariables.size() == 1) {
                        // When using one return value, the type is sufficient
                        returnTypes.add(variableType);
                    } else {
                        // When having tuples (multiple returns), we wanna use NAMED tuples. So we add the name after each type.
                        returnTypes.addAll(componentVariables.stream().map(var -> variableType + " " + var).collect(Collectors.toList()));

                    }
                    returnValues.addAll(componentVariables);
                }
            }

            // Add brackets if tuples are used
            String openingBracket = returnTypes.size() > 1 ? "(" : "";
            String closingBracket = returnTypes.size() > 1 ? ")" : "";

            // Replace void by new return type
            replaceInCode(" void ", " " + openingBracket + String.join(", ", returnTypes) + closingBracket + " ");

            // Add return statement
            String line = "return " + openingBracket + String.join(", ", returnValues) + closingBracket + ";";
            addLine(line);
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

    /*
      Gets the math library and adds neccessary imports.
     */
    private String getMathLibrary() {
        libraries.add("using System;\n");
        return mathLibrary;
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
}
