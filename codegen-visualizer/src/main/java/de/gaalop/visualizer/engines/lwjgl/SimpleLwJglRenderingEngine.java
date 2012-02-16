package de.gaalop.visualizer.engines.lwjgl;

import de.gaalop.visualizer.Point3d;
import de.gaalop.visualizer.PointCloud;
import de.gaalop.visualizer.Rendering;
import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
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
   public void draw(HashMap<String, PointCloud> clouds, HashSet<String> visibleObjects) {
       if (clouds == null) return;
       
       //draw axes
       GL11.glBegin(GL11.GL_LINES);
       GL11.glColor4d(1,0,0,0);//Red
       GL11.glVertex3d(0, 0, 0);
       GL11.glVertex3d(1, 0, 0);
       GL11.glColor4d(0,1,0,0);//Green
       GL11.glVertex3d(0, 0, 0);
       GL11.glVertex3d(0, 1, 0);
       GL11.glColor4d(0,0,1,0);//Blue
       GL11.glVertex3d(0, 0, 0);
       GL11.glVertex3d(0, 0, 1);
       GL11.glEnd();
       
       
       
              Sphere s = new Sphere();
       
       for (String cloud: clouds.keySet()) 
           if (visibleObjects.contains(cloud))
           {
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
