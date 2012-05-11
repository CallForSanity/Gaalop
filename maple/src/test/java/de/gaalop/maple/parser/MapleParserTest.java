package de.gaalop.maple.parser;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.junit.Test;


public class MapleParserTest {
    @Test
    public void testEmpty() throws RecognitionException {
        ANTLRStringStream in = new ANTLRStringStream("");
        MapleLexer lexer = new MapleLexer(in);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MapleParser parser = new MapleParser(tokens);
        parser.program();
    }
}
