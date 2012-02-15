package de.gaalop.visualizer.engines.lwjgl;

import de.gaalop.visualizer.Point3d;
import de.gaalop.visualizer.PointCloud;
import de.gaalop.visualizer.Rendering;
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
    
    public SimpleLwJglRenderingEngine(String lwJglNativePath, Rendering rendering) {
        super(lwJglNativePath, rendering);
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
