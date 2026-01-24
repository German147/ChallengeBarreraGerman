package com.barreragerman;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input =
                     ConfigManager.class.getClassLoader()
                             .getResourceAsStream("config.properties")) {

            if (input != null) {
                properties.load(input);
            } else {
                System.out.println("config.properties not found, using environment variables only");
            }

        } catch (Exception e) {
            throw new RuntimeException("Could not load config.properties", e);
        }
    }

    // 🔥 MAIN METHOD: ENV > PROPERTIES
    public static String get(String key) {

        String envValue = System.getenv(key.toUpperCase().replace(".", "_"));
        if (envValue != null) {
            return envValue;
        }

        return properties.getProperty(key);
    }

    public static String getBrowser(String browser) {

        String systemValue = System.getProperty(browser);
        if (systemValue != null) {
            return systemValue;
        }

        return get(browser);
    }
}
