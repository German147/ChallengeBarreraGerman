package com.barreragerman.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;


public class BoardPage extends BasePage {

    @FindBy(css = "div.pIQ5_g4p0XJopD ")
    private List<WebElement> listOfBoards;

    private List<String> getBoardNames() {
        wait.until(ExpectedConditions.visibilityOfAllElements(listOfBoards));

        return listOfBoards.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .filter(name -> !name.isEmpty())
                .toList();
    }

    public boolean isBoardVisible(String boardName) {
        return getBoardNames()
                .stream()
                .anyMatch(name -> name.equalsIgnoreCase(boardName));
    }

    public boolean waitUntilBoardIsVisible(String boardName) {
        return wait.until(driver -> {
            List<String> boards = getBoardNames();
            return boards.stream()
                    .anyMatch(name -> name.equalsIgnoreCase(boardName));
        });
    }


}
