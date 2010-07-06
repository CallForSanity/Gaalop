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
    
    private Plugin plugin;

    public MapleSimplifier(Plugin plugin) {
        engine = Maple.getEngine();
        this.plugin = plugin;
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
        loadModule("gaalopfunctions.m");

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

        MapleCfgVisitor visitor = new MapleCfgVisitor(engine, plugin);
        graph.accept(visitor);
    }
    
    void notifyMaximum(int max) {
    	plugin.notifyMaximum(max);
    }

}
