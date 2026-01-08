package com.barreragerman.trello.mobile;


import com.barreragerman.mobile.MobileLoginScreen;
import org.testng.annotations.Test;

public class MobileLoginTest extends MobileBaseTest {

    @Test(description = "Validate button is clickable in Mobile App",
            groups = { "mobile","smoke","negative"})
    public void shouldTapLoginButton() {

        logger.info("Validating mobile login button");

        MobileLoginScreen loginPage = new MobileLoginScreen();

        loginPage.tapLogin();

        logger.info("Login button tapped successfully");
    }
}
