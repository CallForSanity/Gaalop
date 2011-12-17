package de.gaalop.gappDebugger;

import de.gaalop.gapp.instructionSet.GAPPBaseInstruction;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;

/**
 *
 * @author christian
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RecognitionException, FileNotFoundException, IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader("/home/christian/gapp/Circle.gapp"));
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }

        ANTLRStringStream inputStream = new ANTLRStringStream(sb.toString());
        GappLexer lexer = new GappLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        GappParser parser = new GappParser(tokenStream);
        GappParser.script_return parserResult = parser.script();

        if (!parser.getErrors().isEmpty()) {
            StringBuilder message = new StringBuilder();
            message.append("Unable to parse CluCalc file:\n");
            for (String error : parser.getErrors()) {
                message.append(error);
                message.append('\n');
            }
            System.err.println(message);
            return;
        }

        if (parserResult.getTree() == null) {
            System.out.println("The input file is empty.");
            return;
        }

        CommonTreeNodeStream treeNodeStream = new CommonTreeNodeStream(parserResult.getTree());
        GappTransformer transformer = new GappTransformer(treeNodeStream);
        GAPPBuilder graph = transformer.script();
        System.out.println(graph.getInstructions().size());
        int count = 0;
        for (GAPPBaseInstruction i: graph.getInstructions())
            if (i != null)
                count++;
            else
                System.out.println("F: "+count);
        System.out.println(count);


        if (!parser.getErrors().isEmpty()) {
            StringBuilder message = new StringBuilder();
            message.append("Unable to parse CluCalc file:\n");
            for (String error : parser.getErrors()) {
                message.append(error);
                message.append('\n');
            }
            return;
        }




/*
        UI ui = new UI();
        ui.controller = new Controller(ui);
        ui.setVisible(true);
        //ui.controller.loadSource(new File("E:\\Frage\\frage.gapp.txt"));
        
        //ui.controller.setVariableValue("a1=3;a2=4;a3=5;b1=6;b2=7;b3=8");
        //ui.controller.repaint();
 */
 
    }


}
