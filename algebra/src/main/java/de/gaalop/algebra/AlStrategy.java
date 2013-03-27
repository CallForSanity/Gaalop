package de.gaalop.algebra;

import de.gaalop.AlgebraStrategy;
import de.gaalop.CodeParserException;
import de.gaalop.InputFile;
import de.gaalop.OptimizationException;
import de.gaalop.cfg.AlgebraDefinitionFile;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.FindStoreOutputNodes;
import de.gaalop.cfg.Macro;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.OuterProduct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
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
        //Check if graph contains at least one StoreResultNode
        FindStoreOutputNodes outputNodes = new FindStoreOutputNodes();
        graph.accept(outputNodes);
        if (outputNodes.getNodes().isEmpty()) {
            throw new RuntimeException("There are no lines marked for optimization ('?')");
        }

        InputStream inputStream = null;
        try {
            //load algebra
            AlgebraDefinitionFile alFile = graph.getAlgebraDefinitionFile();
            alFile.setUsePrecalculatedTable(plugin.usePrecalulatedTables);
            alFile.setUseAsRessource(graph.asRessource);
            
            String baseDir = (graph.asRessource) ? "algebra" : graph.algebraBaseDirectory;
            
            if (!baseDir.endsWith("/")) baseDir += "/";
            
            baseDir += graph.algebraName+"/";
            
            alFile.setProductsFilePath(baseDir+"products.csv");

            inputStream = (graph.asRessource)
                    ? getClass().getResourceAsStream(baseDir+"definition.csv")
                    : new FileInputStream(new File(baseDir+"definition.csv"));
            alFile.loadFromFile(inputStream);
            
            createBlades(alFile);

            //replace all functions / macros

            inputStream = (graph.asRessource)
                    ? getClass().getResourceAsStream(baseDir+"macros.clu")
                    : new FileInputStream(new File(baseDir+"macros.clu"));
            
            ControlFlowGraph macrosGraph = new de.gaalop.clucalc.input.Plugin().createCodeParser().parseFile(inputStreamToInputFile(inputStream, "macros", null));
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
                     ControlFlowGraph userMacrosGraph = new de.gaalop.clucalc.input.Plugin().createCodeParser().parseFile(inputStreamToInputFile(inputStream, "userMacros", f.getParentFile()));
                     inputStream.close();
                     MacrosVisitor.getAllMacros(userMacrosGraph, macros);
                } else
                    System.err.println("Algebra Plugin: User Macro File Path does not exist!");
            }

            //inline all macros
            Inliner.inline(graph, macros);
            
            //Remove Macro definitions from graph
            for (Macro macro: macros.values()) 
                graph.removeNode(macro);
            

            //replace Variables which are basevectors
            BaseVectorDefiner definer = new BaseVectorDefiner();
            definer.createFromAlBase(alFile.base);
            BaseVectorReplaceVisitor replacerB = new BaseVectorReplaceVisitor(definer);
            graph.accept(replacerB);
            //Update variable set
            UpdateLocalVariableSet.updateVariableSets(graph);
            //RemoveDefVars.removeDefVars(graph);

            //update output blades
            HashMap<String, Integer> mapIndices = new HashMap<String, Integer>();
            for (int index = 0;index<alFile.blades.length;index++) 
                mapIndices.put(bladeToString(alFile.blades[index]), new Integer(index));

            Set<String> set = graph.getPragmaOutputVariables();
            HashSet<String> copySet = new HashSet<String>(set);
            set.clear();
            for (String str: copySet) {
                if (str.contains(" ")) {
                    String[] parts = str.split(" ");
                    if (parts[1].equals("1")) parts[1] = "1.0";
                    if (!mapIndices.containsKey(parts[1]))
                        throw new OptimizationException("The bladename "+parts[1]+" is not found in the default blade list.", graph);

                    set.add(parts[0]+"$"+mapIndices.get(parts[1]));
                } else 
                    set.add(str);
            }
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

    private String bladeToString(Expression blade) {
        if (!blade.isComposite()) return blade.toString();
        OuterProduct outerProduct = (OuterProduct) blade;
        return bladeToString(outerProduct.getLeft())+"^"+bladeToString(outerProduct.getRight());
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
     * @param parent The parent file object
     * @return The InputFile
     */
    private InputFile inputStreamToInputFile(InputStream inputStream, String cluName, File parent) {
        StringBuilder sb = new StringBuilder();
        readIn(inputStream, sb, parent);
        sb.append("\n");
        return new InputFile(cluName, sb.toString());
    }
    
    /**
     * Reads an inputStream in a stringbuilder object
     * @param inputStream The inputStream
     * @param sb The stringbuilder object to use
     * @param parent The parent file object
     */
    private void readIn(InputStream inputStream, StringBuilder sb, File parent) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("#include")) {
                    line = line.trim();
                    String filename = line.substring(line.indexOf(" ")+1).trim();
                    File newParent = new File(parent, filename);
                    FileInputStream inp = new FileInputStream(newParent);
                    readIn(inp, sb, newParent);
                    inp.close();
                } else 
                    sb.append(line);
                sb.append("\n");
            }
            reader.close();
        } catch (IOException ex) {
            Logger.getLogger(AlStrategy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
