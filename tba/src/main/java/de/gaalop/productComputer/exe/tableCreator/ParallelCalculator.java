package de.gaalop.productComputer.exe.tableCreator;

import antlr.RecognitionException;
import de.gaalop.OptimizationException;
import de.gaalop.algebra.AlStrategy;
import de.gaalop.cfg.AlgebraDefinitionFile;
import de.gaalop.dfg.Expression;
import de.gaalop.productComputer.AlgebraDefinitionTC;
import de.gaalop.productComputer.BladeArrayRoutines;
import de.gaalop.productComputer.bladeOperations.BladeIndexer;
import de.gaalop.productComputer.bladeOperations.FacadeBaseChange;
import de.gaalop.productComputer.dataStruct.TCBlade;
import de.gaalop.productComputer.dataStruct.TCExpression;
import de.gaalop.productComputer.simplification.Simplifier;
import de.gaalop.tba.Algebra;
import de.gaalop.tba.Blade;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Creates a product table using a multithreading technique
 * @author Christian Steinmetz
 */
public class ParallelCalculator {

    /**
     * Creates a table for a geometric algebra
     * @param args the command line arguments: #Threads,AlgebraDefinitionPath,useAsRessource
     */
    public static void main(String[] args) throws OptimizationException, RecognitionException, FileNotFoundException, IOException, InterruptedException {
        
        if (args.length<3) args = new String[]{"2","algebra/5d/definition.csv","true"};
        int threadCount = Integer.parseInt(args[0]);
        AlgebraDefinitionFile alFile = new AlgebraDefinitionFile();

        InputStream inputStream = (Boolean.parseBoolean(args[2]))
            ? AlStrategy.class.getResourceAsStream(args[1])
            : new FileInputStream(args[1]);
        alFile.loadFromFile(inputStream);
        
        AlgebraDefinitionTC algebraDefinition = new AlgebraDefinitionTC(alFile);

        //Create Algebra einf e0
        Algebra algebra = new Algebra();
        TCBlade[] blades = createAlgebra(algebraDefinition.base, algebra);

        BladeIndexer indexer = new BladeIndexer();
        Expression[] simpBlades = baseChange(blades, indexer, algebraDefinition);

        //Create Algebra2 ep em
        Algebra algebra2 = new Algebra();
        TCBlade[] blades2 = createAlgebra(algebraDefinition.base2, algebra2);

        algebra.buildMap();
        algebra2.buildMap();

        // THREAD
        CalcThread[] threads = new CalcThread[threadCount];
        for (int i=0;i<threadCount;i++) {
            CalcThread t = new CalcThread();
            threads[i] = t;
            t.algebra = algebra;
            t.algebra2 = algebra2;
            t.algebraDefinition = algebraDefinition;
            t.blades2 = blades2;
            t.indexer = indexer;
            t.simpBlades = simpBlades;
            t.from = (i*blades2.length)/threadCount;
            t.to = ((i+1)*blades2.length)/threadCount;
        }

        for (int i=0;i<threadCount;i++) 
            threads[i].start();

        //JOIN
        
        PrintWriter out = new PrintWriter("products.csv");
        for (int i=0;i<threadCount;i++) {
            threads[i].join();
            threads[i].useAlgebra.printProducts(out, threads[i].from, threads[i].to);
        }
        out.close();

        out = new PrintWriter("blades.csv");
        threads[0].useAlgebra.printBlades(out);
        out.close();
    }

    /**
     * Creates an array of blades from a base
     * @param base The base
     * @param algebra The algebra
     * @return The blades
     */
    private static TCBlade[] createAlgebra(String[] base, Algebra algebra) {
        TCBlade[] blades = BladeArrayRoutines.createBlades(Arrays.copyOfRange(base, 1, base.length));
        int bladeCount = blades.length;
        algebra.setBase(base);
        for (int i=0;i<bladeCount;i++)
            algebra.setBlade(i, new Blade(blades[i].getBase()));
        return blades;
    }

    /**
     * Changes the base of an array of blades
     * @param blades The array of blades
     * @param indexer The indexer to be used
     * @param algebraDefinition The algebra definition
     * @return The new array of blades with changed base
     */
    private static Expression[] baseChange(TCExpression[] blades, BladeIndexer indexer, AlgebraDefinitionTC algebraDefinition) {
        FacadeBaseChange facade = new FacadeBaseChange(algebraDefinition);

        int bladeCount = blades.length;
        Expression[] result = new Expression[bladeCount];
        for (int i=0;i<bladeCount;i++)
             result[i] = Simplifier.simplify(facade.transformToPlusMinusBase(blades[i]),indexer);

        return result;
    }



}
