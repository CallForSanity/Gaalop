package de.gaalop.visualizer;

import de.gaalop.visualizer.engines.RenderingEngine;
import de.gaalop.CodeGenerator;
import de.gaalop.CodeGeneratorException;
import de.gaalop.OutputFile;
import de.gaalop.cfg.ControlFlowGraph;
import de.gaalop.dfg.MultivectorComponent;
import de.gaalop.visualizer.engines.lwjgl.LwJglRenderingEngine;
import java.awt.Color;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * This class visualizes the graph
 * @author Christian Steinmetz
 */
public class VisualizerCodeGenerator implements CodeGenerator {

    private Plugin plugin;
    private static final double EPSILON = 1E-3;

    public VisualizerCodeGenerator(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Set<OutputFile> generate(ControlFlowGraph in) throws CodeGeneratorException {
        HashMap<MultivectorComponent, Double> globalValues = new HashMap<MultivectorComponent, Double>(); //Sliders, ...
        
        HashMap<String, Color> colors = ColorEvaluater.getColors(in);

        LinkedList<Point3d> pointsToTest = new LinkedList<Point3d>();

        HashMap<String, LinkedList<Point3d>> pointsToRender = new HashMap<String, LinkedList<Point3d>>();

        for (Point3d p: pointsToTest) {
            HashMap<MultivectorComponent, Double> values = new HashMap<MultivectorComponent, Double>(globalValues);
            values.put(new MultivectorComponent("_V_X", 0),p.x);
            values.put(new MultivectorComponent("_V_Y", 0),p.y);
            values.put(new MultivectorComponent("_V_Z", 0),p.z);

            Evaluater evaluater = new Evaluater(values);
            in.accept(evaluater);

            HashMap<String, Double> squaredAndSummedValues = new HashMap<String, Double>();
            for (MultivectorComponent mvC: values.keySet()) {
                String name = mvC.getName();
                if (!squaredAndSummedValues.containsKey(name)) 
                    squaredAndSummedValues.put(name, new Double(0));

                double value = values.get(mvC);
                squaredAndSummedValues.put(name, squaredAndSummedValues.get(name) + value*value);
            }
            for (String key: squaredAndSummedValues.keySet()) 
                if (Math.sqrt(squaredAndSummedValues.get(key)) <= EPSILON) {
                    //output point!
                    if (!pointsToRender.containsKey(key))
                        pointsToRender.put(key, new LinkedList<Point3d>());
                    pointsToRender.get(key).add(p);
                }
        }

        RenderingEngine engine = new LwJglRenderingEngine();
        HashMap<String, PointCloud> clouds = new HashMap<String, PointCloud>();
        for (String key: pointsToRender.keySet()) 
            clouds.put(key, new PointCloud(colors.get(key), pointsToRender.get(key)));
        
        engine.render(clouds);
        
        HashSet<OutputFile> out = new HashSet<OutputFile>(); //only for debugging
        out.add(new OutputFile(in.getSource().getName(), in.toString(), Charset.forName("UTF-8")));
        return out;
    }

}
