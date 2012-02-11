/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.visualizer.engines.lwjgl;

/**
 *
 * @author Christian Steinmetz
 */
public class Vec3f {

    public float x;
    public float y;
    public float z;

    public Vec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return x+","+y+","+z;
    }

    void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }



}
