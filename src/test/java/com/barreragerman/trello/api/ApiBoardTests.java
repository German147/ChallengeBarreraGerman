package com.barreragerman.trello.api;

import com.barreragerman.API.Board;
import com.barreragerman.API.TrelloService;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * API test suite for validating Trello board operations.
 *
 * <p>This class verifies the behavior of Trello REST endpoints related to
 * board management, including:</p>
 * <ul>
 *     <li>Board creation</li>
 *     <li>Board retrieval</li>
 *     <li>Board updates</li>
 *     <li>Negative scenarios for invalid and non-existing resources</li>
 * </ul>
 *
 * <p>These tests provide fast and stable validation of backend functionality
 * and are also used to support UI and E2E tests as data providers.</p>
 */
public class ApiBoardTests {

    /**
     * Validates that a board can be created and later retrieved using the API,
     * and that it is properly deleted afterward.
     *
     * <p>This test validates:</p>
     * <ul>
     *     <li>POST /boards creates a new board</li>
     *     <li>GET /boards/{id} returns the created board</li>
     *     <li>DELETE /boards/{id} removes the board</li>
     * </ul>
     *
     * <p>Cleanup is performed using the API to avoid environment pollution.</p>
     */
    @Test(description = "Create board via API and validate it exists using GET, then delete it",
            groups = { "api", "integration", "regression" })
    public void shouldCreateAndDeleteBoard() {

        // Arrange - create test data
        Board createdBoard = TrelloService.createBoard();

        // Act - retrieve created board
        Board retrievedBoard = TrelloService.getBoardById(createdBoard.getId());

        // Assert - validate board data consistency
        Assert.assertEquals(createdBoard.getName(), retrievedBoard.getName(),
                "Board name mismatch");

        // Act - delete board
        TrelloService.deleteBoard(createdBoard.getId());

        // Assert - validate backend deletion
        boolean exists = TrelloService.boardExists(createdBoard.getId());
        Assert.assertFalse(exists, "Board still exists after deletion");
    }

    /**
     * Validates that a board name can be updated and that the change
     * is persisted in the backend.
     *
     * <p>This test validates:</p>
     * <ul>
     *     <li>PUT /boards/{id} updates the board name</li>
     *     <li>GET /boards/{id} reflects the updated name</li>
     * </ul>
     *
     * <p>Board cleanup is executed at the end of the test.</p>
     */
    @Test(
            description = "Create board via API, update its name and validate the update",
            groups = { "api", "integration", "regression" }
    )
    public void shouldUpdateBoardName() {

        // Arrange - create test board
        Board createdBoard = TrelloService.createBoard();
        String updatedName = createdBoard.getName() + "-UPDATED";

        // Act - update board name
        Board updatedBoard =
                TrelloService.updateBoardName(createdBoard.getId(), updatedName);

        // Act - retrieve board after update
        Board retrievedBoard =
                TrelloService.getBoardById(createdBoard.getId());

        // Assert - validate update response and persistence
        Assert.assertEquals(updatedBoard.getName(), updatedName,
                "Board name was not updated");

        Assert.assertEquals(retrievedBoard.getName(), updatedName,
                "Updated board name was not persisted");

        // Cleanup
        TrelloService.deleteBoard(createdBoard.getId());
    }

    /**
     * Validates that requesting a non-existing board returns HTTP 404.
     *
     * <p>This test ensures that the API properly handles requests
     * for resources that do not exist.</p>
     */
    @Test(description = "Validate API returns 404 when board does not exist",
            groups = { "api", "negative", "regression" }
    )
    public void shouldReturn404ForNonExistingBoard() {

        String fakeBoardId = "64f1c9e8a1b2c3d4e5f67890";

        int statusCode =
                TrelloService.getBoardStatusCode(fakeBoardId);

        Assert.assertEquals(statusCode, 404,
                "Expected 404 for non existing board");
    }

    /**
     * Validates that requesting a board using an invalid ID format
     * returns HTTP 400 (Bad Request).
     *
     * <p>This test validates API input validation behavior.</p>
     */
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
                "Expected 400 for invalid board id format"
        );
    }

}
