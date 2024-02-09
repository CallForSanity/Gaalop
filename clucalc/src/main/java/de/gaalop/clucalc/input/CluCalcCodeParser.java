package de.gaalop.clucalc.input;

import de.gaalop.CodeParser;
import de.gaalop.CodeParserException;
import de.gaalop.InputFile;
import de.gaalop.cfg.ReturnDefinition;
import de.gaalop.cfg.ControlFlowGraph;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.LinkedList;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class wraps the ANTLR parsers inside the Gaalop CodeGenerator interface.
 * It is implemented as a singleton because it does not have any state.
 */
public enum CluCalcCodeParser implements CodeParser {

    INSTANCE;

    final String DEFAULT_PRAGMA_START = "//#pragma ";
    final String SHORT_PRAGMA_START = "#pragma ";

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

    private ControlFlowGraph parse(InputFile input) throws CodeParserException, IOException {
        LinkedList<String> onlyEvaluates = new LinkedList<String>();
        LinkedList<String> outputs = new LinkedList<String>();

        LinkedList<String[]> drawSegments = new LinkedList<String[]>();
        LinkedList<String[]> drawTriangles = new LinkedList<String[]>();

        String syntax = null;
        LinkedList<String> ranges = new LinkedList<String>();

        LinkedList<ReturnDefinition> returnDefinitions = new LinkedList<ReturnDefinition>();
        LinkedList<String> insertionTexts = new LinkedList<String>();

        String content = input.getContent();
        BufferedReader reader = new BufferedReader(new StringReader(content));
        String line = null;
        StringBuilder builder = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            String trimmedLine = line.trim();
            // Allow using #pragma instead of //#pragma
            Boolean usingPragma = trimmedLine.startsWith(DEFAULT_PRAGMA_START);
            Boolean usingNewPragma = trimmedLine.startsWith(SHORT_PRAGMA_START);
            if (usingPragma || usingNewPragma) {
                int lengthOfPragmaStart = usingNewPragma ? SHORT_PRAGMA_START.length() : DEFAULT_PRAGMA_START.length();

                String line2 = line.replaceAll("\t", " ");
                while (line2.contains("  "))
                    line2 = line2.replaceAll("  "," ");

                int indexOfSpace = line2.indexOf(" ", 10);

                String whichPragma = line2.substring(lengthOfPragmaStart, indexOfSpace);
                if (whichPragma.equals("unroll") || whichPragma.equals("count")) {
                    builder.append(line);
                    builder.append("\n");
                } else {
                    //process here
                    String rest = line2.substring(indexOfSpace + 1);

                    switch (whichPragma) {
                        case "output":
                            //IDENTIFIER blade+
                            String[] partsOutput = rest.split(" ");
                            for (int i=1;i<partsOutput.length;i++)
                                outputs.add(partsOutput[0]+" "+partsOutput[i]);
                            break;
                        case "onlyEvaluate":
                            //IDENTIFIER+
                            String[] partsOnlyEvaluate = rest.split(" ");
                            onlyEvaluates.addAll(Arrays.asList(partsOnlyEvaluate));
                            break;
                        case "in2out":
                            syntax = rest;
                            break;
                        case "segments":
                            while (rest.contains("  "))
                                rest = rest.replaceAll("\\W\\W", " ");

                            for (String t: rest.split(",")) {
                                String[] segmentMvs = t.trim().split("\\W");
                                if (segmentMvs.length == 2) {
                                    drawSegments.add(segmentMvs);
                                } else {
                                    throw new CodeParserException(input, "pragma segments must be in format: A1 B1, A2 B2, ... , An Bn.");
                                }
                            }
                            break;
                        case "triangles":
                            while (rest.contains("  "))
                                rest = rest.replaceAll("\\W\\W", " ");

                            for (String t: rest.split(",")) {
                                String[] triangleMvs = t.trim().split("\\W");
                                if (triangleMvs.length == 3) {
                                    drawTriangles.add(triangleMvs);
                                } else {
                                    throw new CodeParserException(input, "pragma triangles must be in format: A1 B1 C1, A2 B2 C2, ... , An Bn Cn.");
                                }
                            }
                            break;
                        case "range":
                            ranges.add(rest);
                            break;

                        case "insert":
                            insertionTexts.add(rest);
                            break;

                        case "return":
                            // Returns the values as object of given type
                            // Example: #pragma return p5,p6 typed Vector2 as Vector2(-<e02> / <e12>, <e01> / <e12>)

                            String[] parts = rest.split("\\s+typed\\s+|\\s+as\\s+");

                            if (parts.length == 3) {
                                String variablesText = parts[0];
                                String returnType = parts[1];
                                String returnText = parts[2];
                                String[] variables = Arrays.stream(variablesText.split(",")).map(String::trim).toArray(String[]::new);
                                ReturnDefinition returnDefintion = new ReturnDefinition(variables, returnType, returnText);
                                returnDefinitions.add(returnDefintion);
 }

                            break;
                        default:
                            throw new CodeParserException(input, "pragma "+whichPragma+" is unknown.");
                    }
                }

            } else {
                builder.append(line);
                builder.append("\n");
            }
        }

        String program = builder.toString();
        ANTLRInputStream inputStream = new ANTLRInputStream(new StringReader(program));
        CluCalcLexer lexer = new CluCalcLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        CluCalcParser parser = new CluCalcParser(tokenStream);

        CluVisitor visitor = new CluVisitor();
        visitor.visit(parser.script());
        ControlFlowGraph graph = visitor.graph;

        for (ReturnDefinition definition : returnDefinitions) {
            graph.addReturnDefinition(definition);
        }

        for (String text : insertionTexts) {
            graph.addInsertionText(text);
        }

        graph.drawSegments = drawSegments;
        graph.drawTriangles = drawTriangles;
        if (syntax != null) {
            //Parse syntax
            String[] syntaxParts = syntax.split("->");
            if (syntaxParts.length == 2) {
                // Parse inputs
                for (String var: syntaxParts[0].split(","))
                    graph.syntaxInputs.add(var.trim());
                // Parse outputs
                for (String var: syntaxParts[1].split(","))
                    graph.syntaxOutputs.add(var.trim());
            } else {
                throw new CodeParserException(input, "#pragma in2out must contain one arrow ->");
            }
            graph.syntaxSpecified = true;
        } else
            graph.syntaxSpecified = false;

        for (String output: outputs)
            graph.getPragmaOutputVariables().add(output);

        for (String onlyEval: onlyEvaluates)
            graph.addPragmaOnlyEvaluateVariable(onlyEval);

        for (String range: ranges) {
            String[] rangeParts = range.split("<=");
            if (rangeParts.length == 3)
                graph.addPragmaMinMaxValues(rangeParts[1].trim(), rangeParts[0].trim(), rangeParts[2].trim());
            else
                throw new CodeParserException(input, "#pragma range must be of the form: {minValue} <= {variable_name} <= {maxValue} but is "+range);
        }

        return graph;
    }

}
