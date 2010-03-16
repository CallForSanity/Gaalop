package de.gaalop;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final String LIB_FOLDER = "plugins";

    public static final String MAIN_CLASS = "de.gaalop.gui.Main";

    /**
     * Starts Gaalop.
     *
     * @param args
     */
    public static void main(String[] args) {
        // Create root class loader.
        File libFolder = new File(LIB_FOLDER);
        URL[] urls = getJarList(libFolder);
        ClassLoader loader = new URLClassLoader(urls);

        try {
            Class<?> mainClass = Class.forName(MAIN_CLASS, false, loader);

            Method mainMethod = mainClass.getMethod("main", String[].class);

            if (!Modifier.isStatic(mainMethod.getModifiers())) {
                System.err.println("Main method is not static in " + MAIN_CLASS);
            } else {
                mainMethod.invoke(null, new Object[]{args});
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Unable to find " + MAIN_CLASS);
            e.printStackTrace();
        } catch (SecurityException e) {
            System.err.println("Unable to access main method in " + MAIN_CLASS);
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            System.err.println("Unable to find main method in " + MAIN_CLASS);
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Main method does not accept String[] argument in " + MAIN_CLASS);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.err.println("Main method needs to be public in " + MAIN_CLASS);
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.err.println("Main method threw an exception in " + MAIN_CLASS);
            e.printStackTrace();
        }
    }

    /**
     * Compile a list of URLs for all the JAR files in the given folder.
     *
     * @param libFolder
     * @return
     */
    private static URL[] getJarList(File libFolder) {
        List<URL> urls = new ArrayList<URL>();

        if (!libFolder.isDirectory()) {
            throw new RuntimeException(libFolder.getAbsolutePath() + " is not a directory.");
        }

        for (File file : libFolder.listFiles()) {
            if (isJar(file)) {
                URL url = getFileUrl(file);
                urls.add(url);
            }
        }

        // Convert to array
        URL[] result = new URL[urls.size()];
        return urls.toArray(result);
    }

    /**
     * Get the URL for a given file.
     *
     * @param file
     * @return
     */
    private static URL getFileUrl(File file) {
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Filename cannot be converted to URL: " + file.getAbsolutePath(), e);
        }
    }

    /**
     * Returns true if the given File is a JAR file.
     */
    private static boolean isJar(File file) {
        return file.getName().toLowerCase().endsWith(".jar");
    }

}
