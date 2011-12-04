package de.gaalop.algebra;

import de.gaalop.AlgebraStrategy;
import de.gaalop.OptimizationException;
import de.gaalop.cfg.AlgebraDefinitionFile;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.Expression;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
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


            LinkedList<Function> functions = (inputStream != null) 
                    ? FunctionParser.parseFunctions(inputStream)
                    : new LinkedList<Function>();


            //load user macros
            if (!plugin.getUserMacroFilePath().trim().equals("")) {
                File f = new File(plugin.userMacroFilePath);
                if (f.exists()) {
                     inputStream = new FileInputStream(f);
                     functions.addAll(FunctionParser.parseFunctions(inputStream));
                } else
                    System.err.println("Algebra Plugin: User Macro File Path does not exist!");
            }

            FunctionWrapper wrapper = new FunctionWrapper(functions);
            FunctionReplaceVisitor replacerF = new FunctionReplaceVisitor(wrapper);
            graph.accept(replacerF);
            //replace Variables which are basevectors
            BaseVectorDefiner definer = new BaseVectorDefiner();
            definer.createFromAlBase(alFile.base);
            BaseVectorReplaceVisitor replacerB = new BaseVectorReplaceVisitor(definer);
            graph.accept(replacerB);
            //Update variable set
            UpdateLocalVariableSet.updateVariableSets(graph);
            RemoveDefVars.removeDefVars(graph);
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

}
