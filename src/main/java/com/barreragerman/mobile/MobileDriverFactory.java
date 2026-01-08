package com.barreragerman.mobile;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.time.Duration;

/**
 * Factory class responsible for managing the lifecycle of the AndroidDriver
 * used in mobile automation tests with Appium.
 *
 * <p>This class follows a utility/factory design pattern and provides:</p>
 * <ul>
 *     <li>Centralized driver initialization</li>
 *     <li>Thread-safe driver storage using ThreadLocal</li>
 *     <li>Controlled driver cleanup</li>
 * </ul>
 *
 * <p>Although mobile tests are executed locally only, the use of ThreadLocal
 * allows this implementation to be extended for parallel execution
 * or cloud device providers in the future.</p>
 */
public class MobileDriverFactory {

    private static final Logger logger =
            LogManager.getLogger(MobileDriverFactory.class);

    /**
     * Thread-local storage for AndroidDriver instances.
     *
     * <p>Ensures that each test thread receives its own isolated driver instance,
     * preventing cross-test interference and improving scalability.</p>
     */
    private static final ThreadLocal<AndroidDriver> driver = new ThreadLocal<>();

    /**
     * Private constructor to prevent instantiation.
     *
     * <p>This class is designed to be used only through static methods.</p>
     */
    private MobileDriverFactory() {
    }

    /**
     * Initializes a new AndroidDriver instance and binds it to the current thread.
     *
     * <p>This method configures UiAutomator2 capabilities and connects to the
     * locally running Appium server.</p>
     *
     * <p>Typical usage:
     * called in setup hooks before mobile test execution.</p>
     *
     * @throws RuntimeException if driver initialization fails
     */
    public static void initDriver() {
        try {
            logger.info("Initializing Android Driver");

            UiAutomator2Options options = new UiAutomator2Options()
                    .setPlatformName("Android")
                    .setAutomationName("UiAutomator2")
                    .setDeviceName("AndroidDevice")
                    .setNoReset(true)
                    .setAppPackage("com.trello")
                    .setAppActivity("com.trello.home.HomeActivity");

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

    /**
     * Retrieves the AndroidDriver instance associated with the current thread.
     *
     * @return active {@link AndroidDriver} instance
     */
    public static AndroidDriver getDriver() {
        return driver.get();
    }

    /**
     * Terminates the AndroidDriver instance and removes it from thread-local storage.
     *
     * <p>This method should be called after each test execution to ensure
     * proper resource cleanup and avoid memory leaks.</p>
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            logger.info("Quitting Android Driver");
            driver.get().quit();
            driver.remove();
        }
    }
}
