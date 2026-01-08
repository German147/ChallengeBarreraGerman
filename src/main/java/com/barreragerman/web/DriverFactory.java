package com.barreragerman.web;

import com.barreragerman.ConfigManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class DriverFactory {

    private static final ThreadLocal<WebDriver> driver =
            new ThreadLocal<>();

    private DriverFactory() {
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void initDriver(String browser) {

        WebDriver webDriver;

        String selectedBrowser =
                browser != null
                        ? browser.toLowerCase()
                        : ConfigManager.get("browser").toLowerCase();

        switch (selectedBrowser) {

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                webDriver = new FirefoxDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                webDriver = new EdgeDriver();
                break;

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                webDriver = new ChromeDriver();
                break;
        }

        webDriver.manage().window().maximize();
        driver.set(webDriver);
    }

    public static void quitDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            webDriver.quit();
            driver.remove();
        }
    }
}

