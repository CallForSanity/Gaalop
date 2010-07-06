package de.gaalop.codegen_verilog;

import de.gaalop.CodeGenerator;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.codegen_verilog.VerilogIR.VerilogDFG;
import de.gaalop.optimizations.CSE.CSE_Collector;

import de.gaalop.optimizations.ConstantFolding;
import de.gaalop.quadriererOptimierer.Quadopt;
import java.util.Set;
import java.util.Collections;
import java.nio.charset.Charset;

/**
 * This class facilitates Verilog code generation.
 */
public enum VerilogCodegen implements CodeGenerator {

    INSTANCE;

    @Override
    public Set<OutputFile> generate(ControlFlowGraph in) {
    	
    	
    	
    	
        String code = generateCode(in);

        String filename = generateFilename(in);

        OutputFile sourceFile = new OutputFile(filename, code, Charset.forName("UTF-8"));
        return Collections.singleton(sourceFile);
    }

    
    
    
    private String generateFilename(ControlFlowGraph in) {
        String filename = "gaalop";
        if (in.getSource() != null) {
            filename = in.getSource().getName();
            int lastDotIndex = filename.lastIndexOf('.');
            if (lastDotIndex != -1) {
                filename = filename.substring(0, lastDotIndex);
            }
            filename += ".v";
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
      ConstantFolding cf = new ConstantFolding();
      // to start CSE remove the comments on the following line remove quadopt instead
      //in.accept(new CSE_Collector());
      in.accept(new Quadopt());
      in.accept(cf);
    	VerilogDFG mydfg = new VerilogDFG(in);
    	
    	
    	
    	// different steps
    	
    	//todo IR conversion
    	
    	//restructuring of Tree
    	
    	// leveling of tree
    	
    	// floating point conversion
    	
    	
    	// code generation
    	System.out.println(in.toString());
        // Old Code
    	//VerilogVisitor visitor = new VerilogVisitor();
        //in.accept(visitor);
        //return visitor.getCode();
    	return mydfg.getIrvisit().getResult();
    }

}
