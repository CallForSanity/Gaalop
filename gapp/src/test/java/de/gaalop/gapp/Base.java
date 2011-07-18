package de.gaalop.gapp;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.CodeGeneratorPlugin;
import de.gaalop.CodeParser;
import de.gaalop.CodeParserException;
import de.gaalop.InputFile;
import de.gaalop.gapp.executer.Executer;
import java.util.HashMap;
import de.gaalop.OptimizationException;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.DivisionRemover;
import de.gaalop.dfg.ExpressionRemover;
import de.gaalop.gapp.codegen.GAPPCodeGeneratorPlugin;
import de.gaalop.gapp.importing.GAPPImportingMain;
import de.gaalop.gapp.importing.Splitter;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author christian
 */
public class Base {

     protected Executer executeProgramm(GAPPTestable testable, String cluName) throws OptimizationException, CodeParserException {
        CodeParser parser = (new de.gaalop.clucalc.input.Plugin()).createCodeParser();
        ControlFlowGraph graph = parser.parseFile(new InputFile(cluName, testable.getSource()));

        GAPPImportingMain importer = new GAPPImportingMain();
        importer.importGraph(graph);

        //Evaluate!
        HashMap<String, Float> inputValues = testable.getInputs();
        de.gaalop.tba.cfgImport.CFGImporter importer2 = new de.gaalop.tba.cfgImport.CFGImporter(true,false);
        
        outputPlugin(new GAPPCodeGeneratorPlugin(), graph);
        outputPlugin(new de.gaalop.clucalc.output.Plugin(), graph);

        Executer executer = new Executer(importer2.getUsedAlgebra(),inputValues);
        graph.accept(executer);
        return executer;
    }

     public static void outputPlugin(CodeGeneratorPlugin plugin, ControlFlowGraph graph) {
        CodeGenerator generator = plugin.createCodeGenerator();
        Set<OutputFile> outputFiles;
        try {
            outputFiles = generator.generate(graph);
            for (OutputFile outputFile: outputFiles)
                writeFile(outputFile);
        } catch (CodeGeneratorException ex) {
            Logger.getLogger(GAPPImportingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected static void writeFile(OutputFile outputFile) {
        try {
            PrintWriter out = new PrintWriter("src/test/java/de/gaalop/gapp/generatedTests/"+outputFile.getName());
            out.print(outputFile.getContent());
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GAPPImportingMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
