package de.gaalop.tablebased;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.gaalop.cfg.ControlFlowGraph;

/**
 * Created by IntelliJ IDEA.
 * User: Sebastian
 * Date: 08.02.2009
 * Time: 13:08:19
 * To change this template use File | Settings | File Templates.
 */
public class TableBasedSimplifier {

    private Log log = LogFactory.getLog(TableBasedSimplifier.class);
 
    private Plugin plugin;

    public TableBasedSimplifier(Plugin plugin) {
        this.plugin = plugin;
    }

    public void simplify(ControlFlowGraph graph) {
        TableBasedCfgVisitor visitor = new TableBasedCfgVisitor(plugin);
        graph.accept(visitor);
    }
    
    void notifyMaximum(int max) {
    	plugin.notifyMaximum(max);
    }

}
