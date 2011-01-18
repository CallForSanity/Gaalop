package de.gaalop.gaalet;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.gaalop.CodeParser;
import de.gaalop.CodeParserException;
import de.gaalop.InputFile;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.gaalet.antlr.GaaletLexer;
import de.gaalop.gaalet.antlr.GaaletParser;
import de.gaalop.gaalet.antlr.GaaletTransformer;



/*
 * This class is taken form the the ClucalcCodeParser source. 
 * It was modified to fit the GaaletLexer, Parser and Transformer.
 * 
 */
public enum GaaletCodeParser implements CodeParser{

	INSTANCE;
	
	private static final Log log = LogFactory.getLog(GaaletCodeParser.class);

	
	
	private Plugin plugin;
	
	public void setPluginReference(Plugin plugin) {
		
		if (this.plugin == null)
			this.plugin = plugin;
		
		
	}
	
	
	@Override
	public ControlFlowGraph parseFile(InputFile input)	throws CodeParserException {
	
		log.debug("Processing" + input.getName() + ", Content: " + input.getContent());
		
		
        ControlFlowGraph graph;

        try {
            graph = parse(input);
        } catch (Throwable e) {
            throw new CodeParserException(input, "Unable to parse Gaalet file.\n" + e.getMessage(), e);
        }

       graph.setSource(input);
       return graph;
	}

	 private ControlFlowGraph parse(InputFile input) throws CodeParserException, RecognitionException {
        ANTLRStringStream inputStream = new ANTLRStringStream(input.getContent());
        GaaletLexer lexer = new GaaletLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        GaaletParser parser = new GaaletParser(tokenStream);       
        GaaletParser.script_return parserResult = parser.script();

        if (!parser.getErrors().isEmpty()) {
            StringBuilder message = new StringBuilder();
            message.append("Unable to parse Gaalet file:\n");
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
        GaaletTransformer transformer = new GaaletTransformer(treeNodeStream);
        ControlFlowGraph graph = transformer.script();
//        if (plugin != null) {
//        	plugin.setNumberOfAssignments(transformer.getNumberOfAssignments());
//        }

        if (!parser.getErrors().isEmpty()) {
            StringBuilder message = new StringBuilder();
            message.append("Unable to parse Gaalet file:\n");
            for (String error : parser.getErrors()) {
                message.append(error);
                message.append('\n');
            }
            throw new CodeParserException(input,  message.toString());
        }

        return graph;

    }

    
}
