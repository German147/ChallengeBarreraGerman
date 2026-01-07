package com.barreragerman.API;


import com.barreragerman.expections.BoardException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.given;

public class TrelloApiClient {
    private static final Logger logger =
            LogManager.getLogger(TrelloApiClient.class);

    static {
        RestAssured.baseURI = ConfigManager.get("trello.baseUrl");
        logger.info("Base URI configured: {}", RestAssured.baseURI);
    }

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
}
