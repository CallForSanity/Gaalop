package de.gaalop;

import java.io.IOException;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.*;
import java.util.stream.Stream;

public class Main {

    public static final String LIB_FOLDER = "plugins";
 
    /**
     * Starts Gaalop.
     *
     * @param args Main arguments
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        // Create root class loader.
        ClassLoader pluginLoader = getPluginLoader();

        String concatenatedArgs = String.join(" ", args);
        
        String mainClassName = "de.gaalop.gui.Main";
        if (concatenatedArgs.contains("--cli"))
            mainClassName = "de.gaalop.cli.Main";
        
        try {
            Class<?> mainClass = Class.forName(mainClassName, false, pluginLoader);

            Method mainMethod = mainClass.getMethod("main", String[].class);

            if (!Modifier.isStatic(mainMethod.getModifiers())) {
                System.err.println("Main method is not static in " + mainClassName);
            } else {
                mainMethod.invoke(null, new Object[]{args});
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Unable to find " + mainClassName);
            e.printStackTrace();
        } catch (SecurityException e) {
            System.err.println("Unable to access main method in " + mainClassName);
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            System.err.println("Unable to find main method in " + mainClassName);
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Main method does not accept String[] argument in " + mainClassName);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.err.println("Main method needs to be public in " + mainClassName);
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.err.println("Main method threw an exception in " + mainClassName);
            e.printStackTrace();
        }
    }

    private static ClassLoader getPluginLoader() throws URISyntaxException, IOException {
        Path starterDir = arg0().getParent();
        URL[] urls = Stream.concat(
                getJarFiles(starterDir.resolve(LIB_FOLDER)),
                getJarFiles(Paths.get(LIB_FOLDER)))
            .peek(u ->
                System.out.println("Loading plugin: " + u))
            .toArray(URL[]::new);
        return new URLClassLoader(urls);
    }

    private static Path arg0() throws URISyntaxException {
        return Paths.get(
            Main.class
            .getProtectionDomain()
            .getCodeSource()
            . getLocation()
            .toURI());
    }

    /**
     * Compile a list of URLs for all the JAR files in the given folder.
     *
     * @param libFolder Folder to search jars in
     * @return Stream of jar files
     */
    private static Stream<URL> getJarFiles(Path libFolder) throws IOException {
        if (!Files.isDirectory(libFolder)) {
            return Stream.of();
        }

        return Files.list(libFolder)
            .filter(Main::isJar)
            .map(Main::getFileUrl);
    }

    /**
     * Get the URL for a given {@link Path}.
     *
     * @param path Path to convert
     * @return URL for the given path
     */
    private static URL getFileUrl(Path path) {
        try {
            return path.toUri().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Filename cannot be converted to URL: " + path, e);
        }
    }

    /**
     * Returns true if the given File is a JAR file.
     */
    private static boolean isJar(Path file) {
        return file.toString().toLowerCase().endsWith(".jar");
    }

}
