package de.gaalop.visualizer;

/**
 * Represents a point in 3d space in double precision
 * @author Christian Steinmetz
 */
public class Point3d {

    public double x;
    public double y;
    public double z;

    public Point3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3d(Point3d p) {
        x = p.x;
        y = p.y;
        z = p.z;
    }

    @Override
    public String toString() {
        return x+","+y+","+z;
    }


}
