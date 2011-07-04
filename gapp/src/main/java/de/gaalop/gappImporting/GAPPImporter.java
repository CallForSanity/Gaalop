package de.gaalop.gappImporting;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.CodeParser;
import de.gaalop.CodeParserException;
import de.gaalop.InputFile;
import de.gaalop.OptimizationException;
import de.gaalop.OptimizationStrategy;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Facade class to import the ControlFlowGraph in a GAPP ControlFlowGraph
 * @author christian
 */
public class GAPPImporter {

    	public ControlFlowGraph importGraph(ControlFlowGraph graph) {
            Splitter splitter = new Splitter(graph);
            graph.accept(splitter);

            CFGImporter vCFG = new CFGImporter();
            graph.accept(vCFG);
            return graph;
	}

        public static void main(String[] args) {
            try {
            CodeParser parser = (new de.gaalop.clucalc.input.Plugin()).createCodeParser();
            ControlFlowGraph graph = parser.parseFile(new InputFile("Circle", getSource()));
           
            GAPPImporter importer = new GAPPImporter();
            importer.importGraph(graph);

           // CodeGenerator generator = (new de.gaalop.clucalc.output.Plugin()).createCodeGenerator();
            CodeGenerator generator = (new GAPPCodeGeneratorPlugin()).createCodeGenerator();
            Set<OutputFile> outputFiles = generator.generate(graph);

          for (OutputFile outputFile: outputFiles)
                writeFile(outputFile);


        } catch (CodeParserException ex) {
            Logger.getLogger(GAPPImporter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CodeGeneratorException ex) {
                Logger.getLogger(GAPPImporter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


        private static String getSource() {
            return
            "DefVarsN3();"+"\n"+
            ":IPNS;"+"\n"+

            "//#pragma output m_1"+"\n"+
            "//#pragma output m_2"+"\n"+
            "//#pragma output r_0"+"\n"+

            "\n"+

            "v1 = x1*e1+y1*e2;"+"\n"+
            "v2 = x2*e1+y2*e2;"+"\n"+
            "v3 = x3*e1+y3*e2;"+"\n"+

    //        "p1 = v1 + 0.5*v1*v1*einf + e0;"+"\n"+
    //        "p2 = v2 + 0.5*v2*v2*einf + e0;"+"\n"+
    //        "p3 = v3 + 0.5*v3*v3*einf + e0;"+"\n"+

    //        "c = *(p1^p2^p3);"+"\n"+

    //       "m = c*einf*c;"+"\n"+
    //        "?m = -m/(m.einf);"+"\n"+
    //        "?r = sqrt(abs(c.c/((einf.c)*(einf.c))));"+"\n"
                    "?v3;\n"
            ;
        }

        private static void writeFile(OutputFile outputFile) {
        try {
            PrintWriter out = new PrintWriter("src/test/java/de/gaalop/gapp/generatedTests/"+outputFile.getName());
            //out.println("package de.gaalop.gapp.generatedTests;\n");
            out.print(outputFile.getContent());
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GAPPImporter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
