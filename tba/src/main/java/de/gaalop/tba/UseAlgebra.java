package de.gaalop.tba;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UseAlgebra {

    public UseAlgebra(String algebraDirName) {
        algebra = new Algebra("algebra/"+algebraDirName+"/blades.csv");

        tableInner = new MultTableImpl();
        tableOuter = new MultTableImpl();
        tableGeo = new MultTableImpl();
        MultTableLoader loader = new MultTableLoader();
        try {
            loader.load(this, "algebra/"+algebraDirName+"/products.csv", "algebra/"+algebraDirName+"/replaces.csv");
        } catch (IOException ex) {
            Logger.getLogger(UseAlgebra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    private Algebra algebra;
    private IMultTable tableInner;
    private IMultTable tableOuter;
    private IMultTable tableGeo;

    public Multivector inner(Integer factor1, Integer factor2) {
        return tableInner.getProduct(factor1, factor2);
    }

    public Multivector outer(Integer factor1, Integer factor2) {
        return tableOuter.getProduct(factor1, factor2);
    }

    public Multivector geo(Integer factor1, Integer factor2) {
        return tableGeo.getProduct(factor1, factor2);
    }

    public int getBladeCount() {
        return algebra.getBlades().size();
    }

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
        return new UseAlgebra("conf5d");
    }

}
