package de.gaalop.java;

import de.gaalop.DefaultCodeGenerator;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import java.util.Set;
import java.nio.charset.Charset;
import java.util.HashSet;

/**
 * This class facilitates Java code generation.
 */
public class JavaCodeGenerator extends DefaultCodeGenerator {

    private final Plugin plugin;

    public JavaCodeGenerator(Plugin plugin) {
        super("java");
        this.plugin = plugin;
    }

    @Override
    public Set<OutputFile> generate(ControlFlowGraph in) {
        Set<OutputFile> result = new HashSet<OutputFile>();
        String filename = generateFilename(in);
        String code = generateCode(in);

        OutputFile sourceFile = new OutputFile(filename, code, Charset.forName("UTF-8"));
        result.add(sourceFile);

        result.add(new OutputFile(
                "GAProgram.java",
                createGAInterface(),
                Charset.forName("UTF-8")));

        return result;
    }

    @Override
    protected String generateCode(ControlFlowGraph in) {
        JavaVisitor visitor = new JavaVisitor();
        visitor.filename = generateFilename(in);
        try {
            in.accept(visitor);
        } catch (Throwable error) {
            plugin.notifyError(error);
        }
        return visitor.getCode();
    }

    /**
     * Returns an interface source for external Java programs,
     * that use the optimized code
     * @return The String containing the java interface source
     */
    private String createGAInterface() {
        return "\n"
                + "\n"
                + "import java.util.HashMap;\n"
                + "\n"
                + "/**\n"
                + " * Performs the calculations specified in a Geometric Algebra Program\n"
                + " */\n"
                + "public interface GAProgram {\n"
                + "\n"
                + "    /**\n"
                + "     * Performs the calculation\n"
                + "     */\n"
                + "    public void calculate();\n"
                + "\n"
                + "    /**\n"
                + "     * Returns the value of a variable\n"
                + "     * @param varName The variable name, specified in the Geometric Algebra program\n"
                + "     * @return The value of the variable with the given name\n"
                + "     */\n"
                + "    public double getValue(String varName);\n"
                + "\n"
                + "    /**\n"
                + "     * Sets the value of a variable\n"
                + "     * @param varName The variable name, specified in the Geometric Algebra program\n"
                + "     * @param value The value\n"
                + "     * @returns <value>true</value> if the setting was successful, <value>false</value> otherwise\n"
                + "     */\n"
                + "    public boolean setValue(String varName, double value);\n"
                + "\n"
                + "    /**\n"
                + "     * Returns all values in a map name->value\n"
                + "     * @return The map which contains all values\n"
                + "     */\n"
                + "    public HashMap<String,Double> getValues();\n"
                + "\n"
                + "}\n";
    }
}
