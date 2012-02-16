package de.gaalop.visualizer;

import java.awt.Color;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 * Implements a point cloud and common operations on point clouds
 * @author Christian Steinmetz
 */
public class PointCloud {

    public Color color;
    public LinkedList<Point3d> points;

    public PointCloud(Color color, LinkedList<Point3d> points) {
        this.color = color;
        this.points = points;
    }

    /**
     * Load clouds from an inpustream
     * @param inStream The inputstream to load from
     * @throws IOException
     * @throws JDOMException 
     */
    public static HashMap<String, PointCloud> loadClouds(InputStream inStream) throws IOException, JDOMException {
        HashMap<String, PointCloud> clouds = new HashMap<String, PointCloud>();
        Element root = new SAXBuilder().build(inStream).getRootElement();
        for (Object oCloud: root.getChildren()) {
            Element e = (Element) oCloud;

            LinkedList<Point3d> points = new LinkedList<Point3d>();

            PointCloud pointCloud = new PointCloud(
                    new Color(
                        Integer.parseInt(e.getAttributeValue("colorR")),
                        Integer.parseInt(e.getAttributeValue("colorG")),
                        Integer.parseInt(e.getAttributeValue("colorB")),
                        Integer.parseInt(e.getAttributeValue("colorA"))
                    ),points);
            clouds.put(e.getAttributeValue("name"), pointCloud);
            
            Element pointsElement = e.getChild("points");
            for (Object oPoint: pointsElement.getChildren()) {
                Element ePoint = (Element) oPoint;
                points.add(new Point3d(
                        Double.parseDouble(ePoint.getAttributeValue("x")),
                        Double.parseDouble(ePoint.getAttributeValue("y")),
                        Double.parseDouble(ePoint.getAttributeValue("z"))
                        ));
            }

        }
        return clouds;
    }

    /**
     * Save clouds to a outputstream
     * @param clouds The clouds to be saved
     * @param outStream The outputstream to write on
     * @throws IOException 
     */
    public static void saveClouds(HashMap<String, PointCloud> clouds, OutputStream outStream) throws IOException {
        Element root = new Element("clouds");
        Document doc = new Document(root);
        int i=0;
        for (String c: clouds.keySet()) {
            Element e = new Element("cloud_"+i);
            root.addContent(e);
            e.setAttribute("name", c);

            Color color = clouds.get(c).color;
            e.setAttribute("colorR", Integer.toString(color.getRed()));
            e.setAttribute("colorG", Integer.toString(color.getGreen()));
            e.setAttribute("colorB", Integer.toString(color.getBlue()));
            e.setAttribute("colorA", Integer.toString(color.getAlpha()));

            Element points = new Element("points");
            e.addContent(points);

            int j=0;
            for (Point3d p: clouds.get(c).points) {
                Element point = new Element("point_"+j);
                points.addContent(point);
                point.setAttribute("x", Double.toString(p.x));
                point.setAttribute("y", Double.toString(p.y));
                point.setAttribute("z", Double.toString(p.z));
                j++;
            }

            i++;
        }

        XMLOutputter outputter = new XMLOutputter();
        outputter.output(doc, outStream);
    }            
}
