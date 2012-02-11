package de.gaalop.visualizer;

import de.gaalop.visualizer.engines.RenderingEngine;
import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.visualizer.engines.lwjgl.LwJglRenderingEngine;
import de.gaalop.visualizer.engines.lwjgl.SimpleLwJglRenderingEngine;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 * This class visualizes the graph
 * @author Christian Steinmetz
 */
public class VisualizerCodeGenerator implements CodeGenerator {

    private Plugin plugin;
    private static final double EPSILON = 1E-1;
    
    public VisualizerCodeGenerator(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Set<OutputFile> generate(ControlFlowGraph in) throws CodeGeneratorException {
        HashMap<MultivectorComponent, Double> globalValues = new HashMap<MultivectorComponent, Double>(); //Sliders, ...

        HashMap<String, Color> colors = ColorEvaluater.getColors(in);

        HashMap<String, LinkedList<Point3d>> pointsToRender = new HashMap<String, LinkedList<Point3d>>();

        int a = 5;
        float dist = 0.1f;

        Point3d p = new Point3d(0, 0, 0);
        for (float x = -a; x <= a; x += dist) {
            for (float y = -a; y <= a; y += dist) {
                for (float z = -a; z <= a; z += dist) {

                    p.x = x;
                    p.y = y;
                    p.z = z;

                    HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>(globalValues);
                    values.put(new MultivectorComponent("_V_X", 0), p.x);
                    values.put(new MultivectorComponent("_V_Y", 0), p.y);
                    values.put(new MultivectorComponent("_V_Z", 0), p.z);

                    Evaluater evaluater = new Evaluater(values);
                    in.accept(evaluater);

                    HashMap<String, Double> squaredAndSummedValues = new HashMap<String, Double>();
                    for (MultivectorComponent mvC : values.keySet()) {

                        String name = mvC.getName();
                        if (name.startsWith("_V_PRODUCT")) {
                            if (!squaredAndSummedValues.containsKey(name)) {
                                squaredAndSummedValues.put(name, new Double(0));
                            }

                            double value = values.get(mvC);
                            squaredAndSummedValues.put(name, squaredAndSummedValues.get(name) + value * value);
                        }
                    }
                    for (String key : squaredAndSummedValues.keySet()) {
                        if (Math.sqrt(squaredAndSummedValues.get(key)) <= EPSILON) {
                            //output point!
                            if (!pointsToRender.containsKey(key)) {
                                pointsToRender.put(key, new LinkedList<Point3d>());
                            }
                            pointsToRender.get(key).add(new Point3d(p));
                        }
                    }
                }
            }
        }



        
        HashMap<String, PointCloud> clouds = new HashMap<String, PointCloud>();
        for (String key : pointsToRender.keySet()) {
            System.out.println(pointsToRender.get(key).size());
            clouds.put(key, new PointCloud(colors.get(key), pointsToRender.get(key)));
        }

        FileOutputStream outStream;
        try {
            outStream = new FileOutputStream("out.xml");
            saveClouds(clouds,outStream);
            outStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VisualizerCodeGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VisualizerCodeGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        //RenderingEngine engine = new SimpleLwJglRenderingEngine();
        //engine.render(clouds);

        HashSet<OutputFile> out = new HashSet<OutputFile>(); //only for debugging
        out.add(new OutputFile(in.getSource().getName(), in.toString(), Charset.forName("UTF-8")));
        return out;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, JDOMException {
        FileInputStream inStream = new FileInputStream("out.xml");
        HashMap<String, PointCloud> clouds = new HashMap<String, PointCloud>();
        loadClouds(clouds,inStream);
        RenderingEngine engine = new SimpleLwJglRenderingEngine();
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
