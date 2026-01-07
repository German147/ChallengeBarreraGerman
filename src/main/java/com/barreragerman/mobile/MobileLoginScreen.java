package com.barreragerman.mobile;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class MobileLoginScreen {

    public MobileLoginScreen() {
        PageFactory.initElements(
                new AppiumFieldDecorator(
                        MobileDriverFactory.getDriver(),
                        Duration.ofSeconds(10)
                ),
                this
        );
    }

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Iniciar sesión\")")
    private WebElement loginBtn;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.trello:id/google_auth\")")
    private WebElement googleLoginBtn;

    public void tapLogin() {
        loginBtn.click();
    }

    public void tapGoogleLoginBtn() {
        googleLoginBtn.click();
    }
}
