package de.gaalop.visualizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Christian Steinmetz
 */
public class PointClouds extends HashMap<String, PointCloud> {
    
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

}
