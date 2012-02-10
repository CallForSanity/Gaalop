/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.gaalop.visualizer;

import java.awt.Color;
import java.util.LinkedList;

/**
 *
 * @author Christian Steinmetz
 */
public class PointCloud {

    public Color color;
    public LinkedList<Point3d> points;

    public PointCloud(Color color, LinkedList<Point3d> points) {
        this.color = color;
        this.points = points;
    }

    

}
