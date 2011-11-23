package de.gaalop.productComputer.transformer;

import de.gaalop.productComputer.dataStruct.TCBlade;

/**
 * Represents a blade with a prefactor
 * @author Christian Steinmetz
 */
public class Summand {

    private float prefactor;
    private TCBlade blade;

    public Summand(float prefactor, TCBlade blade) {
        this.prefactor = prefactor;
        this.blade = blade;
    }

    public TCBlade getBlade() {
        return blade;
    }

    public float getPrefactor() {
        return prefactor;
    }

    public void setBlade(TCBlade blade) {
        this.blade = blade;
    }

    public void setPrefactor(float prefactor) {
        this.prefactor = prefactor;
    }

    

}
