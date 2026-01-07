package com.barreragerman.web;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SignInPage extends BasePage {

    public SignInPage() {
        super();

    }

    @FindBy(id = "username-uid1")
    private WebElement userName;
    @FindBy(id = "password")
    private WebElement passwordField;
    @FindBy(id = "login-submit")
    private WebElement continueBtn;
    @FindBy(id = "mfa-promote-dismiss")
    private WebElement dismissTwoStepVerificationBtn;

    public void setUserName() {
        wait.until(ExpectedConditions.visibilityOf(userName))
                .sendKeys("barreragerman27@gmail.com");
    }

    private void clickContinue_LoginBtn() {
        wait.until(ExpectedConditions.visibilityOf(continueBtn)).click();
    }

    private void setPasswordField() {
        wait.until(ExpectedConditions.visibilityOf(passwordField))
                .sendKeys("PinApp2026!");
    }

    private void clickDismissTwoStepVerificationBtn() {
        try {
            wait.until(ExpectedConditions.visibilityOf(dismissTwoStepVerificationBtn));
            dismissTwoStepVerificationBtn.click();
        } catch (TimeoutException e) {
            System.out.println("Two-step verification popup no apareció, continuando...");
        }
    }

    public BoardPage login() {
        setUserName();
        clickContinue_LoginBtn();
        setPasswordField();
        clickContinue_LoginBtn();
        clickDismissTwoStepVerificationBtn();
        return new BoardPage();
    }
}
