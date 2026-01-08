package com.barreragerman.trello.web;


import com.barreragerman.web.BoardPage;
import com.barreragerman.web.HomePage;
import com.barreragerman.web.SignInPage;
import org.testng.Assert;
import org.testng.annotations.Test;


public class WebBoardsTest extends WebBaseTest {


    @Test(description = "Validate a board is visible in Web UI",
            groups = { "web", "smoke" })
    public void shouldDisplayBoardFromUi() {
        HomePage homePage = new HomePage();
        SignInPage signInPage = new SignInPage();
        String boardName = "My Board Using Karate";
        homePage.openHomePage();
        BoardPage boardPage = signInPage.login();

        Assert.assertTrue(boardPage.isBoardVisible(boardName),"The board is not present");

    }
}

