package com.mtritran.testOrangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmployeeListPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public EmployeeListPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public boolean isLoaded() {
        try {
            WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h5[normalize-space()='Employee Information']")
            ));
            boolean isDisplayed = title.isDisplayed();

            if (isDisplayed) {
                System.out.println(AddEmployeePage.logCounter + ". Page Navigation: Redirected to Employee List Page successfully.");
                AddEmployeePage.logCounter++;
            }

            return isDisplayed;
        } catch (Exception e) {
            return false;
        }
    }
}