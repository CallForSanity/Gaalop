package de.gaalop.clucalc.input;

import de.gaalop.CodeParser;
import de.gaalop.CodeParserException;
import de.gaalop.InputFile;
import de.gaalop.cfg.ControlFlowGraph;
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
        log.debug("Processing " + input.getName() + ", Content: " + input.getContent());

        ControlFlowGraph graph;

        try {
            graph = parse(input);
        } catch (Throwable e) {
            throw new CodeParserException(input, e.getMessage(), e);
        }

        graph.setSource(input);
        return graph;
    }

    private ControlFlowGraph parse(InputFile input) throws CodeParserException, RecognitionException {
        ANTLRStringStream inputStream = new ANTLRStringStream(input.getContent());
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

        return graph;
    }

}
