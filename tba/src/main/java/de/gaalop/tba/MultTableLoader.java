package de.gaalop.tba;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class MultTableLoader {

    private class ReplaceString {

        public String regex;
        public String replacement;

        public ReplaceString(String regex, String replacement) {
            this.regex = regex;
            this.replacement = replacement;
        }
    }

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

    private void loadProducts(IMultTable tableInner, IMultTable tableOuter, IMultTable tableGeo, Algebra algebra, String filename_Products, LinkedList<ReplaceString> replaces) throws IOException {
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

                tableInner.setProduct(index0, index1, Multivector.parse(parts[2], base, algebra));
                tableOuter.setProduct(index0, index1, Multivector.parse(parts[3], base, algebra));
                tableGeo.setProduct(index0, index1, Multivector.parse(parts[4], base, algebra));
            }

            line++;
        }

        d.close();
    }

    private void insertStandardProducts(int bladeCount, IMultTable tableInner, IMultTable tableOuter, IMultTable tableGeo, Algebra algebra) {
        //insert standard products with 1

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

    public void load(IMultTable tableInner, IMultTable tableOuter, IMultTable tableGeo, Algebra algebra, String filename_Products, String filename_Replaces) throws IOException {

        int bladeCount = algebra.getBlades().size();
        tableInner.createTable(bladeCount);
        tableOuter.createTable(bladeCount);
        tableGeo.createTable(bladeCount);

        LinkedList<ReplaceString> replaces = loadReplaces(filename_Replaces);
        loadProducts(tableInner, tableOuter, tableGeo, algebra, filename_Products, replaces);
        insertStandardProducts(bladeCount, tableInner, tableOuter, tableGeo, algebra);

    }
}
