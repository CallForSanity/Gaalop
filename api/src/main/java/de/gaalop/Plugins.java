package de.gaalop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * This class can be used to retrieve all registered Gaalop services.
 *
 * Please note that this class can only find plugins that have been loaded with the same
 * class loader as this file.
 *
 * @see java.util.ServiceLoader How finding the plugins is facilitated.
 */
public final class Plugins {

    private static Log log = LogFactory.getLog(Plugins.class);

    private static Set<CodeParserPlugin> codeParserPlugins;

    private static Set<AlgebraStrategyPlugin> algebraStrategyPlugins;

    private static Set<CodeGeneratorPlugin> codeGeneratorPlugins;

    private static Set<OptimizationStrategyPlugin> optimizationStrategyPlugins;

    static {
        codeParserPlugins = loadServices(CodeParserPlugin.class);
        algebraStrategyPlugins = loadServices(AlgebraStrategyPlugin.class);
        codeGeneratorPlugins = loadServices(CodeGeneratorPlugin.class);
        optimizationStrategyPlugins = loadServices(OptimizationStrategyPlugin.class);
    }

    /**
     * Loads services of a given class and returns their instances as a Set.
     *
     * @param clazz The service class to be loaded. Must equal T.
     * @param <T>   The class of the services to load.
     * @return A Set containing all loaded services of the requested type.
     */
    private static <T> Set<T> loadServices(Class<T> clazz) {
        Set<T> services = new HashSet<T>();

        log.debug("Loading services of type " + clazz);

        ServiceLoader<T> loader = ServiceLoader.load(clazz, Plugins.class.getClassLoader());
        for (T service : loader) {
            log.debug("Loaded service: " + service.getClass());
            services.add(service);
        }

        return services;
    }

    private Plugins() {
    }

    /**
     * Gets the available code parser plugins.
     *
     * @return An unmodifiable set that contains all registered code parser plugins.
     */
    public static Set<CodeParserPlugin> getCodeParserPlugins() {
        return Collections.unmodifiableSet(codeParserPlugins);
    }

    /**
     * Gets the available code generator plugins.
     * @return An unmodifiable set that contains all registered code generator plugins.
     */
    public static Set<CodeGeneratorPlugin> getCodeGeneratorPlugins() {
        return Collections.unmodifiableSet(codeGeneratorPlugins);
    }

    /**
     * Gets the available optimization strategy plugins.
     * @return An unmodifiable set of optimization strategy plugins.
     */
    public static Set<OptimizationStrategyPlugin> getOptimizationStrategyPlugins() {
        return Collections.unmodifiableSet(optimizationStrategyPlugins);
    }

    /**
     * Gets the available algebra strategy plugins.
     * @return An unmodifiable set of algebra strategy plugins.
     */
    public static Set<AlgebraStrategyPlugin> getAlgebraStrategyPlugins() {
        return Collections.unmodifiableSet(algebraStrategyPlugins);
    }

}
