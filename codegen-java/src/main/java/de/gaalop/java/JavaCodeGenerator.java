package de.gaalop.java;

import de.gaalop.CodeGenerator;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import java.io.File;

import java.util.Set;
import java.util.Collections;
import java.nio.charset.Charset;
import java.util.HashSet;

/**
 * This class facilitates Java code generation.
 */
public class JavaCodeGenerator implements CodeGenerator {
    
    private final Plugin plugin;
    
    JavaCodeGenerator(Plugin plugin) {
    	this.plugin = plugin;
    }

    @Override
    public Set<OutputFile> generate(ControlFlowGraph in) {
        Set<OutputFile> result = new HashSet<OutputFile>();
        String code = generateCode(in);

        String filename = generateFilename(in);

        OutputFile sourceFile = new OutputFile(filename, code, Charset.forName("UTF-8"));
        result.add(sourceFile);

        result.add(new OutputFile(
                "GAProgram.java",
                createGAInterface(),
                Charset.forName("UTF-8")
                ));

        return result;
    }

    private String generateFilename(ControlFlowGraph in) {
        String filename = "gaalop.java";
        if (in.getSource() != null) {
            filename = in.getSource().getName();
            int lastDotIndex = filename.lastIndexOf('.');
            if (lastDotIndex != -1) {
                filename = filename.substring(0, lastDotIndex);
            }
            filename += ".java";
        }
        return filename;
    }

    /**
     * Generates source code for a control dataflow graph.
     *
     * @param in
     * @return
     */
    private String generateCode(ControlFlowGraph in) {
        JavaVisitor visitor = new JavaVisitor();
        try {
        	in.accept(visitor);
        } catch (Throwable error) {
        	plugin.notifyError(error);
        }
        return visitor.getCode();
    }

        /**
         * Returns a interface source for external Java programs,
         * which use the optimized code
         * @return The String containing the java interface source
         */
        private String createGAInterface() {
           return
            "\n"+
            "\n"+
            "/**\n"+
            " * Performs the calculations specified in a Geometric Algebra Program\n"+
            " */\n"+
            "public interface GAProgram {\n"+
            "\n"+
            "    /**\n"+
            "     * Performs the calculation\n"+
            "     */\n"+
            "    public void calculate();\n"+
            "\n"+
            "    /**\n"+
            "     * Returns the value of a variable\n"+
            "     * @param varName The variable name, specified in the Geometric Algebra program\n"+
            "     * @return The value of the variable with the given name\n"+
            "     */\n"+
            "    public float getValue(String varName);\n"+
            "\n"+
            "    /**\n"+
            "     * Sets the value of a variable\n"+
            "     * @param varName The variable name, specified in the Geometric Algebra program\n"+
            "     * @param value The value\n"+
            "     * @returns <value>true</value> if the setting was successful, <value>false</value> otherwise\n"+
            "     */\n"+
            "    public boolean setValue(String varName, float value);\n"+
            "\n"+
            "}\n";
        }

}
