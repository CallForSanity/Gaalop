package de.gaalop.cpp;

import de.gaalop.DefaultCodeGenerator;
import de.gaalop.cfg.ControlFlowGraph;

/**
 * This class facilitates C/C++ code generation.
 */
public class CppCodeGenerator extends DefaultCodeGenerator {

    private final Plugin plugin;

    public CppCodeGenerator(Plugin plugin) {
        super("c");
        this.plugin = plugin;
    }

    @Override
    protected String generateCode(ControlFlowGraph graph) {
        CppVisitor visitor = new CppVisitor(plugin.getStandalone(), plugin.getUseDouble());
        try {
            graph.accept(visitor);
        } catch (Throwable error) {
            plugin.notifyError(error);
        }
        return visitor.getCode();
    }
}
