package com.barreragerman.trello.web;


import com.barreragerman.API.Board;
import com.barreragerman.API.TrelloService;
import com.barreragerman.web.BoardPage;
import com.barreragerman.web.BoardWebFlow;
import org.testng.Assert;
import org.testng.annotations.Test;


public class WebBoardE2ETest extends WebBaseTest {

    @Test(description = "Validate board created via API is visible in Web UI",
            groups = { "web", "integration", "regression" })
    public void shouldDisplayBoardCreatedFromApi() {
        Board board = TrelloService.createBoard();
        logger.info("Board created: {}", board.getName());
        BoardPage boardsPage = new BoardWebFlow().loginAndOpenBoards();
        Assert.assertTrue(boardsPage.waitUntilBoardIsVisible(board.getName()),
                "Board was NOT visible in Web UI");
    }
}
