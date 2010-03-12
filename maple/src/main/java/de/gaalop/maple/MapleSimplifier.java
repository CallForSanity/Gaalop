package de.gaalop.maple;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.maple.engine.Maple;
import de.gaalop.maple.engine.MapleEngine;
import de.gaalop.maple.engine.MapleEngineException;

/**
 * Created by IntelliJ IDEA.
 * User: Sebastian
 * Date: 08.02.2009
 * Time: 13:08:19
 * To change this template use File | Settings | File Templates.
 */
public class MapleSimplifier {

    private Log log = LogFactory.getLog(MapleSimplifier.class);

    private MapleEngine engine;

    public MapleSimplifier() {
        engine = Maple.getEngine();
    }

    private void loadModule(String resourceName) throws MapleEngineException {
        InputStream module = MapleSimplifier.class.getResourceAsStream(resourceName);

        if (module == null) {
            throw new IllegalArgumentException("Unable to find resource: " + resourceName);
        }

        try {
            engine.loadModule(module);
        } finally {
            try {
                module.close();
            } catch (IOException e) {
                log.warn("Unable to close input stream.", e);
            }
        }
    }

    private void initEngine() throws MapleEngineException {
        engine.reset();

        // Activate Clifford Library
        engine.evaluate("with(Clifford);");

        // Load our Maple modules
        loadModule("gaalop.m");

        // Misc initialization
        engine.evaluate("B:=linalg[diag](1,1,1,1,-1);");
        engine.evaluate("eval(makealiases(5,\"ordered\"));");
        
        // Add e0 and einf to Maple
        engine.evaluate("e0:=-1/2*e4 + 1/2*e5;");
        engine.evaluate("einf:=e4+e5;");

        // Finish the maple-gaalop initialization
        engine.evaluate("gaalopinitialize();");
    }

    public void simplify(ControlFlowGraph graph) {
        try {
            initEngine();
        } catch (MapleEngineException e) {
            log.error("Unable to simplify using Maple.", e);
            throw new RuntimeException("Unable to simplify graph using Maple.", e);
        }

        MapleCfgVisitor visitor = new MapleCfgVisitor(engine);
        graph.accept(visitor);
    }

//    public Expression simplify(Expression expression) {
//        log.debug("Simplifying expression " + expression);
//
//        try {
//            initEngine();
//        } catch (MapleEngineException e) {
//            log.error("Unable to simplify using Maple.", e);
//            throw new RuntimeException("Unable to simplify expression using Maple.", e);
//        }
//
//        MapleDfgVisitor visitor = new MapleDfgVisitor();
//        visitor.setCliffordMode(false);
//        expression.accept(visitor);
//        try {
//            log.debug("Generated Maple code for Expression: " + visitor.getCode());
//            engine.evaluate("EXPR := " + visitor.getCode() + ";");
//            String mapleCode = engine.evaluate("simplify(EXPR);");
//            log.debug("Simplified by Maple to: " + mapleCode);
//            MapleLexer lexer = new MapleLexer(new ANTLRStringStream(mapleCode));
//            MapleParser parser = new MapleParser(new CommonTokenStream(lexer));
//            try {
//                MapleParser.additive_expression_return result = parser.additive_expression();
//                MapleTransformer transformer = new MapleTransformer(new CommonTreeNodeStream(result.getTree()));
//                return transformer.expression();
//            } catch (RecognitionException e) {
//                throw new RuntimeException(e);
//            }
//        } catch (MapleEngineException e) {
//            log.error("Unable to simplify expression " + expression + ".", e);
//            throw new RuntimeException("Unable to simplify expression " + expression + ".", e);
//        }
//    }

}
