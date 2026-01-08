package com.barreragerman;

import java.io.InputStream;
import java.util.Properties;

/**
 * Centralized configuration manager for the automation framework.
 *
 * <p>This class is responsible for loading and providing access to configuration
 * values such as:</p>
 * <ul>
 *     <li>API base URLs</li>
 *     <li>Authentication tokens and credentials</li>
 *     <li>Browser selection</li>
 * </ul>
 *
 * <p>Configuration is loaded from a {@code config.properties} file located
 * in the classpath. This file is typically excluded from version control
 * to avoid leaking sensitive information.</p>
 *
 * <p>The class follows a static utility pattern and cannot be instantiated.</p>
 */
public class ConfigManager {

    /**
     * Holds all configuration key-value pairs loaded from config.properties.
     */
    private static final Properties properties = new Properties();

    /**
     * Static initialization block that loads the configuration file once
     * when the class is loaded.
     *
     * <p>This ensures that configuration is available globally and avoids
     * repeated file access during test execution.</p>
     *
     * @throws RuntimeException if the configuration file cannot be loaded
     */
    static {
        try (InputStream input =
                     ConfigManager.class.getClassLoader()
                             .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException("config.properties file not found in classpath");
            }

            properties.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Could not load config.properties", e);
        }
    }

    /**
     * Retrieves a configuration value by key.
     *
     * <p>This method is typically used for accessing API configuration,
     * credentials and general framework settings.</p>
     *
     * @param key configuration property key
     * @return value associated with the given key, or null if not found
     */
    public static String get(String key) {
        return properties.getProperty(key);
    }

    /**
     * Retrieves browser configuration, prioritizing system properties over
     * values defined in config.properties.
     *
     * <p>This allows overriding the browser from command line or CI pipelines
     * using JVM parameters:</p>
     *
     * <pre>
     * mvn test -Dbrowser=firefox
     * </pre>
     *
     * <p>If no system property is provided, the value from config.properties
     * is used as fallback.</p>
     *
     * @param browser configuration key (usually "browser")
     * @return resolved browser value
     */
    public static String getBrowser(String browser) {

        String systemValue = System.getProperty(browser);
        if (systemValue != null) {
            return systemValue;
        }

        return properties.getProperty(browser);
    }
}
