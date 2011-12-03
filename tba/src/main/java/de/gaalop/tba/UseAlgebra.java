package de.gaalop.tba;

import de.gaalop.algebra.AlStrategy;
import de.gaalop.algebra.BladeArrayRoutines;
import de.gaalop.algebra.TCBlade;
import de.gaalop.cfg.AlgebraDefinitionFile;
import de.gaalop.dfg.Expression;
import de.gaalop.productComputer.AlgebraDefinitionTC;
import de.gaalop.productComputer.exe.direct.MultTableAbsDirectComputer;
import de.gaalop.productComputer2.GeoProductCalculator;
import de.gaalop.productComputer2.InnerProductCalculator;
import de.gaalop.productComputer2.OuterProductCalculator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Stores an algebra and the according multiplication tables
 * @author Christian Steinmetz
 */
public class UseAlgebra {

    private Algebra algebra;
    private IMultTable tableInner;
    private IMultTable tableOuter;
    private IMultTable tableGeo;

    public UseAlgebra(AlgebraDefinitionFile alFile) {
        if (alFile.isUsePrecalculatedTable()) {
            algebra = new Algebra(alFile);
            tableInner = new MultTableImpl();
            tableOuter = new MultTableImpl();
            tableGeo = new MultTableImpl();
            tableInner.createTable(algebra.getBladeCount());
            tableOuter.createTable(algebra.getBladeCount());
            tableGeo.createTable(algebra.getBladeCount());

            MultTableLoader loader = new MultTableLoader();
            try {
                loader.load(this, alFile.getProductsFilePath(), alFile.isUseAsRessource());
            } catch (IOException ex) {
                Logger.getLogger(UseAlgebra.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            tableInner = new MultTableAbsDirectComputer(alFile, new InnerProductCalculator());
            tableInner.createTable(0);
            tableOuter = new MultTableAbsDirectComputer(alFile, new OuterProductCalculator());
            tableOuter.createTable(0);
            tableGeo = new MultTableAbsDirectComputer(alFile, new GeoProductCalculator());
            tableGeo.createTable(0);
            algebra = new Algebra(alFile);
        }
    }

    public UseAlgebra(Algebra algebra, int bladeCount) {
        this.algebra = algebra;
        tableInner = new MultTableImpl();
        tableOuter = new MultTableImpl();
        tableGeo = new MultTableImpl();
        tableInner.createTable(bladeCount);
        tableOuter.createTable(bladeCount);
        tableGeo.createTable(bladeCount);
    }

    /**
     * Returns the inner product of two blades
     * @param factor1 The index of the blade of the first factor
     * @param factor2 The index of the blade of the second factor
     * @return The inner product
     */
    public Multivector inner(Integer factor1, Integer factor2) {
        return tableInner.getProduct(factor1, factor2);
    }

    /**
     * Returns the outer product of two blades
     * @param factor1 The index of the blade of the first factor
     * @param factor2 The index of the blade of the second factor
     * @return The outer product
     */
    public Multivector outer(Integer factor1, Integer factor2) {
        return tableOuter.getProduct(factor1, factor2);
    }

    /**
     * Returns the geometric product of two blades
     * @param factor1 The index of the blade of the first factor
     * @param factor2 The index of the blade of the second factor
     * @return The geometric product
     */
    public Multivector geo(Integer factor1, Integer factor2) {
        return tableGeo.getProduct(factor1, factor2);
    }

    /**
     * Returns the number of blades in this algebra
     * @return The number of blades
     */
    public int getBladeCount() {
        return algebra.getBladeCount();
    }

    /**
     * Returns the grade of a blade in this algebra
     * @param blade The index of the blade
     * @return The grade
     */
    public int getGrade(int blade) {
        return algebra.getBlade(blade).getBases().size();
    }

    public Algebra getAlgebra() {
        return algebra;
    }

    public IMultTable getTableInner() {
        return tableInner;
    }

    public IMultTable getTableOuter() {
        return tableOuter;
    }

    public IMultTable getTableGeo() {
        return tableGeo;
    }

    /**
     * Returns the useAlgebra which represents the calculations in 5d conformal geometric algebra
     * @return The useAlgebra instance
     */
    public static UseAlgebra get5dConformalGATable() {
        return loadInternalAlgebra(5, true);
    }

    /**
     * Returns the useAlgebra which represents the calculations in 5d conformal geometric algebra
     * @return The useAlgebra instance
     */
    public static UseAlgebra get5dConformalGALive() {
        return loadInternalAlgebra(5, false);
    }

    /**
     * Returns the useAlgebra which represents the calculations in 3d geometric algebra
     * @return The useAlgebra instance
     */
    public static UseAlgebra get3dGA() {
        return loadInternalAlgebra(3, true);
    }

    /**
     * Loads an internal algebra with a given dimension
     * @param dimension The dimension
     * @param useTable Should the tables be used?
     * @return The usealgebra
     */
    private static UseAlgebra loadInternalAlgebra(int dimension, boolean useTable) {
        try {
            AlgebraDefinitionFile alFile = new AlgebraDefinitionFile();
            String baseDirPath = "algebra/"+dimension+"d/";
            alFile.loadFromFile(AlStrategy.class.getResourceAsStream(baseDirPath+"definition.csv"));
            alFile.setProductsFilePath(baseDirPath+"products.csv");
            alFile.setUseAsRessource(true);
            alFile.setUsePrecalculatedTable(useTable);

            TCBlade[] blades = BladeArrayRoutines.createBlades(Arrays.copyOfRange(alFile.base,1,alFile.base.length));
            alFile.blades = new Expression[blades.length];
            for (int i = 0; i < blades.length; i++) {
                alFile.blades[i] = blades[i].toExpression();
            }
            return new UseAlgebra(alFile);
        } catch (IOException ex) {
            Logger.getLogger(UseAlgebra.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Returns the product of two blades
     * @param typeProduct The type of the product
     * @param bladeL The index of the blade of the first factor
     * @param bladeR The index of the blade of the sectond factor
     * @return The product
     */
    public Multivector getProduct(Products typeProduct, int bladeL, int bladeR) {
        Multivector prodMv = null;

        switch (typeProduct) {
            case INNER:
                prodMv = inner(bladeL, bladeR);
                break;
            case OUTER:
                prodMv = outer(bladeL, bladeR);
                break;
            case GEO:
                prodMv = geo(bladeL, bladeR);
                break;
            default:
                System.err.println("Product type is unknown!");
                break;
        }
        return prodMv;

    }

    public void saveToDir(File dir, int from, int to) throws FileNotFoundException {

        //save blade file
        PrintWriter out = new PrintWriter(new File(dir,"blades.csv"));
        printBlades(out);
        out.close();

        //save products file
        out = new PrintWriter(new File(dir,"products.csv"));
        printProducts(out, from, to);

        out.close();

    }

    public void printBlades(PrintWriter out) {
        StringBuilder sb = new StringBuilder();
        for (String b: algebra.getBase()) {
            sb.append(";");
            sb.append(b);
        }
        if (algebra.getBase().length > 0)
            out.println(sb.substring(1));

        int bladeCount = algebra.getBladeCount();
        for (int blade=0;blade<bladeCount;blade++)
            out.println(algebra.getBlade(blade).toString());
    }

    public void printProducts(PrintWriter out, int from, int to) {
        int bladeCount = algebra.getBladeCount();
        for (int i=from;i<to;i++)
            for (int j=0;j<bladeCount;j++) {
                out.print("E");out.print(i);out.print(";");
                out.print("E");out.print(j);out.print(";");
                out.print(printMultivector(tableInner.getProduct(i, j)));out.print(";");
                out.print(printMultivector(tableOuter.getProduct(i, j)));out.print(";");
                out.print(printMultivector(tableGeo.getProduct(i, j)));
                out.println();
            }
    }

    private String printMultivector(Multivector m) {
        StringBuilder sb = new StringBuilder();
        for (BladeRef ref: m.getBlades()) {

            switch (ref.getPrefactor()) {
                case -1:
                    sb.append("-E"+ref.getIndex());
                    break;
                case 0:
                    break;
                case 1:
                    sb.append("+E"+ref.getIndex());
                    break;
                default:
                    System.err.println("Only -1,0,1 allowed as prefactors in multivectors");
                    break;
            }


        }
        if (sb.length()==0) return "";
        if (sb.charAt(0) == '+')
            return sb.substring(1);
        else
            return sb.toString();
    }


}
