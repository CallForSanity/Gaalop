package de.gaalop.tba.linePointDistance;

import de.gaalop.tba.gps.Point3D;

/**
 * Implements a vector in 3d euclidean space
 * @author Christian Steinmetz
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

    /**
     * Returns the length of the vector
     * @return The length
     */
    public float abs() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * Normalizes this vector
     */
    public void normalize() {
        float ab = abs();
        x /= ab;
        y /= ab;
        z /= ab;
    }

    /**
     * Performs a scalar multiplication
     * @param scalar The scalar to multiplicate with
     */
    public void scalarMultiplication(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
    }

    /**
     * Returns a copy of this vector
     * @return The copy
     */
    public Vec3D getCopy() {
        return new Vec3D(x, y, z);
    }

    /**
     * Applies the vector to a point
     * @param p The point
     * @return The new point
     */
    public Point3D applyToPoint(Point3D p) {
        return new Point3D(p.x + x, p.y + y, p.z + z);
    }

    /**
     * Performs the dot product with another Vec3d object
     * @param v The other Vec3d object
     * @return The result of the dot product operation
     */
    public float dotProduct(Vec3D v) {
        return x * v.x + y * v.y + z * v.z;
    }
}
