package com.barreragerman.utils;

import com.barreragerman.expections.ScreenShotException;
import com.barreragerman.mobile.MobileDriverFactory;
import com.barreragerman.web.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Lo que hace este screenShot es:
 * ✔ Detecta automáticamente si es Web o Mobile
 * ✔ No rompe si no hay driver
 * ✔ Crea carpeta automáticamente
 * ✔ Timestamp único
 */

public class ScreenshotUtil {

    private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class);

    private static final String SCREENSHOT_FOLDER = "screenshots";

    public static void takeScreenshot(String testName) {

        try {
            WebDriver driver = resolveDriver();

            if (driver == null) {
                logger.warn("No driver available to take screenshot");
                throw new ScreenShotException("No active WebDriver or MobileDriver found");
            }

            File srcFile =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.FILE);

            Files.createDirectories(Path.of(SCREENSHOT_FOLDER));

            String timestamp =
                    LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

            Path destination =
                    Path.of(SCREENSHOT_FOLDER,
                            testName + "_" + timestamp + ".png");

            Files.copy(srcFile.toPath(), destination);

            logger.info("Screenshot saved at: {}", destination.toAbsolutePath());

        } catch (Exception e) {
            logger.error("Failed to capture screenshot", e);
            throw new ScreenShotException("Failed to capture screenshot for test: " + testName);
        }
    }

    private static WebDriver resolveDriver() {
        if (DriverFactory.getDriver() != null) {
            return DriverFactory.getDriver();
        }
        if (MobileDriverFactory.getDriver() != null) {
            return MobileDriverFactory.getDriver();
        }
        return null;
    }
}

