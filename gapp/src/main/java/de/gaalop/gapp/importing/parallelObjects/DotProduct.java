package de.gaalop.gapp.importing.parallelObjects;

import de.gaalop.gapp.importing.ParallelVector;
import java.util.LinkedList;

/**
 *
 * @author Christian Steinmetz
 */
public class DotProduct extends ParallelObject {

    private LinkedList<ParallelVector> factors;

    private int width;
    private int height;

    public DotProduct() {
        factors = new LinkedList<ParallelVector>();
        width = 0;
        height = 0;
    }

    @Override
    public Object accept(ParallelObjectVisitor visitor, Object arg) {
        return visitor.visitDotProduct(this, arg);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder((isNegated() ? "!" : "") + "[");
        if (width > 0) {
            for (ParallelVector vector: factors) {
                result.append("(");
                result.append(vector.toString());
                result.append("),");
            }
            result.deleteCharAt(result.length()-1);
        }

        result.append("]");
        return result.toString();
    }

    public void ensure(int row, int col) {
        while (col>=width)
            addCol();
        while (row>=height)
            addRow();
    }

    private void addCol() {
        ParallelVector vector = new ParallelVector();
        for (int y=0;y<height;y++)
           vector.getSlots().add(new Constant(1));

        factors.add(vector);
        width++;
    }

    private void addRow() {
        for (ParallelVector factor: factors)
            factor.getSlots().add(new Constant(1));
        height++;
    }

    public void set(int row, int col, ParallelObject object) {
        ensure(row, col);
        factors.get(col).getSlots().set(row, object);
    }

    @Override
    public boolean isTerminal() {
        return false;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ParallelObject get(int row, int col) {
        return factors.get(col).getSlots().get(row);
    }

    public LinkedList<ParallelVector> getFactors() {
        return factors;
    }


}
