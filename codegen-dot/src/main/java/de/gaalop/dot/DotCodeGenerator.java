package de.gaalop.dot;

import de.gaalop.CodeGenerator;
import de.gaalop.OutputFile;
import de.gaalop.CodeGeneratorException;
import de.gaalop.cfg.ControlFlowGraph;

import java.util.Set;
import java.util.Collections;
import java.nio.charset.Charset;

/**
 * This class implements the DOT code generator.
 */
public enum DotCodeGenerator implements CodeGenerator {
    INSTANCE;

    @Override
    public Set<OutputFile> generate(ControlFlowGraph in) throws CodeGeneratorException {
        String dotCode = generateCode(in);
        String filename = generateFilename(in);
        Charset charset = Charset.forName("UTF-8");

        OutputFile output = new OutputFile(filename, dotCode, charset);
        return Collections.singleton(output);
    }

    private String generateFilename(ControlFlowGraph in) {
        String filename = "gaalop.dot";
        if (in.getSource() != null) {
            filename = in.getSource().getName();
            int lastDotIndex = filename.lastIndexOf('.');
            if (lastDotIndex != -1) {
                filename = filename.substring(0, lastDotIndex);
            }
            filename += ".dot";
        }
        return filename;
    }

    private String generateCode(ControlFlowGraph in) {
        CfgVisitor visitor = new CfgVisitor();
        in.accept(visitor);
        return visitor.getResult();
    }
}
