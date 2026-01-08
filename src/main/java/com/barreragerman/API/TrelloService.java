package com.barreragerman.API;

import com.barreragerman.ConfigManager;
import com.barreragerman.expections.BoardException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.given;

/**
 * Service layer responsible for all interactions with the Trello REST API.
 *
 * <p>This class centralizes backend communication and is used for:</p>
 * <ul>
 *     <li>Test data setup (creating boards)</li>
 *     <li>Backend validation of UI actions</li>
 *     <li>Cleanup of test data after execution</li>
 * </ul>
 *
 * <p>All API calls use credentials and base URL loaded from {@link ConfigManager},
 * allowing seamless execution in both local and CI environments.</p>
 *
 * <p>This class follows a stateless utility pattern and exposes only static methods.</p>
 */
public class TrelloService {

    private static final Logger logger =
            LogManager.getLogger(TrelloService.class);

    /**
     * Static initialization block to configure RestAssured base URI
     * when the service class is loaded.
     *
     * <p>This ensures that all API requests use the correct environment
     * without repeating configuration logic in each method.</p>
     */
    static {
        RestAssured.baseURI = ConfigManager.get("trello.baseUrl");
        logger.info("Base URI configured: {}", RestAssured.baseURI);
    }

    /**
     * Creates a new Trello board using the public Trello API.
     *
     * <p>The board name is generated dynamically to avoid data collisions
     * between tests and allow parallel execution.</p>
     *
     * @return {@link Board} object containing the created board data
     * @throws BoardException if the API does not return HTTP 200
     */
    public static Board createBoard() {

        String boardName = "PinAppBoard-" + System.currentTimeMillis();
        logger.info("Creating board with name: {}", boardName);

        Response response = given()
                .queryParam("key", ConfigManager.get("trello.key"))
                .queryParam("token", ConfigManager.get("trello.token"))
                .queryParam("name", boardName)
                .contentType(ContentType.JSON)
                .when()
                .post("/1/boards")
                .then()
                .extract()
                .response();

        if (response.statusCode() != 200) {
            logger.error("Board creation failed. Body: {}", response.asString());
            throw new BoardException(
                    "Board creation failed. Status: "
                            + response.statusCode()
                            + " Body: "
                            + response.asString()
            );
        }

        Board board = response.as(Board.class);
        logger.info("Board created successfully. ID: {}", board.getId());
        logger.info("Board created successfully. Name: {}", board.getName());
        logger.info("Board created successfully. Url: {}", board.getUrl());

        return board;
    }

    /**
     * Retrieves a Trello board by its unique identifier.
     *
     * <p>This method is used to validate backend persistence after board creation
     * or update operations.</p>
     *
     * @param boardId unique Trello board identifier
     * @return {@link Board} object retrieved from the API
     * @throws BoardException if the board cannot be retrieved successfully
     */
    public static Board getBoardById(String boardId) {

        Response response = given()
                .queryParam("key", ConfigManager.get("trello.key"))
                .queryParam("token", ConfigManager.get("trello.token"))
                .when()
                .get("/1/boards/{id}", boardId)
                .then()
                .extract()
                .response();

        if (response.statusCode() != 200) {
            logger.error("Getting board failed. Body: {}", response.asString());
            throw new BoardException(
                    "Getting board failed. Status: "
                            + response.statusCode()
                            + " Body: "
                            + response.asString()
            );
        }
        return response.as(Board.class);
    }

    /**
     * Deletes an existing Trello board.
     *
     * <p>This method is primarily used for cleanup after tests
     * to avoid polluting the environment with test data.</p>
     *
     * @param boardId unique Trello board identifier
     */
    public static void deleteBoard(String boardId) {

        given()
                .queryParam("key", ConfigManager.get("trello.key"))
                .queryParam("token", ConfigManager.get("trello.token"))
                .when()
                .delete("/1/boards/{id}", boardId)
                .then()
                .statusCode(200);
    }

    /**
     * Checks whether a Trello board exists by verifying API response status.
     *
     * <p>This method is useful for polling strategies when validating
     * asynchronous UI actions affecting backend state.</p>
     *
     * @param boardId unique Trello board identifier
     * @return true if the board exists (HTTP 200), false otherwise
     */
    public static boolean boardExists(String boardId) {

        int statusCode =
                given()
                        .queryParam("key", ConfigManager.get("trello.key"))
                        .queryParam("token", ConfigManager.get("trello.token"))
                        .when()
                        .get("/1/boards/{id}", boardId)
                        .then()
                        .extract()
                        .statusCode();

        return statusCode == 200;
    }

    /**
     * Updates the name of an existing Trello board.
     *
     * <p>This method validates that backend data can be modified
     * and persisted correctly.</p>
     *
     * @param boardId unique Trello board identifier
     * @param newName new board name to be applied
     * @return {@link Board} object containing updated board data
     */
    public static Board updateBoardName(String boardId, String newName) {

        Response rs =
                given()
                        .queryParam("key", ConfigManager.get("trello.key"))
                        .queryParam("token", ConfigManager.get("trello.token"))
                        .queryParam("name", newName)
                        .when()
                        .put("/1/boards/{id}", boardId)
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();

        return rs.as(Board.class);
    }

    /**
     * Retrieves the HTTP status code when querying a board by ID.
     *
     * <p>This method is used mainly for negative testing scenarios,
     * such as validating 400 or 404 responses.</p>
     *
     * @param boardId board identifier (valid or invalid)
     * @return HTTP status code returned by the API
     */
    public static int getBoardStatusCode(String boardId) {

        int statusCode =
                given()
                        .queryParam("key", ConfigManager.get("trello.key"))
                        .queryParam("token", ConfigManager.get("trello.token"))
                        .when()
                        .get("/1/boards/{id}", boardId)
                        .then()
                        .extract()
                        .statusCode();

        return statusCode;
    }

}
