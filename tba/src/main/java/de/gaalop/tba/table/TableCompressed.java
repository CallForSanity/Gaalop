package de.gaalop.tba.table;

import de.gaalop.tba.table.BitIO.AbsBitWriter;
import de.gaalop.tba.table.BitIO.AbsBitReader;
import de.gaalop.tba.BladeRef;
import de.gaalop.tba.IMultTable;
import de.gaalop.tba.Multivector;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Stores the product tables in a compressed format
 * @author christian
 */
public class TableCompressed implements TableReaderIO {

    private AbsBitReader reader;
    private AbsBitWriter writer;

    public TableCompressed(AbsBitReader reader, AbsBitWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public void readFromFile(InputStream filestream, IMultTable innerTable, IMultTable outerTable, IMultTable geoTable) {
        try {
            DataInputStream in = new DataInputStream(filestream);
            byte format = in.readByte();
            if (format != 1) {
                System.err.println("The format is not the compressed format - Stop to read in");
                in.close();
            }
            int dimension = in.readByte();
            int bladeCount = (int) Math.pow(2,dimension);
            int bitCount = in.readInt();

            reader.setDataInputStream(in);

           for (int i=0;i<bladeCount;i++)
                for (int j=0;j<bladeCount;j++) {
                    innerTable.setProduct(i, j, readMultivector(reader, dimension, bitCount));
                    outerTable.setProduct(i, j, readMultivector(reader, dimension, bitCount));
                    geoTable.setProduct(i, j, readMultivector(reader, dimension, bitCount));
                }

            in.close();
        } catch (IOException ex) {
            Logger.getLogger(TableCompressed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Reads a multivector
     * @param in The reader to be used
     * @param dimension The dimension of the algebra
     * @param bitCount The bit count
     * @return The read multivector
     * @throws IOException
     */
    private Multivector readMultivector(AbsBitReader in, int dimension, int bitCount) throws IOException {
        Multivector result = new Multivector();
        int size = in.read(bitCount);
        for (int i=0;i<size;i++) {
            int prefactor = in.read(1);
            int index = in.read(dimension);
            result.addBlade(new BladeRef((prefactor == 1) ? (byte) -1: (byte) 1, index));
        }
        return result;
    }

    /**
     * Writes a multivector
     * @param product The multivector to be written
     * @param dimension The dimension of the algebra
     * @param out The writer to be used
     * @param bitCount The bit count
     * @throws IOException
     */
    private void writeMultivector(Multivector product, int dimension, AbsBitWriter out, int bitCount) throws IOException {
        int size = product.getBlades().size();
        out.write(size, bitCount);
        for (BladeRef bR: product.getBlades()) {
            out.write((bR.getPrefactor() < 0) ? 1 : 0, 1);
            out.write(bR.getIndex(), dimension);
        }
    }

    /**
     * Determines the maximum number of summands
     * @param bladeCount The number of blades
     * @param innerTable The inner product table
     * @param outerTable The outer product table
     * @param geoTable The geometric product table
     * @return The maximum number of summands
     */
    private int getMaximalNumberOfSummands(int bladeCount, IMultTable innerTable, IMultTable outerTable, IMultTable geoTable) {
        int maxNumber = 0;
        for (int i=0;i<bladeCount;i++)
            for (int j=0;j<bladeCount;j++) {
                maxNumber = Math.max(maxNumber,innerTable.getProduct(i, j).getBlades().size());
                maxNumber = Math.max(maxNumber,outerTable.getProduct(i, j).getBlades().size());
                maxNumber = Math.max(maxNumber,geoTable.getProduct(i, j).getBlades().size());
            }
        return maxNumber;
    }

    @Override
    public void writeToFile(int bladeCount, int dimension, IMultTable innerTable, IMultTable outerTable, IMultTable geoTable, OutputStream outputStream) {
        try {
            int maxNumber = getMaximalNumberOfSummands(bladeCount, innerTable, outerTable, geoTable);

            int number = 2;
            int bitCount = 1;
            while (number < maxNumber+1) {
                bitCount++;
                number *= 2;
            }
            
            DataOutputStream out = new DataOutputStream(outputStream);
            out.writeByte(1); //format
            out.writeByte(dimension); //dimension
            out.writeInt(bitCount);

            writer.setDataOutputStream(out);

            for (int i=0;i<bladeCount;i++)
                for (int j=0;j<bladeCount;j++) {
                    writeMultivector(innerTable.getProduct(i, j), dimension, writer, bitCount);
                    writeMultivector(outerTable.getProduct(i, j), dimension, writer, bitCount);
                    writeMultivector(geoTable.getProduct(i, j), dimension, writer, bitCount);
                }

            writer.finish();

            out.close();
        } catch (IOException ex) {
            Logger.getLogger(TableCompressed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
