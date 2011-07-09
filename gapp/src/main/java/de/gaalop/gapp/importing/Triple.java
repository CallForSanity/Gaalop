package de.gaalop.gapp.importing;

/**
 * Data Container. Represents three values in a GA product.
 * The left blade index, the right blade index and the sign of the result,
 * when multiplying left blade and right blade.
 *
 * @author christian
 */
public class Triple {

    private int leftBlade;
    private int rightBlade;
    private byte sign;

    public Triple(int leftBlade, int rightBlade, byte sign) {
        this.leftBlade = leftBlade;
        this.rightBlade = rightBlade;
        this.sign = sign;
    }

    public int getLeftBlade() {
        return leftBlade;
    }

    public int getRightBlade() {
        return rightBlade;
    }

    public byte getSign() {
        return sign;
    }

    public void setLeftBlade(int leftBlade) {
        this.leftBlade = leftBlade;
    }

    public void setRightBlade(int rightBlade) {
        this.rightBlade = rightBlade;
    }

    public void setSign(byte sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return leftBlade+" mult "+rightBlade+" -> "+sign;
    }


    

}
