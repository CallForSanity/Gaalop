package de.gaalop.tba;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.gappImporting.cfgimport.CFGImporter;
import de.gaalop.gappImporting.cfgimport.CFGImporter2;
import de.gaalop.gappImporting.cfgimport.ConstantFolding;

/**
 * This class implements a basic starter kit for testing the tba plugin
 * @author christian
 */
public class TestTBA {

    public static void main(String[] args) {
        TestTBA testTBA = new TestTBA();

    }

    public TestTBA() {
        //basicProcessing("Kreis");
       basicProcessing("KreisInstance");
    }

    /**
     * Do basic processing of a file
     * @param file The file to be processed without ending
     */
    private void basicProcessing(String file) {
        String baseDir = "/daten/";
        String inDir = baseDir+"InputFiles/";
        String outDir = baseDir+"GeneratedFiles/";

        //Read input
        ControlFlowGraph graphIn = GraphIOMethods.readGraphFromClu(inDir+file+".clu");

        //Modify graph
        ControlFlowGraph graphOut = modifyGraph(graphIn);

        //Output graph
        GraphIOMethods.writeGraphToFile(graphOut, outDir+file+".clu", new de.gaalop.clucalc.output.Plugin());
        GraphIOMethods.writeGraphToFile(graphOut, outDir+file+".dot", new de.gaalop.dot.Plugin());
        GraphIOMethods.writeGraphToFile(graphOut, outDir+file+".java", new de.gaalop.java.Plugin());

    }

    private ControlFlowGraph modifyGraph(ControlFlowGraph graphIn) {
        ControlFlowGraph graphOut = null;

        CFGImporter2 importer = new CFGImporter2();
        graphOut = importer.importGraph(graphIn);

        ConstantFolding folding = new ConstantFolding();
        graphOut.accept(folding);


        return graphOut;
    }







}
