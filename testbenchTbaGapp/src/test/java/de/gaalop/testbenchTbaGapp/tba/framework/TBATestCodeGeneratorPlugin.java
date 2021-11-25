package de.gaalop.testbenchTbaGapp.tba.framework;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorPlugin;
import de.gaalop.dfg.Variable;
import java.awt.Image;
import java.util.HashMap;

/**
 *
 * @author Christian Steinmetz
 */
public class TBATestCodeGeneratorPlugin implements CodeGeneratorPlugin {
    
    private HashMap<Variable, Double> inputVariables;

    public TBATestCodeGeneratorPlugin(HashMap<Variable, Double> inputVariables) {
        this.inputVariables = inputVariables;
    }

    @Override
    public CodeGenerator createCodeGenerator() {
        return new TBATestCodeGenerator(inputVariables, true);
    }

    @Override
    public String getName() {
        return "TBA TestGenerator";
    }

    @Override
    public String getDescription() {
        return "This code generator provides a simple value evaluation.";
    }

    @Override
    public Image getIcon() {
        return null;
    }

}
