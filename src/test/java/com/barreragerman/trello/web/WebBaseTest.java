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

    //esta tag es para que se auto-seleccione al ser parallel al venir el llamado de un xml
    @Parameters("browser")
    @BeforeClass(alwaysRun = true)
    public void setUp(@Optional String browser) {
        logger.info("Initializing WebDriver");
        logger.info(">>> Browser parameter received from TestNG = {}", browser);
        DriverFactory.initDriver(browser);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        logger.info("Quitting WebDriver");
        DriverFactory.quitDriver();
    }
}
