package com.barreragerman;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input =
                     ConfigManager.class.getClassLoader()
                             .getResourceAsStream("config.properties")) {

            properties.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Could not load config.properties", e);
        }
    }

    public static String get(String key_or_token) {
        return properties.getProperty(key_or_token);
    }

    public static String getBrowser(String browser) {

        String systemValue = System.getProperty(browser);
        if (systemValue != null) {
            return systemValue;
        }

        return properties.getProperty(browser);
    }
}
