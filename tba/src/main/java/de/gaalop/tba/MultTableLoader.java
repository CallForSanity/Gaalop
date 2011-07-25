package de.gaalop.tba;

import de.gaalop.common.StringMethods;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 * Loads a Multiplication table from a file
 * @author christian
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
     * @throws IOException
     */
    private LinkedList<ReplaceString> loadReplaces(String filename_Replaces) throws IOException {

        LinkedList<ReplaceString> replaces = new LinkedList<MultTableLoader.ReplaceString>();

        InputStream resourceAsStream = getClass().getResourceAsStream(filename_Replaces);
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
     * @throws IOException
     */
    private void loadProducts(UseAlgebra useAlgebra, String filename_Products, LinkedList<ReplaceString> replaces) throws IOException {
        Algebra algebra = useAlgebra.getAlgebra();
        String[] base = algebra.getBase();
        InputStream resourceAsStream = getClass().getResourceAsStream(filename_Products);

        BufferedReader d = new BufferedReader(new InputStreamReader(resourceAsStream));

        int line = 0;
        while (d.ready()) {
            String readed = d.readLine();

            if (line > 0) {

                for (ReplaceString curRepl : replaces) {
                    readed = readed.replaceAll(curRepl.regex, curRepl.replacement);
                }
                String[] parts = readed.split(";");

                int index0 = algebra.getIndex(Blade.parseStr(parts[0], algebra));
                int index1 = algebra.getIndex(Blade.parseStr(parts[1], algebra));

                useAlgebra.getTableInner().setProduct(index0, index1, Multivector.parse(parts[2], algebra));
                useAlgebra.getTableOuter().setProduct(index0, index1, Multivector.parse(parts[3], algebra));
                useAlgebra.getTableGeo().setProduct(index0, index1, Multivector.parse(parts[4], algebra));
            }

            line++;
        }

        d.close();
    }

    /**
     * Inserts the standard products, e.g. the products with 1
     * @param useAlgebra The algebra to be used
     */
    private void insertStandardProducts(UseAlgebra useAlgebra) {
        Algebra algebra = useAlgebra.getAlgebra();
        //insert standard products with 1

        IMultTable tableInner = useAlgebra.getTableInner();
        IMultTable tableOuter = useAlgebra.getTableOuter();
        IMultTable tableGeo = useAlgebra.getTableGeo();

        int bladeCount = algebra.getBlades().size();

        for (int i = 0; i < bladeCount; i++) {
            Blade blade = algebra.getBlade(i);
            Multivector mvBlade = new Multivector(algebra);
            mvBlade.addBlade(blade);

            Multivector mvNull = new Multivector(algebra);

            tableInner.setProduct(0, i, mvNull);
            tableInner.setProduct(i, 0, mvNull);
            tableOuter.setProduct(0, i, mvBlade);
            tableOuter.setProduct(i, 0, mvBlade);
            tableGeo.setProduct(0, i, mvBlade);
            tableGeo.setProduct(i, 0, mvBlade);

        }
    }

    /**
     * Loads an algebra from different files
     * @param useAlgebra The algebra to be used
     * @param filename_Products The filename of the file which contains the products
     * @param filename_Replaces The filename of the file which contains the replaces
     * @throws IOException
     */
    public void load(UseAlgebra useAlgebra, String filename_Products, String filename_Replaces) throws IOException {

        int bladeCount = useAlgebra.getAlgebra().getBlades().size();
        useAlgebra.getTableInner().createTable(bladeCount);
        useAlgebra.getTableOuter().createTable(bladeCount);
        useAlgebra.getTableGeo().createTable(bladeCount);

        LinkedList<ReplaceString> replaces = loadReplaces(filename_Replaces);
        loadProducts(useAlgebra, filename_Products, replaces);
        insertStandardProducts(useAlgebra);

    }
}
