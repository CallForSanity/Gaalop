package de.gaalop.visualizer;

import java.util.HashMap;

/**
 * Represents a collection of point clouds and implements operation on them
 * like loading and saving from/to a XML file.
 * @author Christian Steinmetz
 */
public class PointClouds extends HashMap<String, PointCloud> {
    
    /**
     * Loads all point clouds from a XML file
     * @param file The XML file
     
    public void loadFromFile(File file) {
        try {
            Element rootElement = new SAXBuilder().build(file).getRootElement();
            for (Object childObj: rootElement.getChildren()) {
                PointCloud pointCloud = new PointCloud((Element) childObj);
                put(pointCloud.name, pointCloud);
            }
        } catch (JDOMException ex) {
            Logger.getLogger(PointClouds.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PointClouds.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    */
    
    /**
     * Saves all points clouds to a XML file
     * @param file The XML file
     
    public void saveToFile(File file) {
        Element rootElement = new Element("PointClouds");
        for (String key: keySet()) 
            rootElement.addContent(get(key).toElement());

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            new XMLOutputter().output(new Document(rootElement), outputStream);
            outputStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PointClouds.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PointClouds.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    */
}
