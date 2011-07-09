/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.gapp.codegen;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.gapp.instructionSet.GAPPAddMv;
import de.gaalop.gapp.instructionSet.GAPPAssignMv;
import de.gaalop.gapp.instructionSet.GAPPCalculate;
import de.gaalop.gapp.instructionSet.GAPPDotVectors;
import de.gaalop.gapp.instructionSet.GAPPResetMv;
import de.gaalop.gapp.instructionSet.GAPPSetMv;
import de.gaalop.gapp.instructionSet.GAPPSetVector;
import de.gaalop.gapp.visitor.CFGGAPPVisitor;
import de.gaalop.gapp.visitor.PrettyPrint;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author christian
 */
class GAPPCodeGenerator implements CodeGenerator {

    public GAPPCodeGenerator() {
    }

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
     * @param in
     * @return
     */
    private String generateCode(ControlFlowGraph in) {
        final PrettyPrint visitor = new PrettyPrint();

        CFGGAPPVisitor v = new CFGGAPPVisitor() {

            @Override
            public Object visitAddMv(GAPPAddMv gappAddMv, Object arg) {
                gappAddMv.accept(visitor, null);
                return null;
            }

            @Override
            public Object visitAssignMv(GAPPAssignMv gappAssignMv, Object arg) {
                gappAssignMv.accept(visitor, null);
                return null;
            }

            @Override
            public Object visitDotVectors(GAPPDotVectors gappDotVectors, Object arg) {
                gappDotVectors.accept(visitor, null);
                return null;
            }

            @Override
            public Object visitResetMv(GAPPResetMv gappResetMv, Object arg) {
                gappResetMv.accept(visitor, null);
                return null;
            }

            @Override
            public Object visitSetMv(GAPPSetMv gappSetMv, Object arg) {
                gappSetMv.accept(visitor, null);
                return null;
            }

            @Override
            public Object visitSetVector(GAPPSetVector gappSetVector, Object arg) {
                gappSetVector.accept(visitor, null);
                return null;
            }

            @Override
            public Object visitCalculate(GAPPCalculate gappCalculate, Object arg) {
                gappCalculate.accept(visitor, null);
                return null;
            }
        };

        in.accept(v);
        return visitor.getResultString();
    }


}
