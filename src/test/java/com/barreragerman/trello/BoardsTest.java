package com.barreragerman.trello;


import com.barreragerman.web.BoardPage;
import com.barreragerman.web.HomePage;
import com.barreragerman.web.SignInPage;
import org.testng.Assert;
import org.testng.annotations.Test;


public class BoardsTest extends BaseTest {


    @Test
    public void shouldDisplayBoardCreatedFromApi() {
        HomePage homePage = new HomePage();
        SignInPage signInPage = new SignInPage();
        String boardName = "My Board Using Karate3";
        homePage.openHomePage();
        BoardPage boardPage = signInPage.login();

        Assert.assertTrue(boardPage.isBoardVisible(boardName),"The board is not present");

    }
}

