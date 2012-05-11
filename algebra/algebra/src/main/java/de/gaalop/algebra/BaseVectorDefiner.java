package de.gaalop.algebra;

import java.util.HashSet;

/**
 * Provides methods for fast detection of baseVectors
 * @author Christian Steinmetz
 */
public class BaseVectorDefiner {

    private HashSet<String> baseVectors = new HashSet<String>();

    public void addBaseVector(String name) {
        baseVectors.add(name);
    }

    public boolean isBaseVector(String name) {
        return baseVectors.contains(name);
    }

    public void createFromAlBase(String[] alBase) {
        for (int i=1;i<alBase.length;i++)
            baseVectors.add(alBase[i]);
    }

}
