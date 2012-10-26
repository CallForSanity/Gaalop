package de.gaalop.tba;

import de.gaalop.OptimizationException;
import de.gaalop.OptimizationStrategy;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.tba.cfgImport.CFGImporterFacade;

/**
 * Defines a facade class for the table based approach
 * @author Christian Steinmetz
 */
public class TBAOptStrategy implements OptimizationStrategy {

    private Plugin plugin;

    public TBAOptStrategy(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void transform(ControlFlowGraph graph) throws OptimizationException {
        CFGImporterFacade importer = new CFGImporterFacade(plugin);
        importer.importGraph(graph);
    }
}

/**
 * TODO chs find solution: Example in 9d with/without maxima results in two different outputs:
 * 
G = createEllipsoid(2,3,3,2,2,4);
:Blue;
:G;

H = createCylinder(3,4,0,3);
:Green;
:H;

S = (G^H); //String als Bivektor
:Red;
:S;

 */