package com.barreragerman.trello.mobile;


import com.barreragerman.mobile.MobileLoginScreen;
import org.testng.annotations.Test;

public class MobileLoginTest extends MobileBaseTest {

    @Test
    public void shouldTapLoginButton() {
        logger.info("Validating mobile login button");

        MobileLoginScreen loginPage = new MobileLoginScreen();
        loginPage.tapLogin();

        logger.info("Login button tapped successfully");
    }
}
