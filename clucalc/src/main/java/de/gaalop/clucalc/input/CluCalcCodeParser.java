package de.gaalop.clucalc.input;

import de.gaalop.CodeParser;
import de.gaalop.CodeParserException;
import de.gaalop.InputFile;
import de.gaalop.cfg.ControlFlowGraph;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.LinkedList;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class wraps the ANTLR parsers inside the Gaalop CodeGenerator interface.
 * It is implemented as a singleton because it does not have any state.
 */
public enum CluCalcCodeParser implements CodeParser {

    INSTANCE;

    // static in order not to get an "illegal reference to static field from initializer" error
    private static final Log log = LogFactory.getLog(CluCalcCodeParser.class);
    
    private Plugin plugin;
    
    public void setPluginReference(Plugin plugin) {
    	if (this.plugin == null) {
    		this.plugin = plugin;
    	}
    }

    @Override
    public ControlFlowGraph parseFile(InputFile input) throws CodeParserException {
        log.debug("Processing " + input.getName() + ", Content: \n" + input.getContent());

        ControlFlowGraph graph;

        try {
            graph = parse(input);
        } catch (Throwable e) {
            throw new CodeParserException(input, e.getMessage(), e);
        }

        graph.setSource(input);
        return graph;
    }

    private ControlFlowGraph parse(InputFile input) throws CodeParserException, RecognitionException, IOException {
        LinkedList<String> onlyEvaluates = new LinkedList<String>();
        LinkedList<String> outputs = new LinkedList<String>();


        String content = input.getContent();
        BufferedReader reader = new BufferedReader(new StringReader(content));
        String line = null;
        StringBuilder builder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            if (line.trim().startsWith("//#pragma")) {
                String line2 = line.replaceAll("\t", " ");
                while (line2.contains("  "))
                    line2 = line2.replaceAll("  "," ");

                String whichPragma = line2.substring(10,line2.indexOf(" ",10));
                if (whichPragma.equals("range") || whichPragma.equals("unroll") || whichPragma.equals("count")) {
                    builder.append(line);
                    builder.append("\n");
                } else {
                    //process here
                    String rest = line2.substring(line2.indexOf(" ",10)+1);
                    if (whichPragma.equals("output")) {
                        //IDENTIFIER blade+
                        String[] parts = rest.split(" ");
                        for (int i=1;i<parts.length;i++)
                            outputs.add(parts[0]+" "+parts[i]);
                        
                    } else {
                        if (whichPragma.equals("onlyEvaluate")) {
                            //IDENTIFIER+
                            String[] parts = rest.split(" ");
                            onlyEvaluates.addAll(Arrays.asList(parts));
                        } else 
                            throw new CodeParserException(input, "pragma "+whichPragma+" is unknown.");
                    }


                }

            } else {
                builder.append(line);
                builder.append("\n");
            }
        }


        ANTLRStringStream inputStream = new ANTLRStringStream(builder.toString());
        CluCalcLexer lexer = new CluCalcLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        CluCalcParser parser = new CluCalcParser(tokenStream);
        CluCalcParser.script_return parserResult = parser.script();

        if (!parser.getErrors().isEmpty()) {
            StringBuilder message = new StringBuilder();
            message.append("Unable to parse CluCalc file:\n");
            for (String error : parser.getErrors()) {
                message.append(error);
                message.append('\n');
            }
            throw new CodeParserException(input,  message.toString());
        }

        if (parserResult.getTree() == null) {
            throw new CodeParserException(input, "The input file is empty.");
        }

        CommonTreeNodeStream treeNodeStream = new CommonTreeNodeStream(parserResult.getTree());
        CluCalcTransformer transformer = new CluCalcTransformer(treeNodeStream);
        ControlFlowGraph graph = transformer.script();

        if (!parser.getErrors().isEmpty()) {
            StringBuilder message = new StringBuilder();
            message.append("Unable to parse CluCalc file:\n");
            for (String error : parser.getErrors()) {
                message.append(error);
                message.append('\n');
            }
            throw new CodeParserException(input,  message.toString());
        }

        for (String output: outputs)
            graph.getPragmaOutputVariables().add(output);

        for (String onlyEval: onlyEvaluates)
            graph.getPragmaOnlyEvaluateVariables().add(onlyEval);

        return graph;
    }

}
