package de.gaalop.algebra;

import de.gaalop.AlgebraStrategy;
import de.gaalop.CodeParserException;
import de.gaalop.InputFile;
import de.gaalop.OptimizationException;
import de.gaalop.cfg.AlgebraDefinitionFile;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.Macro;
import de.gaalop.dfg.Expression;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Sets the algebra on a Control Flow Graph
 * @author Christian Steinmetz
 */
public class AlStrategy implements AlgebraStrategy {

    private Plugin plugin;

    public AlStrategy(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void transform(ControlFlowGraph graph) throws OptimizationException {
        InputStream inputStream = null;
        try {
            //load algebra
            AlgebraDefinitionFile alFile = graph.getAlgebraDefinitionFile();
            alFile.setUsePrecalculatedTable(plugin.usePrecalulatedTables);
            alFile.setUseAsRessource(plugin.useBuiltInFiles);
            alFile.setProductsFilePath(plugin.productsFilePath);

            inputStream = (plugin.isUseBuiltInFiles())
                    ? getClass().getResourceAsStream(plugin.definitionFilePath)
                    : new FileInputStream(new File(plugin.definitionFilePath));
            alFile.loadFromFile(inputStream);
            
            createBlades(alFile);

            //replace all functions / macros

            inputStream = (plugin.isUseBuiltInFiles())
                    ? getClass().getResourceAsStream(plugin.macrosFilePath)
                    : new FileInputStream(new File(plugin.macrosFilePath));
            
            ControlFlowGraph macrosGraph = new de.gaalop.clucalc.input.Plugin().createCodeParser().parseFile(inputStreamToInputFile(inputStream, "macros"));
            inputStream.close();
            HashMap<StringIntContainer, Macro> macros = MacrosVisitor.getAllMacros(macrosGraph);
            MacrosVisitor.getAllMacros(graph, macros);
            StringIntContainer dual = new StringIntContainer("Dual",1);
            if (macros.containsKey(dual)) {
                macros.put(new StringIntContainer("*",1), macros.get(dual));
                macros.remove(dual);
            }

            //load user macros
            if (!plugin.getUserMacroFilePath().trim().equals("")) {
                File f = new File(plugin.userMacroFilePath);
                if (f.exists()) {
                     inputStream = new FileInputStream(f);
                     ControlFlowGraph userMacrosGraph = new de.gaalop.clucalc.input.Plugin().createCodeParser().parseFile(inputStreamToInputFile(inputStream, "userMacros"));
                     inputStream.close();
                     MacrosVisitor.getAllMacros(userMacrosGraph, macros);
                } else
                    System.err.println("Algebra Plugin: User Macro File Path does not exist!");
            }

            //inline all macros
            Inliner.inline(graph, macros);

            //replace Variables which are basevectors
            BaseVectorDefiner definer = new BaseVectorDefiner();
            definer.createFromAlBase(alFile.base);
            BaseVectorReplaceVisitor replacerB = new BaseVectorReplaceVisitor(definer);
            graph.accept(replacerB);
            //Update variable set
            UpdateLocalVariableSet.updateVariableSets(graph);
            RemoveDefVars.removeDefVars(graph);
        } catch (CodeParserException ex) {
            Logger.getLogger(AlStrategy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AlStrategy.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(AlStrategy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Create blades in a AlgebraDefinitionFile
     * @param alFile The AlgebraDefinitionFile
     */
    public static void createBlades(AlgebraDefinitionFile alFile) {
        TCBlade[] blades = BladeArrayRoutines.createBlades(Arrays.copyOfRange(alFile.base,1,alFile.base.length));
        alFile.blades = new Expression[blades.length];
        for (int i = 0; i < blades.length; i++) 
            alFile.blades[i] = blades[i].toExpression();
    }

    /**
     * Puts an inputStream into an InputFile
     * @param inputStream The inputStream
     * @param cluName The cluName to use
     * @return The InputFile
     */
    private InputFile inputStreamToInputFile(InputStream inputStream, String cluName) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(AlStrategy.class.getName()).log(Level.SEVERE, null, ex);
        }
        sb.append("\n");
        sb.append("?a=1;");
        return new InputFile(cluName, sb.toString());
    }

}
