package de.gaalop.testbenchTbaGapp.tbaNew.framework;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.dfg.Variable;
import de.gaalop.testbenchTbaGapp.tbaNew.framework.CFGInterpreter;
import java.nio.charset.Charset;
import java.util.*;

/**
 *
 * @author Christian Steinmetz
 */
public class TBATestCodeGenerator implements CodeGenerator {

    private CFGInterpreter interpreter;
    private boolean sort;

    public TBATestCodeGenerator(HashMap<Variable, Double> inputVariables, boolean sort) {
        interpreter = new CFGInterpreter(inputVariables);
        this.sort = sort;
    }

    @Override
    public Set<OutputFile> generate(ControlFlowGraph in) throws CodeGeneratorException {
        in.accept(interpreter);
        
        StringBuilder contentMap = new StringBuilder();
        StringBuilder contentOutput = new StringBuilder();
        HashMap<Variable, Double> mapVariables = interpreter.getMapVariables();
        HashSet<Variable> outputVariables = interpreter.getOutputVariables();
        
        ArrayList<Variable> mapVariablesList = new ArrayList<Variable>(mapVariables.keySet());
        ArrayList<Variable> outputVariablesList = new ArrayList<Variable>(outputVariables);
        
        if (sort) {            
            Collections.sort(mapVariablesList, new Comparator<Variable>() {
                @Override
                public int compare(Variable o1, Variable o2) {
                    return o1.toString().compareTo(o2.toString());
                }
            });
        }
        
        for (Variable v: mapVariablesList) {
            contentMap.append(v.toString()+" = "+mapVariables.get(v).toString());
            contentMap.append("\n");
        }
        
        for (Variable v: outputVariablesList) {
            
            LinkedList<Integer> indices = new LinkedList<Integer>();

            for (String outputVarStr: in.getPragmaOutputVariables()) {
                String[] parts = outputVarStr.split("\\$");
                if (parts[0].equals(v.getName())) 
                    indices.add(Integer.parseInt(parts[1]));
            }

            if (indices.isEmpty()) {
                for (Variable vi: mapVariablesList)
                    if (vi.getName().equals(v.getName()))
                        contentOutput.append(vi.toString()+" = "+mapVariables.get(vi).toString()+"\n");
            } else {
                for (Variable vi: mapVariablesList)
                    if (vi.getName().equals(v.getName()) && indices.contains(((MultivectorComponent) vi).getBladeIndex()))
                        contentOutput.append(vi.toString()+" = "+mapVariables.get(vi).toString()+"\n");
            }
        }

        HashSet<OutputFile> result = new HashSet<OutputFile>();
        result.add(new OutputFile("Map Values", contentMap.toString(), Charset.forName("UTF-8")));
        result.add(new OutputFile("Output Values", contentOutput.toString(), Charset.forName("UTF-8")));
        return result;
    }

}
