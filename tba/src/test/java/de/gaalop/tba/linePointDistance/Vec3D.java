package de.gaalop.tba.linePointDistance;

import de.gaalop.tba.gps.Point3D;

/**
 *
 * @author christian
 */
public class Vec3D {

    private float x;
    private float y;
    private float z;

    public Vec3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float abs() {
        return (float) Math.sqrt(x*x+y*y+z*z);
    }

    public void normalize() {
        float ab = abs();
        x /= ab;
        y /= ab;
        z /= ab;
    }

    public void scalarMultiplication(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
    }

    public Vec3D getCopy() {
        return new Vec3D(x,y,z);
    }

    public Point3D applyToPoint(Point3D p) {
        return new Point3D(p.x+x, p.y+y, p.z+z);
    }

    public float dotProduct(Vec3D r) {
        return x*r.x+y*r.y+z*r.z;
    }

}
