package de.gaalop.tba;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Provides methods for loading a Multiplication table from a file
 * @author Christian Steinmetz
 */
public class MultTableLoader {

    /**
     * Loads products from a file
     * @param useAlgebra The Algebra to be used
     * @param filename_Products The file which contains the products
     * @param useAsRessource true, if filename_products is a ressource
     * @throws IOException
     */
    private void loadProducts(UseAlgebra useAlgebra, String filename_Products, boolean useAsRessource) throws IOException {
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

            String[] parts = readed.split(";");

            BladeRef ref0 = Parser.parseBladeRef(parts[0]);
            BladeRef ref1 = Parser.parseBladeRef(parts[1]);

            int index0 = ref0.getIndex();
            int index1 = ref1.getIndex();

            useAlgebra.getTableInner().setProduct(index0, index1, Parser.parseMultivector(parts[2], algebra));
            useAlgebra.getTableOuter().setProduct(index0, index1, Parser.parseMultivector(parts[3], algebra));
            useAlgebra.getTableGeo().setProduct(index0, index1, Parser.parseMultivector(parts[4], algebra));


            line++;
        }

        d.close();
    }



    /**
     * Loads an algebra from different files
     * @param useAlgebra The algebra to be used
     * @param filename_Products The filename of the file which contains the products
     * @param useAsRessource true, if filename_products is a ressource
     * @throws IOException
     */
    public void load(UseAlgebra useAlgebra, String filename_Products, boolean useAsRessource) throws IOException {

        int bladeCount = useAlgebra.getAlgebra().getBlades().size();
        useAlgebra.getTableInner().createTable(bladeCount);
        useAlgebra.getTableOuter().createTable(bladeCount);
        useAlgebra.getTableGeo().createTable(bladeCount);

        loadProducts(useAlgebra, filename_Products, useAsRessource);

    }
}
