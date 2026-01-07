package com.barreragerman.trello.mobile;


import com.barreragerman.mobile.MobileDriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MobileSmokeTest extends MobileBaseTest {

    @Test
    public void shouldLaunchTrelloAppSuccessfully() {
        logger.info("Running Mobile Smoke Test");

        Assert.assertNotNull(
                MobileDriverFactory.getDriver(),
                "Android driver was not initialized"
        );

        logger.info("Trello App launched successfully");
    }
}
