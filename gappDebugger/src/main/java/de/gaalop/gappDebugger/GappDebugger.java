package de.gaalop.gappDebugger;

import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JFrame;

/**
 *
 * @author Christian Steinmetz
 */
public class GappDebugger implements CodeGenerator {

    @Override
    public Set<OutputFile> generate(ControlFlowGraph in) throws CodeGeneratorException {
        UI ui = new UI();
        ui.controller = new Controller(ui);
        ui.setVisible(true);
        ui.controller.loadSource(in);
        ui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        String[] base = in.getAlgebraDefinitionFile().base;
        ui.controller.setAlgebraBlades(Arrays.copyOfRange(base, 1, base.length));
        ui.controller.restart();
        
        return new HashSet<OutputFile>();
    }

}
