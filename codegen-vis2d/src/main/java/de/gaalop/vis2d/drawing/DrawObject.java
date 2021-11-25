package de.gaalop.vis2d.drawing;

import java.awt.Color;

/**
 *
 * @author Christian Steinmetz
 */
public abstract class DrawObject {
    
    public Color color;

    public DrawObject(Color color) {
        this.color = color;
    }
    
    public abstract void accept(DrawVisitor visitor);

}
