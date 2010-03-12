package de.gaalop.clucalc;

import org.junit.Test;

public class CodegenTest {

    @Test
    public void test() {

    }                                                  /*

    public static final File MAPLE_PATH = new File("C:/Programs/Maple 9.5/");

    private ControlFlowGraph parseCluCalc(String text) throws RecognitionException {
        CluCalcLexer lexer = new CluCalcLexer(new ANTLRStringStream(text));
        CluCalcParser parser = new CluCalcParser(new CommonTokenStream(lexer));
        CluCalcParser.script_return result = parser.script();
        CluCalcTransformer transformer = new CluCalcTransformer(new CommonTreeNodeStream(result.getTree()));
        ControlFlowGraph cfg = transformer.script();
        return cfg;
    }

    @BeforeClass
    public static void initMaple() throws Exception {
        Maple.initialize(new File(MAPLE_PATH, "java"), new File(MAPLE_PATH, "bin.win"));
    }

    @Test
    public void testEvaluate() throws Exception {
        String text = "DefVarsN3()\nS = e0-0.5*r*r*einf;\n" +
                "P = VecN3(x,y,z);\n" +
                "?C = S^(P+(P.S)*einf);";

        generateCode(text);
    }

    private void generateCode(String text) throws RecognitionException {
        ControlFlowGraph graph = parseCluCalc(text);

        MapleSimplifier simplifier = new MapleSimplifier();
        simplifier.simplify(graph);

        GaTransformer transformer = new GaTransformer();
        transformer.transformBlades(simplifier, graph);
                      
        CluCalcCodeGenerator gen = new CluCalcCodeGenerator();
        Set<OutputFile> files = gen.generate(graph);

        for (OutputFile file : files) {
            System.out.println("file = " + file);
        }
    }

    @Test
    public void testTwoSpheres() throws Exception {
        String text = "DefVarsN3();\n" +
                        "CA = VecN3(c1, c2, c3) - f * einf;\n" +
                        "DB = VecN3(d1, d2, d3) - g * einf;\n" +
                        "?K=CA^DB;";

        generateCode(text);
    }
*/
}
