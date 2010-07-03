package de.gaalop.codegen_verilog;

import de.gaalop.CodeGenerator;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.codegen_verilog.VerilogIR.VerilogDFG;
import de.gaalop.cpp.CppVisitor;
import de.gaalop.optimizations.CSE.CSE_Collector;

import de.gaalop.optimizations.CSE.CSE_Collector;
import de.gaalop.optimizations.ConstantFolding;
import de.gaalop.optimizations.ConstantKiller.ConstanKillCrawler;
import de.gaalop.quadriererOptimierer.Quadopt;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.Collections;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.omg.PortableInterceptor.INACTIVE;

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
      CppVisitor cpp = new CppVisitor();
      in.accept(cpp);
      try {
            BufferedWriter w = new BufferedWriter(new FileWriter("original.cpp"));
            w.write(cpp.getCode());
            w.flush();
            w.close();
        } catch (IOException ex) {
            Logger.getLogger(VerilogCodegen.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
      ConstantFolding cf = new ConstantFolding();
      in.accept(cf);
      for(int i=0;i<20;++i) {
        in.accept(new ConstanKillCrawler());
        in.accept(new ConstantFolding());
      }
      // to start CSE remove the comments on the following line remove quadopt instead
      in.accept(new CSE_Collector());
      in.accept(new CSE_Collector());
      //in.accept(new Quadopt());
    	
    	cpp = new CppVisitor();
        in.accept(cpp);
        
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter("junk.cpp"));
            w.write(cpp.getCode());
            w.flush();
            w.close();
        } catch (IOException ex) {
            Logger.getLogger(VerilogCodegen.class.getName()).log(Level.SEVERE,
                    null, ex);
        }

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
