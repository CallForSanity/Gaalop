package de.gaalop.vis2d.drawing;

import de.gaalop.vis2d.Stretch2d;

/**
 *
 * @author Christian Steinmetz
 */
public interface DrawVisitor {

    public void visitCircle2d(Circle2d circle2d);

    public void visitGerade2d(Gerade2d gerade2d);
    
    public void visitPoint2d(Point2d point2d);

    public void visitPointpair2d(Pointpair2d pointpair2d);
    
    public void drawKOS();

    public void visitText2d(Text2d text2d);

    public void visitStretch2d(Stretch2d stretch2d);

}
