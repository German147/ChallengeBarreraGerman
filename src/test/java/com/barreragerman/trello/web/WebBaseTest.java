package com.barreragerman.trello.web;


import com.barreragerman.listeners.TestListener;
import com.barreragerman.web.DriverFactory;
import io.qameta.allure.testng.AllureTestNg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners({ AllureTestNg.class, TestListener.class })
public abstract class BaseTest {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        logger.info("Initializing WebDriver");
        DriverFactory.initDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        logger.info("Quitting WebDriver");
        DriverFactory.quitDriver();
    }
}
