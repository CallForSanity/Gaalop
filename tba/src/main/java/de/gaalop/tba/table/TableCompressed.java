package de.gaalop.tba.table;

import de.gaalop.tba.table.BitIO.AbsBitWriter;
import de.gaalop.tba.table.BitIO.AbsBitReader;
import de.gaalop.tba.BladeRef;
import de.gaalop.tba.IMultTable;
import de.gaalop.tba.Multivector;
import de.gaalop.tba.table.BitIO.BitReader;
import de.gaalop.tba.table.BitIO.BitWriter;
import de.gaalop.tba.table.BitIO.SimpleBitReader;
import de.gaalop.tba.table.BitIO.SimpleBitWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
            int bitCount = in.readByte();

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

    @Override
    public void writeToFile(int bladeCount, int dimension, IMultTable innerTable, IMultTable outerTable, IMultTable geoTable, OutputStream outputStream) {
        try {
            AbsBitWriter w = new SimpleBitWriter();

            int bitCount = 32;
            File tempFile = File.createTempFile("TableCreator", "txt");
            DataOutputStream out = new DataOutputStream(new FileOutputStream(tempFile));

            w.setDataOutputStream(out);

            int maxNumber = 0;

            for (int i=0;i<bladeCount;i++)
                for (int j=0;j<bladeCount;j++) {
                    Multivector innerM = innerTable.getProduct(i, j);
                    Multivector outerM = outerTable.getProduct(i, j);
                    Multivector geoM = geoTable.getProduct(i, j);

                    maxNumber = Math.max(maxNumber, innerM.getBlades().size());
                    maxNumber = Math.max(maxNumber, outerM.getBlades().size());
                    maxNumber = Math.max(maxNumber, geoM.getBlades().size());

                    writeMultivector(innerM, dimension, w, bitCount);
                    writeMultivector(outerM, dimension, w, bitCount);
                    writeMultivector(geoM, dimension, w, bitCount);
                }

            w.finish();
            out.close();

            // ===== 

            out = new DataOutputStream(outputStream);

            DataInputStream in = new DataInputStream(new FileInputStream(tempFile));

            AbsBitReader r = new SimpleBitReader();
            r.setDataInputStream(in);


            int number = 2;
            int bitCount2 = 1;
            while (number < maxNumber+1) {
                bitCount2++;
                number *= 2;
            }


            out.writeByte(1); //format
            out.writeByte(dimension); //dimension
            out.writeByte(bitCount2);

            writer.setDataOutputStream(out);

           for (int i=0;i<bladeCount;i++)
                for (int j=0;j<bladeCount;j++) {
                    writeMultivector(readMultivector(r, dimension, bitCount), dimension, writer, bitCount2);
                    writeMultivector(readMultivector(r, dimension, bitCount), dimension, writer, bitCount2);
                    writeMultivector(readMultivector(r, dimension, bitCount), dimension, writer, bitCount2);
                }

            writer.finish();
            in.close();
            tempFile.delete();

        } catch (IOException ex) {
            Logger.getLogger(TableCompressed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
