package de.gaalop.csharp;

import de.gaalop.DefaultCodeGenerator;
import de.gaalop.cfg.ControlFlowGraph;

/**
 * This class facilitates C/C++ code generation.
 */
public class CsharpCodeGenerator extends DefaultCodeGenerator {

    private final Plugin plugin;

    public CsharpCodeGenerator(Plugin plugin) {
        super("cs");
        this.plugin = plugin;
    }

    @Override
    protected String generateCode(ControlFlowGraph graph) {
        CsharpVisitor visitor = new CsharpVisitor(plugin.getStandalone(), plugin.getUseDouble());
        try {
            graph.accept(visitor);
        } catch (Throwable error) {
            plugin.notifyError(error);
        }
        return visitor.getCode();
    }
}
