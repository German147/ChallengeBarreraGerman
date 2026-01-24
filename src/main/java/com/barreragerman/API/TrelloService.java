package com.barreragerman.API;


import com.barreragerman.ConfigManager;
import com.barreragerman.expections.BoardException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.given;

public class TrelloService {
    private static final Logger logger =
            LogManager.getLogger(TrelloService.class);

    private static void configure() {
        RestAssured.baseURI = ConfigManager.get("trello.baseUrl");
        logger.info("Base URI configured: {}", RestAssured.baseURI);
    }


    public static Board createBoard() {
        configure();
        String boardName = "PinAppBoardGermanAPI-" + System.currentTimeMillis();
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

    public static Board getBoardById(String boardId) {
        configure();
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


    public static void deleteBoard(String boardId) {
        configure();
        given()
                .queryParam("key", ConfigManager.get("trello.key"))
                .queryParam("token", ConfigManager.get("trello.token"))
                .when()
                .delete("/1/boards/{id}", boardId)
                .then()
                .statusCode(200);
    }

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

    public static Board updateBoardName(String boardId, String newName) {
    configure();
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

    public static int getBoardStatusCode(String boardId) {
      configure();
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
