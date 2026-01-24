package com.barreragerman.web;

import com.barreragerman.ConfigManager;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SignInPage extends BasePage {

    String realUsername = ConfigManager.get("trello.username");
    String realPassword = ConfigManager.get("trello.password");

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
    @FindBy(css = ".error-message")
    private WebElement errorMessageLabel;

    public void setUserName() {
        wait.until(ExpectedConditions.visibilityOf(userName))
                .sendKeys(realUsername);
    }

    private void clickContinue_LoginBtn() {
        wait.until(ExpectedConditions.visibilityOf(continueBtn)).click();
    }

    private void setPasswordField() {
        wait.until(ExpectedConditions.visibilityOf(passwordField))
                .sendKeys(realPassword);
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
    public BoardPage inValidLogin(String dataProviderUsername,String dataProviderPassword){
        wait.until(ExpectedConditions.visibilityOf(userName))
                .sendKeys(dataProviderUsername);
        clickContinue_LoginBtn();
        wait.until(ExpectedConditions.visibilityOf(passwordField))
                .sendKeys(dataProviderPassword);
        clickContinue_LoginBtn();
        return new BoardPage();
    }
    public String getErrorMessage() {
        return errorMessageLabel.getText().trim();
    }
    public boolean isErrorVisible() {
        return errorMessageLabel.isDisplayed();
    }
}
