package com.barreragerman.trello.mobile;

import com.barreragerman.listeners.TestListener;
import com.barreragerman.mobile.MobileDriverFactory;
import io.qameta.allure.testng.AllureTestNg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners({ AllureTestNg.class, TestListener.class })
public abstract class MobileBaseTest {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        logger.info("Starting Mobile Test");
        MobileDriverFactory.initDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        logger.info("Finishing Mobile Test");
        MobileDriverFactory.quitDriver();
    }
}
