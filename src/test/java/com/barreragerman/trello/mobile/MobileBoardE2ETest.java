package com.barreragerman.trello.mobile;



import com.barreragerman.API.Board;
import com.barreragerman.API.TrelloApiClient;
import com.barreragerman.mobile.MobileBoardsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MobileBoardE2ETest extends MobileBaseTest {

    @Test(description = "Validate board created via API is visible in Mobile App")
    public void shouldDisplayBoardCreatedFromApiInMobile() {

        Board board = TrelloApiClient.createBoard();
        logger.info("Validating board [{}] in Mobile App", board.getName());

        MobileBoardsPage boardsPage = new MobileBoardsPage();

        Assert.assertTrue(boardsPage.waitUntilBoardIsVisible(board.getName()),
                "The board name [" + board.getName() + "] was not visible");
    }
}
