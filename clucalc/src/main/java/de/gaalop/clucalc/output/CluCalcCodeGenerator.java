package de.gaalop.clucalc.output;

import de.gaalop.CodeGenerator;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Set;

/**
 * This class generates CluCalc code from a Control Dataflow Graph.
 *
 * It is implemented as a singleton because it has no state.
 */
public class CluCalcCodeGenerator implements CodeGenerator {
	
	private String suffix;

	public CluCalcCodeGenerator(String suffix) {
		this.suffix = suffix;
	}

    @Override
    public Set<OutputFile> generate(ControlFlowGraph in) {
        String code = generateCode(in);
        
        String filename = generateFilename(in);

        OutputFile sourceFile = new OutputFile(filename, code, Charset.forName("UTF-8"));
        return Collections.singleton(sourceFile);
    }
    
    private String generateFilename(ControlFlowGraph in) {
        String filename = "gaalop.clu";
        if (in.getSource() != null) {
            filename = in.getSource().getName();
            int lastDotIndex = filename.lastIndexOf('.');
            if (lastDotIndex != -1) {
                filename = filename.substring(0, lastDotIndex);
            }
            filename += ".clu";
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
        CfgVisitor visitor = new CfgVisitor(suffix);
        in.accept(visitor);
        return visitor.getCode();
    }

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

}
