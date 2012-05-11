package de.gaalop.clucalc;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.clucalc.input.CluCalcLexer;
import de.gaalop.clucalc.input.CluCalcParser;
import de.gaalop.clucalc.input.CluCalcTransformer;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.junit.Test;

public class CluCalcFrontendTest {
    @Test
    public void testSimple() throws RecognitionException {
        String text = "DefVarsN3();\n" +
                "S = e0-0.5*r*r*einf;\n" +
                "P = VecN3(x,y,z);\n" +
                "?C = S^(P+(P.S)*einf);";

        CluCalcLexer lexer = new CluCalcLexer(new ANTLRStringStream(text));
        CluCalcParser parser = new CluCalcParser(new CommonTokenStream(lexer));
        CluCalcParser.script_return result = parser.script();
        CluCalcTransformer transformer = new CluCalcTransformer(new CommonTreeNodeStream(result.getTree()));
        ControlFlowGraph cfg = transformer.script();

        // TODO: Insert test here that the cfg is really the algorithm above
    }
}
