package com.barreragerman.trello.api;

import com.barreragerman.API.Board;
import com.barreragerman.API.TrelloService;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApiBoardTests {

    @Test(description = "Create board via API and validate it exists using GET, then delete it",
            groups = { "api", "integration", "regression" })
    public void shouldCreateAndDeleteBoard() {

        // Arrange
        Board createdBoard = TrelloService.createBoard();

        // Act
        Board retrievedBoard = TrelloService.getBoardById(createdBoard.getId());

        // Assert - Board was created correctly
        Assert.assertEquals(createdBoard.getName(), retrievedBoard.getName(), "Board name mismatch");

        // Act - Delete board
        TrelloService.deleteBoard(createdBoard.getId());

        // Assert - Board no longer exists
        boolean exists = TrelloService.boardExists(createdBoard.getId());

        Assert.assertFalse(exists, "Board still exists after deletion");
    }

    @Test(
            description = "Create board via API, update its name and validate the update",
            groups = { "api", "integration", "regression" }
    )
    public void shouldUpdateBoardName() {
        // Arrange
        Board createdBoard = TrelloService.createBoard();

        String updatedName = createdBoard.getName() + "-UPDATED";
        // Act
        Board updatedBoard =
                TrelloService.updateBoardName(createdBoard.getId(), updatedName);
        Board retrievedBoard =
                TrelloService.getBoardById(createdBoard.getId());

        Assert.assertEquals(updatedBoard.getName(), updatedName,
                "Board name was not updated");
        Assert.assertEquals(retrievedBoard.getName(), updatedName,
                "Updated board name was not persisted");

        TrelloService.deleteBoard(createdBoard.getId());
    }


    @Test(description = "Validate API returns 404 when board does not exist",
            groups = { "api", "negative", "regression" }
    )
    public void shouldReturn404ForNonExistingBoard() {
        String fakeBoardId = "64f1c9e8~a1b2c3d4e5f67890";

        int statusCode =
                TrelloService.getBoardStatusCode(fakeBoardId);

        Assert.assertEquals(statusCode, 404,
                "Expected 404 for non existing board");
    }

    @Test(
            description = "Validate API returns 400 for invalid board id format",
            groups = { "api", "negative", "regression" }
    )
    public void shouldReturn400ForInvalidFormatIdTest() {

        String fakeBoardId = "invalid-board-id-123";

        int statusCode =
                TrelloService.getBoardStatusCode(fakeBoardId);

        Assert.assertEquals(
                statusCode,
                400,
                "Expected 400 for non existing board"
        );
    }

}
