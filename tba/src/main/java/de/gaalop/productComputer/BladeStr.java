package de.gaalop.productComputer;

import java.util.Arrays;

/**
 * Blade represented by an array of strings
 * @author Christian Steinmetz
 */
public class BladeStr {

    private float prefactor;
    private String[] baseVectors;

    public BladeStr(String baseVector) {
        this.prefactor = 1;
        this.baseVectors = new String[]{baseVector};
    }

    public BladeStr(String[] baseVectors) {
        this.prefactor = 1;
        this.baseVectors = baseVectors;
    }

    public BladeStr(float prefactor, String baseVector) {
        this.prefactor = prefactor;
        this.baseVectors = new String[]{baseVector};
    }

    public BladeStr(float prefactor, String[] baseVectors) {
        this.prefactor = prefactor;
        this.baseVectors = baseVectors;
    }

    public String[] getBaseVectors() {
        return baseVectors;
    }

    public float getPrefactor() {
        return prefactor;
    }

    public void setBaseVectors(String[] baseVectors) {
        this.baseVectors = baseVectors;
    }

    public void setPrefactor(float prefactor) {
        this.prefactor = prefactor;
    }

    @Override
    public String toString() {
        return prefactor+Arrays.toString(baseVectors);
    }

}
