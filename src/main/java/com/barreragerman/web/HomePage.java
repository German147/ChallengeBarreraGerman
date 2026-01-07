package com.barreragerman.web;


public class HomePage extends BasePage {

    public void openHomePage(){
      DriverFactory.getDriver()
               .get("https://trello.com/login");
    }



}