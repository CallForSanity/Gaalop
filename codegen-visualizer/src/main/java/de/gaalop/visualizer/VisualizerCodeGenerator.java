package de.gaalop.visualizer;

import de.gaalop.visualizer.zerofinding.ZeroFinder;
import de.gaalop.visualizer.engines.RenderingEngine;
import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.visualizer.engines.lwjgl.SimpleLwJglRenderingEngine;
import de.gaalop.visualizer.zerofinding.DiscreteCubeMethod;
import de.gaalop.visualizer.zerofinding.RayMethod;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 * This class visualizes the graph
 * @author Christian Steinmetz
 */
public class VisualizerCodeGenerator {

    private Plugin plugin;
    
    
    public VisualizerCodeGenerator(Plugin plugin) {
        this.plugin = plugin;
    }

    public void generate(ControlFlowGraph in) throws CodeGeneratorException {        
        HashMap<MultivectorComponent, Double> globalValues = new HashMap<MultivectorComponent, Double>(); //Sliders, ...

        HashMap<String, Color> colors = ColorEvaluater.getColors(in);

        ZeroFinder finder = new RayMethod(plugin.maximaCommand);
        HashMap<String, LinkedList<Point3d>> pointsToRender = finder.findZeroLocations(in, globalValues);

        HashMap<String, PointCloud> clouds = new HashMap<String, PointCloud>();
        for (String key : pointsToRender.keySet()) {
            System.out.println(pointsToRender.get(key).size());
            clouds.put(key, new PointCloud(colors.get(key.substring(0, key.length()-2)), pointsToRender.get(key)));
        }

        /*
        FileOutputStream outStream;
        try {
            outStream = new FileOutputStream("E:\\out.xml");
            saveClouds(clouds,outStream);
            outStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VisualizerCodeGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VisualizerCodeGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        */

        RenderingEngine engine = new SimpleLwJglRenderingEngine(plugin.lwJglNativePath);
        engine.render(clouds);

    }

    //direct rendering
    public static void main1(String[] args) throws FileNotFoundException, IOException, JDOMException {
        FileInputStream inStream = new FileInputStream("E:\\out.xml");
        HashMap<String, PointCloud> clouds = new HashMap<String, PointCloud>();
        loadClouds(clouds,inStream);
        RenderingEngine engine = new SimpleLwJglRenderingEngine("/usr/lib/jni/");
        engine.render(clouds);
        inStream.close();
    }

    private static void loadClouds(HashMap<String, PointCloud> clouds, InputStream inStream) throws IOException, JDOMException {
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

    }

    private static void saveClouds(HashMap<String, PointCloud> clouds, OutputStream outStream) throws IOException {
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
