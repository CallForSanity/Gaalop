package de.gaalop.cfg;

/**
 * Defines the signature of an algebra
 * @author csteinmetz15
 */
public class AlgebraSignature {
    
    public int p;
    public int q;
    public int r;
    
    public AlgebraSignature(int p, int q) {
        this(p, q, 0);
    }

    public AlgebraSignature(int p, int q, int r) {
        this.p = p;
        this.q = q;
        this.r = r;
    }
    
    public int getDimension() {
        return p + q + r;
    }
    
}
