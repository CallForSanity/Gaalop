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
    
    public boolean hasSignature(int p, int q, int r) {
        return p == this.p && q == this.q && r == this.r;
    }

    @Override
    public String toString() {
        return p + "," + q + "," + r;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + this.p;
        hash = 31 * hash + this.q;
        hash = 31 * hash + this.r;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AlgebraSignature other = (AlgebraSignature) obj;
        if (this.p != other.p) {
            return false;
        }
        if (this.q != other.q) {
            return false;
        }
        if (this.r != other.r) {
            return false;
        }
        return true;
    }

    
    
    
    
}
