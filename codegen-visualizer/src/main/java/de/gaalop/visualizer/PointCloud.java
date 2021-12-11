package de.gaalop.visualizer;

import java.awt.Color;
import java.util.LinkedList;

/**
 * Implements a point cloud and common operations on point clouds like loading and saving from/to XML Element
 * @author Christian Steinmetz
 */
public class PointCloud {

    public String name;
    public Color color;
    public LinkedList<Point3d> points;

    public PointCloud(String name, Color color, LinkedList<Point3d> points) {
        this.name = name;
        this.color = color;
        this.points = points;
    }
    
    /**
     * Reads in a given CSV line
     * @param element The XML Element
     
    public PointCloud(String line) {
        name = element.getAttributeValue("name");
        color = new Color(
                Integer.parseInt(element.getAttributeValue("colorR")), 
                Integer.parseInt(element.getAttributeValue("colorG")), 
                Integer.parseInt(element.getAttributeValue("colorB")), 
                Integer.parseInt(element.getAttributeValue("colorA"))
                );
        points = new LinkedList<Point3d>();
        for (Object childObj: element.getChildren()) {
            Element child = (Element) childObj;
            points.add(new Point3d(
                    Double.parseDouble(child.getAttributeValue("x")), 
                    Double.parseDouble(child.getAttributeValue("y")), 
                    Double.parseDouble(child.getAttributeValue("z"))
                    ));
        }
    }*/
    
    /**
     * Creates an XML Element from this instance and returns it
     * @return The created XML Element
     
    public Element toElement() {
        Element result = new Element("PointCloud");
        result.setAttribute("name", name);
        result.setAttribute("colorR", Integer.toString(color.getRed()));
        result.setAttribute("colorG", Integer.toString(color.getGreen()));
        result.setAttribute("colorB", Integer.toString(color.getBlue()));
        result.setAttribute("colorA", Integer.toString(color.getAlpha()));
        for (Point3d p: points) {
            Element pElement = new Element("p");
            pElement.setAttribute("x", Double.toString(p.x));
            pElement.setAttribute("y", Double.toString(p.y));
            pElement.setAttribute("z", Double.toString(p.z));
            result.addContent(pElement);
        }
        return result;
    }
    */
}
