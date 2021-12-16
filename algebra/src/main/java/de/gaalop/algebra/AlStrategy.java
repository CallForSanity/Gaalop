package de.gaalop.algebra;

import de.gaalop.AlgebraStrategy;
import de.gaalop.CodeParserException;
import de.gaalop.InputFile;
import de.gaalop.OptimizationException;
import de.gaalop.cfg.AlgebraDefinitionFile;
import de.gaalop.cfg.AssignmentNode;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.cfg.Macro;
import de.gaalop.cfg.SequentialNode;
import de.gaalop.dfg.Expression;
import de.gaalop.dfg.OuterProduct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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
        if (graph.getOutputs().isEmpty() && plugin.atLeastOneQuestionSignedRequired) {
            throw new OptimizationException("There are no lines marked for optimization ('?')", graph);
        }

        Reader reader = null;
        try {
            //load algebra
            AlgebraDefinitionFile alFile = graph.getAlgebraDefinitionFile();
            alFile.setUsePrecalculatedTable(plugin.usePrecalulatedTables);
            alFile.setUseAsRessource(graph.asRessource);
            
            String baseDir = (graph.asRessource) ? "algebra" : graph.algebraBaseDirectory;
            
            if (!baseDir.endsWith("/")) baseDir += "/";
            
            baseDir += graph.algebraName+"/";
            
            alFile.setProductsFilePath(baseDir+"products.csv");

            if (plugin.algebraDefinitionString == null) {
                alFile.setProductsFilePath(baseDir+"products.csv");
                reader = (graph.asRessource)
                        ? new InputStreamReader(getClass().getResourceAsStream(baseDir+"definition.csv"))
                        : new FileReader(new File(baseDir+"definition.csv"));
            } else 
                reader = new StringReader(plugin.algebraDefinitionString);
            alFile.loadFromFile(reader);
            
            createBlades(alFile);

            //replace all functions / macros

            reader = (graph.asRessource)
                    ? new InputStreamReader(getClass().getResourceAsStream(baseDir+"macros.clu"))
                    : new FileReader(new File(baseDir+"macros.clu"));
            
            ControlFlowGraph macrosGraph = new de.gaalop.clucalc.input.Plugin().createCodeParser().parseFile(inputStreamToInputFile(reader, "macros", null));
            reader.close();
            HashMap<StringIntContainer, Macro> macros = MacrosVisitor.getAllMacros(macrosGraph);
            MacrosVisitor.getAllMacros(graph, macros);
            StringIntContainer dual = new StringIntContainer("Dual",1);
            if (macros.containsKey(dual)) {
                macros.put(new StringIntContainer("*",1), macros.get(dual));
            }

            //load user macros
            if (!plugin.getUserMacroFilePath().trim().equals("")) {
                File f = new File(plugin.userMacroFilePath);
                if (f.exists()) {
                     reader = new FileReader(f);
                     ControlFlowGraph userMacrosGraph = new de.gaalop.clucalc.input.Plugin().createCodeParser().parseFile(inputStreamToInputFile(reader, "userMacros", f.getParentFile()));
                     reader.close();
                     MacrosVisitor.getAllMacros(userMacrosGraph, macros);
                } else
                    System.err.println("Algebra Plugin: User Macro File Path does not exist!");
            }

            //Check on potential recursions
            RecursionChecker.check(graph, macros);
            
            //inline all macros
            Inliner.inline(graph, macros);
            
            //Remove Macro definitions and embedded evaluateNodes from graph
            LinkedList<AssignmentNode> toDelete = new LinkedList<AssignmentNode>();
            for (Macro macro: macros.values()) {
                graph.removeNode(macro);
                for (SequentialNode n: macro.getBody())
                    for (AssignmentNode evalNode: graph.getOnlyEvaluateNodes())
                        if (n == evalNode)
                            toDelete.add(evalNode);
            }
            
            graph.getOnlyEvaluateNodes().removeAll(toDelete);
            
            for (AssignmentNode node: graph.getOnlyEvaluateNodes()) {
                graph.addPragmaOnlyEvaluateVariable(node.getVariable().getName()); 
            }
            
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
                reader.close();
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
        
        TCBlade[] blades2 = BladeArrayRoutines.createBlades(Arrays.copyOfRange(alFile.base2,1,alFile.base2.length));
        alFile.blades2 = new Expression[blades2.length];
        for (int i = 0; i < blades2.length; i++) 
            alFile.blades2[i] = blades2[i].toExpression();
    }

    /**
     * Puts a reated into an InputFile
     * @param reader The reader
     * @param cluName The cluName to use
     * @param parent The parent file object
     * @return The InputFile
     */
    private InputFile inputStreamToInputFile(Reader reader, String cluName, File parent) {
        StringBuilder sb = new StringBuilder();
        readIn(reader, sb, parent);
        sb.append("\n");
        return new InputFile(cluName, sb.toString());
    }
    
    /**
     * Reads a reader in a stringbuilder object
     * @param reader The reader
     * @param sb The stringbuilder object to use
     * @param parent The parent file object
     */
    private void readIn(Reader reader, StringBuilder sb, File parent) {
        try {
            BufferedReader bufReader = new BufferedReader(reader);
            String line;
            while ((line = bufReader.readLine()) != null) {
                if (line.trim().startsWith("#include")) {
                    line = line.trim();
                    String filename = line.substring(line.indexOf(" ")+1).trim();
                    File newParent = new File(parent, filename);
                    FileReader rea = new FileReader(newParent);
                    readIn(rea, sb, newParent);
                    rea.close();
                } else 
                    sb.append(line);
                sb.append("\n");
            }
            bufReader.close();
        } catch (IOException ex) {
            Logger.getLogger(AlStrategy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
