package de.gaalop.tba;

import de.gaalop.cfg.AlgebraSignature;
import de.gaalop.dfg.Expression;
import java.io.File;
import java.io.IOException;
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
    private boolean N3;

    public UseAlgebra(String algebraDirName) {

        boolean useAsRessource = algebraDirName.equalsIgnoreCase("5d")
                || algebraDirName.equalsIgnoreCase("2d")
                || algebraDirName.equalsIgnoreCase("3d");
        N3 = algebraDirName.equalsIgnoreCase("5d");
        String dirName = (useAsRessource) ? "algebra/" + algebraDirName + "/" : new File(algebraDirName).getAbsolutePath() + File.separatorChar;

        algebra = new Algebra(dirName + "blades.csv", useAsRessource);

        tableInner = new MultTableImpl();
        tableOuter = new MultTableImpl();
        tableGeo = new MultTableImpl();
        MultTableLoader loader = new MultTableLoader();
        try {
            loader.load(this, dirName + "products.csv", useAsRessource);
        } catch (IOException ex) {
            Logger.getLogger(UseAlgebra.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean isN3() {
        return N3;
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
        return algebra.getBlades().size();
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
     * @return The useAlgerba instance
     */
    public static UseAlgebra get5dConformalGA() {
        //load 5d conformal algebra
        return new UseAlgebra("5d");
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

    /**
     * Creates a new AlgebraSignature from this algebra
     * @return The new AlgebraSignature
     */
    public AlgebraSignature getAlgebraSignature() {

        Expression[] bladlist = new Expression[algebra.getBlades().size()];
        for (int blade = 0; blade < bladlist.length; blade++) {
            bladlist[blade] = algebra.getBlades().get(blade).getExpression();
        }


        AlgebraSignature sig = new AlgebraSignature(algebra.getBaseCount(), bladlist, N3);

        sig.setBasesFromBladesFile(algebra.getBase());

        return sig;
    }
}
