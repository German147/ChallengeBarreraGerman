package com.barreragerman.web;

public class BoardWebFlow {

    public BoardPage loginAndOpenBoards() {
        HomePage homePage = new HomePage();
        homePage.openHomePage();

        SignInPage signInPage = new SignInPage();
        return signInPage.login();
    }
}
