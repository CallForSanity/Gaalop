package de.gaalop.visualizer.engines.lwjgl.recording;

import de.gaalop.visualizer.engines.lwjgl.LwJglRenderingEngine;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * Implements a recorder that outputs a GIF file
 * @author Christian Steinmetz
 */
public class GIFRecorder extends Recorder {
    
    private AnimatedGifEncoder encoder;

    @Override
    public void _addFrame(BufferedImage image, long delay) {
        encoder.setDelay((int) delay);
        encoder.addFrame(image);
    }

    @Override
    public void _finish() {
        encoder.finish();
        System.out.println("Written recording");
    }

    @Override
    protected void _startRecording() {
        try {
            encoder = new AnimatedGifEncoder();
            
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileFilter() {

                @Override
                public boolean accept(File f) {
                    if (f.getName().toLowerCase().endsWith(".gif")) 
                        return true;
                    if (f.isDirectory()) return true;
                    return false;
                }

                @Override
                public String getDescription() {
                    return "GIF files";
                }
            });
            if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                encoder.start(new FileOutputStream(chooser.getSelectedFile()));
                encoder.setDelay(40);
                System.out.println("Started recording");
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LwJglRenderingEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
