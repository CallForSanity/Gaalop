package de.gaalop.tba;

import de.gaalop.common.StringMethods;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 * Loads a Multiplication table from a file
 * @author Christian Steinmetz
 */
public class MultTableLoader {

    /**
     * Stores a string to search and a string for replacement
     */
    private class ReplaceString {

        public String regex;
        public String replacement;

        public ReplaceString(String regex, String replacement) {
            this.regex = regex;
            this.replacement = replacement;
        }
    }

    /**
     * Loads a list of Replacestring from a file
     * @param filename_Replaces The file which contains the replaces
     * @return The list of Replacestring
     * @param useAsRessource true, if filename_products is a ressource
     * @throws IOException
     */
    private LinkedList<ReplaceString> loadReplaces(String filename_Replaces, boolean useAsRessource) throws IOException {

        LinkedList<ReplaceString> replaces = new LinkedList<MultTableLoader.ReplaceString>();

        InputStream resourceAsStream;
        if (useAsRessource)
            resourceAsStream = getClass().getResourceAsStream(filename_Replaces);
        else
            resourceAsStream = new FileInputStream(new File(filename_Replaces));

        BufferedReader dRepl = new BufferedReader(new InputStreamReader(resourceAsStream));

        while (dRepl.ready()) {
            String readed = dRepl.readLine();
            String[] parts = StringMethods.splitStrIgnoreConnectedSequences(readed, ';', '"', '"');
            replaces.add(new ReplaceString(parts[0], parts[1]));
        }

        dRepl.close();

        return replaces;
    }

    /**
     * Loads products from a file
     * @param useAlgebra The Algebra to be used
     * @param filename_Products The file which contains the products
     * @param replaces The replaces list to be used
     * @param useAsRessource true, if filename_products is a ressource
     * @throws IOException
     */
    private void loadProducts(UseAlgebra useAlgebra, String filename_Products, LinkedList<ReplaceString> replaces, boolean useAsRessource) throws IOException {
        Algebra algebra = useAlgebra.getAlgebra();
        InputStream resourceAsStream;
        if (useAsRessource)
            resourceAsStream = getClass().getResourceAsStream(filename_Products);
        else
            resourceAsStream = new FileInputStream(new File(filename_Products));

        BufferedReader d = new BufferedReader(new InputStreamReader(resourceAsStream));

        int line = 0;
        while (d.ready()) {
            String readed = d.readLine();

            
            for (ReplaceString curRepl : replaces) {
                readed = readed.replaceAll(curRepl.regex, curRepl.replacement);
            }
            String[] parts = readed.split(";");

            int index0 = algebra.getIndex(Blade.parseStr(parts[0], algebra));
            int index1 = algebra.getIndex(Blade.parseStr(parts[1], algebra));

            useAlgebra.getTableInner().setProduct(index0, index1, Multivector.parse(parts[2], algebra));
            useAlgebra.getTableOuter().setProduct(index0, index1, Multivector.parse(parts[3], algebra));
            useAlgebra.getTableGeo().setProduct(index0, index1, Multivector.parse(parts[4], algebra));


            line++;
        }

        d.close();
    }


    /**
     * Loads an algebra from different files
     * @param useAlgebra The algebra to be used
     * @param filename_Products The filename of the file which contains the products
     * @param filename_Replaces The filename of the file which contains the replaces
     * @throws IOException
     */
    public void load(UseAlgebra useAlgebra, String filename_Products, String filename_Replaces, boolean useAsRessource) throws IOException {

        int bladeCount = useAlgebra.getAlgebra().getBlades().size();
        useAlgebra.getTableInner().createTable(bladeCount);
        useAlgebra.getTableOuter().createTable(bladeCount);
        useAlgebra.getTableGeo().createTable(bladeCount);

        LinkedList<ReplaceString> replaces = loadReplaces(filename_Replaces, useAsRessource);
        loadProducts(useAlgebra, filename_Products, replaces, useAsRessource);

    }
}
