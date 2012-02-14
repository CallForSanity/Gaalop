package de.gaalop.visualizer.engines.lwjgl;

import de.gaalop.visualizer.Point3d;
import de.gaalop.visualizer.PointCloud;
import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedList;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

/**
 * Implements a simple drawing of the points
 * @author Christian Steinmetz
 */
public class SimpleLwJglRenderingEngine extends LwJglRenderingEngine {

    public SimpleLwJglRenderingEngine(String lwJglNativePath) {
        super(lwJglNativePath);
    }
    
    
    
    public static void main(String[] args) {
        LwJglRenderingEngine engine = new SimpleLwJglRenderingEngine("/usr/lib/jni/");
        HashMap<String, PointCloud> clouds = new HashMap<String, PointCloud>();
        LinkedList<Point3d> points = new LinkedList<Point3d>();
        points.add(new Point3d(0,0,0));
        points.add(new Point3d(1,0,0));
        points.add(new Point3d(0,1,0));
        points.add(new Point3d(0,0,1));
        clouds.put("1", new PointCloud(Color.GREEN, points));

        LinkedList<Point3d> points2 = new LinkedList<Point3d>();
        points2.add(new Point3d(0.5,0,0));
        points2.add(new Point3d(0,0.5,0));
        points2.add(new Point3d(0,0,0.5));
        clouds.put("2", new PointCloud(Color.RED, points2));

        engine.render(clouds);
    }

   @Override
   public void draw(HashMap<String, PointCloud> clouds) {
       if (clouds == null) return;
       
       Sphere s = new Sphere();
       
       for (String cloud: clouds.keySet()) {
            PointCloud pointCloud = clouds.get(cloud);

            //Use the color
            GL11.glColor4d(pointCloud.color.getRed()/255.0d, pointCloud.color.getGreen()/255.0d, pointCloud.color.getBlue()/255.0d, pointCloud.color.getAlpha()/255.0d);

            for (Point3d p: pointCloud.points) {
                GL11.glPushMatrix();
                GL11.glTranslated(p.x,p.y,p.z);
                s.draw(0.04f, 3, 3);
                GL11.glPopMatrix();
           }

            
       }
    }

}
