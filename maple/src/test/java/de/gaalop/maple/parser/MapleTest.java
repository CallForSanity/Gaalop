package de.gaalop.maple.parser;

import org.junit.Test;

public class MapleTest {
     /*
    public static final File MAPLE_PATH = new File("C:/Programs/Maple 9.5/");

    private ControlFlowGraph getTestGraph() throws RecognitionException {
        String text = "DefVarsN3()\nS = e0-0.5*r*r*einf;\n" +
                "P = VecN3(x,y,z);\n" +
                "?C = S^(P+(P.S)*einf);";

        CluCalcLexer lexer = new CluCalcLexer(new ANTLRStringStream(text));
        CluCalcParser parser = new CluCalcParser(new CommonTokenStream(lexer));
        CluCalcParser.script_return result = parser.script();
        CluCalcTransformer transformer = new CluCalcTransformer(new CommonTreeNodeStream(result.getTree()));
        ControlFlowGraph cfg = transformer.script();

        return cfg;
    }                    */

    @Test
    public void testEvaluate() throws Exception {
        /*Maple.initialize(new File(MAPLE_PATH, "java"), new File(MAPLE_PATH, "bin.win"));

        ControlFlowGraph graph = getTestGraph();

        System.out.println("Local Variables: " + graph.getLocalVariables());
        System.out.println("Input Variables: " + graph.getInputVariables());
        System.out.println("Output Variables: " + graph.getOutputVariables());

        FileOutputStream out = new FileOutputStream("C:/graph1.txt");
        PrintWriter writer = new PrintWriter(out);
        writer.write(CfgDotExporter.toDotString(graph));
        writer.close();

        MapleSimplifier simplifier = new MapleSimplifier();
        simplifier.simplify(graph);

        out = new FileOutputStream("C:/graph2.txt");
        writer = new PrintWriter(out);
        writer.write(CfgDotExporter.toDotString(graph));
        writer.close();*/

        /*MapleEngine engine = Maple.getEngine();

        engine.evaluate("restart:");
        engine.evaluate("with(Clifford);");
        engine.evaluate("output_suffix := \"\";");

        loadModule(engine, "gaalop.m");
        loadModule(engine, "gaalopfunctions.m");

        engine.evaluate("e0:=-1/2*e4 + 1/2*e5;");
        engine.evaluate("einf:=e4+e5;");
        engine.evaluate("B:=linalg[diag](1,1,1,1,-1);");
        engine.evaluate("eval(makealiases(5,\"ordered\"));");

        engine.evaluate("gaalopinitialize();");

        // Algorithm input starts here
        engine.evaluate(":OPNS;");
        engine.evaluate("CA := (((subs(Id=1, c1 &c  e1) + subs(Id=1, c2 &c  e2)) + subs(Id=1, c3 &c  e3)) + subs(Id=1, f &c  einf));");
        engine.evaluate("DB := (((subs(Id=1, d1 &c  e1) + subs(Id=1, d2 &c  e2)) + subs(Id=1, d3 &c  e3)) + subs(Id=1, g &c  einf));");
        engine.evaluate("G := CA &w  DB;");
        String result = engine.evaluate("gaalop(G);");
        System.out.println(result);*/
    }


}
