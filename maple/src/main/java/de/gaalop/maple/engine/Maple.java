package de.gaalop.maple.engine;

import de.gaalop.maple.engine.win32.Win32MapleFinder;

import java.io.File;
import java.io.FileNotFoundException;

public final class Maple {

    private static MapleEngine engine;

    private Maple() {
    }

    /**
     * This method initializes the Maple engine.
     *
     * @param mapleJavaLibraries   The absolute path to the directory that contains the OpenMaple
     *                             JAR files.
     * @param mapleNativeLibraries The absolute path to the directory that contains the Maple
     *                             binary files.
     * @throws IllegalArgumentException If the given Maple path is invalid and the underlying engine
     *                                  could not be initialized.
     * @throws IllegalStateException    If this method has been called before successfully.
     */
    public static synchronized void initialize(File mapleJavaLibraries,
                                               File mapleNativeLibraries) {
        if (isInitialized()) {
            throw new IllegalStateException(
                    "The Maple engine has already been initialized.");
        }

        try {
            // Create new Maple class loader for the given Maple path
            MapleClassLoader classLoader = new MapleClassLoader(mapleJavaLibraries,
                    mapleNativeLibraries);

            // Check for availability of core classes
            engine = new MapleEngineImpl(classLoader);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to initialize Maple.", e);
        }
    }

    public static synchronized boolean isInitialized() {
        return (engine != null);
    }

    public static MapleEngine getEngine() {
        if (!isInitialized()) {
            throw new IllegalStateException(
                    "This factory must be initialized before use.");
        }

        return engine;
    }

    /**
     * Tries to find the Maple installation by looking for it in the following
     * order:
     * <ol>
     * <li>MAPLE environment variable</li>
     * <li>On the PATH environment variable</li>
     * <li>In the Windows registry if available</li>
     * </ol>
     *
     * @return The path where a maple installation was found.
     * @throws FileNotFoundException If no Maple installation could be found.
     */
    public static String findMaplePath() throws FileNotFoundException {
        Win32MapleFinder mapleFinder = new Win32MapleFinder();
        return mapleFinder.getMaplePathFromRegistry();
    }
}
