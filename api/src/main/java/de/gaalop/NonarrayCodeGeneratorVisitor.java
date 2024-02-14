/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.gaalop;

import de.gaalop.cfg.ControlFlowVisitor;
import de.gaalop.cfg.EndNode;
import de.gaalop.cfg.ReturnDefinition;
import de.gaalop.dfg.ExpressionVisitor;
import de.gaalop.dfg.Variable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class NonarrayCodeGeneratorVisitor extends DefaultCodeGeneratorVisitor {
    protected abstract String getMethodName();

    protected Boolean useArrays = true;
    protected String numberType = "float";

    protected HashSet<String> declaredVariableNames = new HashSet<>();

    protected Set<String> libraries = new HashSet<>();

    protected Boolean useNamedTuples() {
        return true;
    }

    /*
    Add the return statement when no arrays are used.
     */
    @Override
    public void visit(EndNode node) {
        super.visit(node);

        // If no arrays are used, we need to return variables
        if (!useArrays) {
            StringList gaalopOutputVariables = graph.getOutputs();

            ArrayList<String> allBladeVariables = getAllBladeVariables(gaalopOutputVariables);

            // Add blank line
            addLine();

            Set<ReturnDefinition> definitions = graph.getReturnDefinitions();

            // The types that the function returns (as tuples)
            List<String> returnTypes = new LinkedList<String>();

            // Each list of string will be printed in an own line for grouping purposes
            List<String> returnValues = new ArrayList<String>();

            // Get the return object text for each variable
            for (String variable : gaalopOutputVariables) {
                String value = getReturnValue(allBladeVariables, variable, definitions, returnTypes);
                returnValues.add(value);
            }

            // Add brackets if tuples are used
            String openingBracket = returnTypes.size() > 1 ? "(" : "";
            String closingBracket = returnTypes.size() > 1 ? ")" : "";

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

            addReturnType(returnTypes);
        }
    }

    /*
      Here, you can add the method return type, e.g. by replacing code in the method header.
     */
    protected abstract void addReturnType(List<String> returnTypes);

    private String getReturnValue(ArrayList<String> allBladeVariables, String variable, Set<ReturnDefinition> definitions, List<String> returnTypes) {
        String returnValue;

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
            returnValue = matchingDefinition.returnText;
            for (String bladeVariable : bladeVariables) {
                String bladeName = GetBladeNameFromBladeVariable(bladeVariable);
                returnValue = returnValue.replace("<" + bladeName + ">", bladeVariable);
            }

        } else {
            if (allBladeVariables.size() == 1) {
                // When using one return value, the type is sufficient
                returnTypes.add(numberType);
            } else {
                if (useNamedTuples()) {
                    // When having tuples (multiple returns), we wanna use NAMED tuples. So we add the name after each type.
                    returnTypes.addAll(bladeVariables.stream().map(var -> numberType + " " + var).collect(Collectors.toList()));
                } else {
                    returnTypes.addAll(bladeVariables.stream().map(var -> numberType).collect(Collectors.toList()));
                }
            }
            returnValue = bladeVariables.stream().collect(Collectors.joining(", "));

        }
        return returnValue;
    }

    private ArrayList<String> getAllBladeVariables(StringList gaalopOutputVariables) {
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
        return allBladeVariables;
    }

    /*
      Gets the name of the variable which incorporates "useArrays"
     */
    protected String getNewName(Variable variable) {
        return variable.getNewName(graph, useArrays);
    }
    /*
      Takes in p5_e01 and returns p5 (the variables defined in Gaalop script).
     */
    protected String GetVariableNameFromBladeVariable(String componentVariable) {
        int index = componentVariable.lastIndexOf("_");
        return componentVariable.substring(0, index);
    }

    /*
      Takes in p5_e01 and returns e01 (the component string).
     */
    protected String GetBladeNameFromBladeVariable(String componentVariable) {
        int index = componentVariable.lastIndexOf("_") + 1;
        return componentVariable.substring(index);
    }

}
