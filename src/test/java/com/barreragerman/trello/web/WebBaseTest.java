package com.barreragerman.trello.web;


import com.barreragerman.listeners.TestListener;
import com.barreragerman.web.DriverFactory;
import io.qameta.allure.testng.AllureTestNg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;

@Listeners({ AllureTestNg.class, TestListener.class })
public abstract class WebBaseTest {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    @Parameters("browser")
    @BeforeClass(alwaysRun = true)
    public void setUp(@Optional("chrome") String browser) {

        logger.info("Initializing WebDriver");
        logger.info(">>> Browser parameter received from TestNG = {}", browser);

        if (browser == null || browser.isBlank()) {
            browser = "chrome";
            logger.warn("Browser param was null/blank. Defaulting to CHROME.");
        }

        DriverFactory.initDriver(browser);
    }


    @AfterClass(alwaysRun = true)
    public void tearDown() {
        logger.info("Quitting WebDriver");
        DriverFactory.quitDriver();
    }
}
