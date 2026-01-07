package com.barreragerman.mobile;


import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MobileBoardsPage {

    private final WebDriverWait wait;

    public MobileBoardsPage() {
        PageFactory.initElements(
                new AppiumFieldDecorator(
                        MobileDriverFactory.getDriver(),
                        Duration.ofSeconds(10)
                ),
                this
        );

        this.wait = new WebDriverWait(
                MobileDriverFactory.getDriver(),
                Duration.ofSeconds(20)
        );
    }

    protected final Logger logger = LogManager.getLogger(this.getClass());

    // Contenedor principal de boards
    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"BoardsLazyGrid\")")
    private WebElement boardsGrid;

    /**
     * Scrolls until the board text appears in the screen.
     */
    private void scrollToBoard(String boardName) {
        logger.info("Scrolling to board: {}", boardName);
        String uiScrollable =
                "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().textContains(\""
                        + boardName + "\"))";

        MobileDriverFactory.getDriver()
                .findElement(AppiumBy.androidUIAutomator(uiScrollable));
    }

    /**
     * Returns true when a board with the given name appears in the visible list.
     */
    public boolean waitUntilBoardIsVisible(String boardName) {
        scrollToBoard(boardName);
        logger.info("Waiting until board [{}] is visible in list", boardName);

        return wait.until(driver -> {

            List<WebElement> visibleBoards =
                    boardsGrid.findElements(
                            By.className("android.widget.TextView")
                    );

            return visibleBoards.stream()
                    .map(WebElement::getText)
                    .map(String::trim)
                    .anyMatch(text ->
                            text.equalsIgnoreCase(boardName)
                    );
        });
    }
}



