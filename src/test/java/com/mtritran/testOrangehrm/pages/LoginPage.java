package com.mtritran.testOrangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By inputUsername = By.name("username");
    private By inputPassword = By.name("password");
    private By btnLogin      = By.cssSelector(".oxd-button");
    private By errorMessage  = By.cssSelector(".oxd-alert-content-text");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public LoginPage enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputUsername)).sendKeys(username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputPassword)).sendKeys(password);
        return this;
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(btnLogin)).click();
    }

    public void loginAs(String user, String pass) {
        enterUsername(user);
        enterPassword(pass);
        clickLogin();
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }
}