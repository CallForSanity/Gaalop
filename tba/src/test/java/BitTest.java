import de.gaalop.tba.table.BitIO.AbsBitReader;
import de.gaalop.tba.table.BitIO.AbsBitWriter;
import de.gaalop.tba.table.BitIO.MaxReader;
import de.gaalop.tba.table.BitIO.MaxWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 * Tests the bitwriter/bitReader
 * @author Internet
 */
public class BitTest {
    
    private static final Random random = new Random(17);
    
    public BitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    private LinkedList<Record> list = new LinkedList<Record>();
    
    @Ignore
    public void test() throws IOException {
        for (int i=0;i<100;i++) {
            dummy();
        }
    }
    
    public void dummy() throws IOException {
        fillList();
        
        AbsBitWriter writer = new MaxWriter();
        File tFile = File.createTempFile("BitWriter", ".txt");
        tFile.deleteOnExit();
        DataOutputStream out = new DataOutputStream(new FileOutputStream(tFile));
        writer.setDataOutputStream(out);
        for (Record r: list)
            writer.write(r.data, r.bitCount);
        writer.finish();
        
        AbsBitReader reader = new MaxReader();
        DataInputStream din = new DataInputStream(new FileInputStream(tFile));
        reader.setDataInputStream(din);
        int i=0;
        for (Record r: list) {
            assertEquals(i+"",r.data,reader.read(r.bitCount));
            i++;
        }
        
        
    }
    
    private void fillList() {
        list.clear();
        
        int count = random.nextInt(10000);
        for (int i=0;i<count;i++) {
            int bitCount = random.nextInt(33);
            int data = random.nextInt((int) Math.pow(2, bitCount));
            list.add(new Record(data, bitCount));
        }
        
    }
    
}
