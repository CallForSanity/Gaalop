package de.gaalop.vis2d.drawing;

import java.util.LinkedList;

/**
 *
 * @author Christian Steinmetz
 */
public class Drawing {
    
    public LinkedList<DrawObject> objects = new LinkedList<DrawObject>();
    
    public void draw(DrawVisitor visitor) {
        for (DrawObject obj: objects)
            obj.accept(visitor);
    }

    public void printOut() {
        for (DrawObject obj: objects)
            System.out.println(obj.toString());
    }

}
