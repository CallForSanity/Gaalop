package de.gaalop.gapp.importing.parallelObjects;

import de.gaalop.gapp.importing.ParallelVector;
import java.util.LinkedList;

/**
 * Represents a dot product
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

    /**
     * Ensures that a cell is accessible.
     * Adds otherwise rows and cols.
     * 
     * @param row The row of the cell
     * @param col The col of the cell
     */
    public void ensure(int row, int col) {
        while (col>=width)
            addCol();
        while (row>=height)
            addRow();
    }

    /**
     * Adds a column, and fills the new column with constants of the value "1"
     */
    private void addCol() {
        ParallelVector vector = new ParallelVector();
        for (int y=0;y<height;y++)
           vector.getSlots().add(new Constant(1));

        factors.add(vector);
        width++;
    }

    /**
     * Adds a row, and fills the new row with constants of the value "1"
     */
    private void addRow() {
        for (ParallelVector factor: factors)
            factor.getSlots().add(new Constant(1));
        height++;
    }

    /**
     * Returns a ParallelObject from a cell
     * @param row The row
     * @param col The column
     * @returns The ParallelObject
     */
    public ParallelObject get(int row, int col) {
        return factors.get(col).getSlots().get(row);
    }

    /**
     * Sets a ParallelObject on a cell
     * @param row The row
     * @param col The column
     * @param object The ParallelObject to set
     */
    public void set(int row, int col, ParallelObject object) {
        ensure(row, col);
        factors.get(col).getSlots().set(row, object);
    }

    @Override
    public boolean isTerminal() {
        return false;
    }

    /**
     * Returns the width, i.e. the number of factors in the dot product
     * @return The width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height, i.e. the number of slots in a factor of the dot products
     * @return The height
     */
    public int getHeight() {
        return height;
    }

    public LinkedList<ParallelVector> getFactors() {
        return factors;
    }

    /**
     * Computes the witdh and the height
     * and stores them in the attributes
     */
    public void computeWidthAndHeight() {
        width = factors.size();
        if (width >=1)
            height = factors.getFirst().getSlots().size();
        else
            height = 0;
    }

    public void setFactors(LinkedList<ParallelVector> factors) {
        this.factors = factors;
    }

    
}
