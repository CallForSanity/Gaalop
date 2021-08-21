package de.gaalop.matlab;

import de.gaalop.DefaultCodeGenerator;
import de.gaalop.cfg.ControlFlowGraph;

public class MatlabCodeGenerator extends DefaultCodeGenerator {

    private final Plugin plugin;

    MatlabCodeGenerator(Plugin plugin) {
        super("m");
        this.plugin = plugin;
    }

    @Override
    protected String generateCode(ControlFlowGraph in) {
        MatlabVisitor visitor = new MatlabVisitor(plugin.getUseDouble(), generateFilename(in).split("\\.")[0]);
        try {
            in.accept(visitor);
        } catch (Throwable error) {
            plugin.notifyError(error);
        }
        return visitor.getCode();
    }

}
