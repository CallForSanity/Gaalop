package de.gaalop.codegenGapp;

import de.gaalop.CodeGenerator;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.gapp.visitor.PrettyPrint;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Set;

/**
 * Implements a code generator for gapp members, 
 * which lists all GAPP members in a graph in a pretty-printed format
 * @author Christian Steinmetz
 */
public class GAPPCodeGenerator implements CodeGenerator {

    @Override
    public Set<OutputFile> generate(ControlFlowGraph in) {
        String code = generateCode(in);

        String filename = generateFilename(in);

        OutputFile sourceFile = new OutputFile(filename, code, Charset.forName("UTF-8"));
        return Collections.singleton(sourceFile);
    }

    private String generateFilename(ControlFlowGraph in) {
        String filename = "gaalop.gapp";
        if (in.getSource() != null) {
            filename = in.getSource().getName();
            int lastDotIndex = filename.lastIndexOf('.');
            if (lastDotIndex != -1) {
                filename = filename.substring(0, lastDotIndex);
            }
            filename += ".gapp";
        }
        return filename;
    }

    /**
     * Generates source code for a control dataflow graph.
     *
     * @param in The graph that should be used for code generation
     * @return The genereated code
     */
    private String generateCode(ControlFlowGraph in) {
        PrettyPrint printer = new PrettyPrint();
        in.accept(printer);
        return printer.getResultString();
    }


}
