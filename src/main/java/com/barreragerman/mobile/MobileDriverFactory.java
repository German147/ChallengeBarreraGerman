package com.barreragerman.mobile;


import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.time.Duration;

public class MobileDriverFactory {

    private static final Logger logger = LogManager.getLogger(MobileDriverFactory.class);

    private static ThreadLocal<AndroidDriver> driver = new ThreadLocal<>();

    private MobileDriverFactory() {
    }

    public static void initDriver() {
        try {
            logger.info("Initializing Android Driver");

            UiAutomator2Options options = new UiAutomator2Options()
                    .setPlatformName("Android")
                    .setAutomationName("UiAutomator2")
                    .setDeviceName("AndroidDevice")
                    .setNoReset(true)
                    .setAppPackage("com.trello")     // confirm package
                    .setAppActivity("com.trello.home.HomeActivity"); // confirm activity

            AndroidDriver androidDriver =
                    new AndroidDriver(
                            new URL("http://127.0.0.1:4723"),
                            options
                    );

            androidDriver.manage()
                    .timeouts()
                    .implicitlyWait(Duration.ofSeconds(2));

            driver.set(androidDriver);

            logger.info("Android Driver initialized successfully");

        } catch (Exception e) {
            logger.error("Failed to initialize Android Driver", e);
            throw new RuntimeException(e);
        }
    }

    public static AndroidDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            logger.info("Quitting Android Driver");
            driver.get().quit();
            driver.remove();
        }
    }
}

