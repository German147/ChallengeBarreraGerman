package com.barreragerman.trello;


import com.barreragerman.API.Board;
import com.barreragerman.API.TrelloApiClient;
import com.barreragerman.web.BoardPage;
import com.barreragerman.web.BoardWebFlow;
import org.testng.Assert;
import org.testng.annotations.Test;


public class BoardE2ETest extends BaseTest {

    @Test(description = "Validate board created via API is visible in Web UI")
    public void shouldDisplayBoardCreatedFromApi() {
        Board board = TrelloApiClient.createBoard();
        logger.info("Board created: {}", board.getName());
        BoardPage boardsPage = new BoardWebFlow().loginAndOpenBoards();
        Assert.assertTrue(boardsPage.waitUntilBoardIsVisible(board.getName()),
                "Board was NOT visible in Web UI");
    }
}
