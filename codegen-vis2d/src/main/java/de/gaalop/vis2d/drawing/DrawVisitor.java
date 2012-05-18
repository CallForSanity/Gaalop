package de.gaalop.vis2d.drawing;

/**
 *
 * @author Christian Steinmetz
 */
public interface DrawVisitor {

    public void visitCircle2d(Circle2d circle2d);

    public void visitGerade2d(Gerade2d gerade2d);
    
    public void visitPoint2d(Point2d point2d);

    public void visitPointpair2d(Pointpair2d pointpair2d);

}
