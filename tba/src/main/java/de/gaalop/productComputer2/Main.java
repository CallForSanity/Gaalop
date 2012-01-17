/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.productComputer2;

import de.gaalop.algebra.AlStrategy;
import de.gaalop.cfg.AlgebraDefinitionFile;
import de.gaalop.productComputer.AlgebraPC;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Creates the multiplication table
 * @author Christian Steinmetz
 */
public class Main {

    public static void main(String[] args) throws IOException {
            ProductComputer2 productComputer = new ProductComputer2();
            InputStream inputStream = AlStrategy.class.getResourceAsStream("algebra/5d/definition.csv");
            AlgebraDefinitionFile alFile = new AlgebraDefinitionFile();
            alFile.loadFromFile(inputStream);
            AlgebraPC algebraPC = new AlgebraPC(alFile);
            productComputer.initialize(algebraPC);

            ProductCalculator2 inner = new InnerProductCalculator();
            ProductCalculator2 outer = new OuterProductCalculator();
            ProductCalculator2 geo = new GeoProductCalculator();

            PrintWriter out = new PrintWriter("products.csv");
            int bladeCount = (int) Math.pow(2,algebraPC.base.length);
            for (int i = 0; i < bladeCount; i++) {
                for (int j = 0; j < bladeCount; j++) {
                    out.print("E"+i+";E"+j+";");
                    out.print(productComputer.calcProduct(i, j, inner).print());
                    out.print(";");
                    out.print(productComputer.calcProduct(i, j, outer).print());
                    out.print(";");
                    out.println(productComputer.calcProduct(i, j, geo).print());
                }
            }

            out.close();
    }

}
