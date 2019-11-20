package de.gaalop.julia;

import de.gaalop.CodeGenerator;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;

import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.Collections;

public enum JuliaCodeGenerator implements CodeGenerator {

    INSTANCE;

    @Override
    public Set<OutputFile> generate(ControlFlowGraph in) {
        String code = generateCode(in);

        String filename = generateFilename(in);

        OutputFile sourceFile = new OutputFile(filename, code, StandardCharsets.UTF_8);
        return Collections.singleton(sourceFile);
    }

    private String generateFilename(ControlFlowGraph in) {
        String filename = "gaalop.txt";
        if (in.getSource() != null) {
            filename = in.getSource().getName();
            int lastDotIndex = filename.lastIndexOf('.');
            if (lastDotIndex != -1) {
                filename = filename.substring(0, lastDotIndex);
            }
            filename += ".txt";
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
        JuliaVisitor visitor = new JuliaVisitor();
        in.accept(visitor);
        return visitor.getCode();
    }

}
