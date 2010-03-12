package de.gaalop.maple.engine;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

/**
 * This class loader is used to include the Maple libraries in the class and
 * library search. In order to work, this class loader requires the path of a
 * Maple installation.
 *
 * @author Sebastian
 */
public class MapleClassLoader extends URLClassLoader {

    private static Log log = LogFactory.getLog(MapleClassLoader.class);

    private static final String EXTERNALCALL_JAR = "externalcall.jar";

    private static final String JOPENMAPLE_JAR = "jopenmaple.jar";

    private final File javaPath;

    private final File libraryPath;

    /**
     * Constructs a new Maple class loader.
     *
     * @param mapleJavaLibraries   The path to the directory that contains the openmaple java
     *                             files.
     * @param mapleNativeLibraries The path to the directory that contains the binary files of
     *                             Maple.
     */
    public MapleClassLoader(File mapleJavaLibraries, File mapleNativeLibraries) {
        // Generate URLs
        super(getJarUrls(mapleJavaLibraries));

        this.javaPath = mapleJavaLibraries;
        this.libraryPath = mapleNativeLibraries;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return super.findClass(name);
    }

    @Override
    protected String findLibrary(String libname) {
        String nativeName = System.mapLibraryName(libname);

        // Search for the library in the binary path of Maple first
        File mapleFile = new File(libraryPath, nativeName);

        log.debug("Mapping " + libname + " to " + mapleFile);

        if (mapleFile.exists() && mapleFile.isFile()) {
            return mapleFile.getAbsolutePath();
        } else {
            return super.findLibrary(libname);
        }
    }

    private static URL[] getJarUrls(File mapleJavaPath) {
        URL[] urls = new URL[2];
        urls[0] = getFileUrl(mapleJavaPath, JOPENMAPLE_JAR);
        urls[1] = getFileUrl(mapleJavaPath, EXTERNALCALL_JAR);
        log.debug("Jar URLs for Maple: " + Arrays.toString(urls));

        return urls;
    }

    /**
     * Computes a URL for a file in the given directory.
     *
     * @param dir
     * @param filename
     * @return
     */
    private static URL getFileUrl(File dir, String filename) {
        File file = new File(dir, filename);
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Unable to compute the URL for " + file);
        }
    }

    public File getJavaPath() {
        return javaPath;
    }

    public File getLibraryPath() {
        return libraryPath;
    }

}
