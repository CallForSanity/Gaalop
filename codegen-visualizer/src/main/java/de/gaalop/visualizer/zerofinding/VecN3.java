package de.gaalop.visualizer.zerofinding;

/**
 *
 * @author Christian Steinmetz
 */
public class VecN3 {
    
    public double x;
    public double y;
    public double z;

    public VecN3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void normalize() {
        double denom = Math.sqrt(x*x+y*y+z*z);
        if (Math.abs(denom) < 10E-8) return;
        x /= denom;
        y /= denom;
        z /= denom;
    }

}
