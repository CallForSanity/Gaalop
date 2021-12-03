package de.gaalop.ganja;

import de.gaalop.CodeGeneratorException;
import de.gaalop.DefaultCodeGenerator;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.io.IOUtils;
import java.nio.charset.Charset;

/**
 * This class facilitates C/C++ code generation.
 */
public class GanjaCodeGenerator extends DefaultCodeGenerator {
    
    private final Plugin plugin;
    private OutputMode outputMode;
    
    GanjaCodeGenerator(Plugin plugin, OutputMode outputMode) {
        super((outputMode == OutputMode.STANDALONE_PAGE) ? "html": "js");
    	this.plugin = plugin;
        this.outputMode = outputMode;
    }
    
    @Override
    public Set<OutputFile> generate(ControlFlowGraph graph) throws CodeGeneratorException {
        String code = generateCode(graph);

        String filename = generateFilename(graph);
        
        HashSet<OutputFile> result = new HashSet<OutputFile>();
        
        result.add(new OutputFile(filename, code, Charset.forName("UTF-8")));

        if (outputMode == OutputMode.STANDALONE_PAGE) {
            try {
                result.add(new OutputFile("ganja.js", IOUtils.toString(this.getClass().getResourceAsStream("ganja.js"), Charset.forName("UTF-8")), Charset.forName("UTF-8")));
            } catch (IOException ex) {
                throw new CodeGeneratorException(graph, "ganja.js not readable", ex);
            }
        }
        return result;
    }

    /**
     * Generates source code for a control dataflow graph.
     *
     * @param graph
     * @return
     */
    @Override
    protected String generateCode(ControlFlowGraph graph) {        
        try {
            return generateCodeStatic(graph, outputMode);
        } catch (Throwable error) {
            plugin.notifyError(error);
            return "";
        }
    }
    
    // This method is static, because it is also used from external
    public static String generateCodeStatic(ControlFlowGraph graph, OutputMode outputMode) {
        //For >=7d switch to graded representation, otherwise default to flat representation
        AlgebraProperties properties = AlgebraProperties.fromSignature(graph.getAlgebraDefinitionFile().getSignature());
        
        GanjaVisitor visitor = (properties.signature.getDimension() >= 7) 
                ? new GradedGanjaVisitor(properties)
                : new FlatGanjaVisitor(properties);
        

        graph.accept(visitor);
     
        //visitor.printDebugCode();
        
        return visitor.getCode(outputMode);
    }
}
