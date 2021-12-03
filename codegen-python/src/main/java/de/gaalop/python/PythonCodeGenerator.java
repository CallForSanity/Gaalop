package de.gaalop.python;

import de.gaalop.DefaultCodeGenerator;
import de.gaalop.cfg.ControlFlowGraph;

/**
 * This class facilitates Python code generation.
 */
public class PythonCodeGenerator extends DefaultCodeGenerator {

    private final Plugin plugin;

    PythonCodeGenerator(Plugin plugin) {
        super("py");
        this.plugin = plugin;
    }

    @Override
    protected String generateCode(ControlFlowGraph in) {
        PythonVisitor visitor = new PythonVisitor(plugin.getUseDouble(), generateFilename(in).split("\\.")[0]);
        try {
            in.accept(visitor);
        } catch (Throwable error) {
            plugin.notifyError(error);
        }
        return visitor.getCode();
    }

}
