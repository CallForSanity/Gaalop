package de.gaalop.gui.util;

import de.gaalop.ConfigurationProperty;
import de.gaalop.Plugin;
import de.gaalop.Plugins;
import de.gaalop.gui.PanelPluginSelection;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

/**
 * This utility class helps with the configuration of plugins.
 */
public class PluginConfigurator {

    private final Properties configuration;

    private Log log = LogFactory.getLog(PluginConfigurator.class);

    /**
     * Constructs a new Plugin configurator.
     *
     * @param configuration Configuration options will be read from this properties object.
     *                      A copy of this object will be stored.
     */
    public PluginConfigurator(Properties configuration) {
        this.configuration = configuration;
    }

    /**
     * Constructs a Plugin configurator with no configuration options.
     */
    public PluginConfigurator() {
        configuration = new Properties();
    }

    public void configure(Plugin plugin) {
        log.debug("Configuring " + plugin.getClass());

        Field[] fields = plugin.getClass().getFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ConfigurationProperty.class)) {
                configure(plugin, field);
            }
        }
    }

    private void configure(Plugin plugin, Field field) {
        String propertyKey = getPropertyKey(field);
        String propertyValue = configuration.getProperty(propertyKey);
        log.debug("Configuration for " + propertyKey + " = " + propertyValue);

        if (propertyValue != null) {
            try {
                BeanUtils.setProperty(plugin, field.getName(), propertyValue);
            } catch (IllegalAccessException e) {
                log.error("Unable to configure property " + propertyKey, e);
            } catch (InvocationTargetException e) {
                log.error("Unable to configure property " + propertyKey, e);
            }
        }
    }

    private String getPropertyKey(Field field) {
        Class<?> declaringClass = field.getDeclaringClass();
        String fieldName = field.getName();
        return declaringClass.getName() + "." + fieldName;
    }

    /**
     * Configures all registered Plugins using the currently set configuration options.
     */
    public void configureAll(Observer o) {
        Set<? extends Plugin> plugins;

        plugins = Plugins.getOptimizationStrategyPlugins();
        for (Plugin plugin : plugins) {
            configure(plugin);
            register(plugin, o);
        }

        plugins = Plugins.getAlgebraStrategyPlugins();
        for (Plugin plugin : plugins) {
            configure(plugin);
            register(plugin, o);
        }

        plugins = Plugins.getCodeParserPlugins();
        for (Plugin plugin : plugins) {
            configure(plugin);
            register(plugin, o);
        }

        plugins = Plugins.getCodeGeneratorPlugins();
        for (Plugin plugin : plugins) {
            configure(plugin);
            register(plugin, o);
        }
        
        PanelPluginSelection.lastUsedAlgebra = (String) configuration.get("lastUsedAlgebra");  
        PanelPluginSelection.lastUsedAlgebraRessource = Boolean.parseBoolean((String) configuration.get("lastUsedAlgebraRessource"));
        PanelPluginSelection.lastUsedGenerator = (String) configuration.get("lastUsedGenerator");
        PanelPluginSelection.lastUsedVisualCodeInserter = (String) configuration.get("lastUsedVisualCodeInserter");
        PanelPluginSelection.lastUsedOptimization = (String) configuration.get("lastUsedOptimization");
    }

    private void register(Plugin plugin, Observer o) {
		if (plugin.getClass().getSuperclass() == Observable.class) {
			log.debug("Registering " + o + " as observer of " + plugin.getName() + " plugin");
			((Observable) plugin).addObserver(o);
		}
	}

	/**
     * Iterates over all plugins and reads their current configuration state into the property map.
     * The property map can be retrieved using <code>getConfiguration</code>.
     */
    public void readConfiguration() {
        Set<? extends Plugin> plugins;

        plugins = Plugins.getOptimizationStrategyPlugins();
        for (Plugin plugin : plugins) {
            readConfiguration(plugin);
        }

        plugins = Plugins.getAlgebraStrategyPlugins();
        for (Plugin plugin : plugins) {
            readConfiguration(plugin);
        }

        plugins = Plugins.getCodeParserPlugins();
        for (Plugin plugin : plugins) {
            readConfiguration(plugin);
        }

        plugins = Plugins.getCodeGeneratorPlugins();
        for (Plugin plugin : plugins) {
            readConfiguration(plugin);
        }
        
        configuration.put("lastUsedAlgebra", PanelPluginSelection.lastUsedAlgebra);  
        configuration.put("lastUsedAlgebraRessource", Boolean.toString(PanelPluginSelection.lastUsedAlgebraRessource));
        configuration.put("lastUsedGenerator", PanelPluginSelection.lastUsedGenerator);
        configuration.put("lastUsedVisualCodeInserter", PanelPluginSelection.lastUsedVisualCodeInserter);
        configuration.put("lastUsedOptimization", PanelPluginSelection.lastUsedOptimization);
    }

    private void readConfiguration(Plugin plugin) {
        log.debug("Reading configuration of " + plugin.getClass());

        Set<Field> fields = getAllFields(plugin.getClass());
        for (Field field : fields) {
            if (field.isAnnotationPresent(ConfigurationProperty.class)) {
                readConfiguration(plugin, field);
            }
        }
    }

    private Set<Field> getAllFields(Class<?> clazz) {
        // Get the fields of clazz (without inherited fields)
        Set<Field> fields = new HashSet<Field>(Arrays.asList(clazz.getDeclaredFields()));

        // If clazz inherits from some class, get their fields as well and merge them with ours
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null && !superclass.equals(Object.class)) {
            Set<Field> superFields = getAllFields(superclass);
            fields.addAll(superFields);
        }

        return fields;
    }

    private void readConfiguration(Plugin plugin, Field field) {
        String propertyKey = getPropertyKey(field);

        try {
            String configValue = BeanUtils.getProperty(plugin, field.getName());
            log.debug("Read value for " + propertyKey + " = " + configValue);
            configuration.put(propertyKey, configValue);            
        } catch (IllegalAccessException e) {
            log.error("Unable to read property " + propertyKey, e);
        } catch (InvocationTargetException e) {
            log.error("Unable to read property " + propertyKey, e);
        } catch (NoSuchMethodException e) {
            log.error("No getter is available for " + propertyKey, e);
        }
    }

    public Properties getConfiguration() {
        return configuration;
    }
}
