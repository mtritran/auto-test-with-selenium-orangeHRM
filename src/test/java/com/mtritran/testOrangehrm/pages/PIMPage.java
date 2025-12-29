package com.mtritran.testOrangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PIMPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By pimMenu = By.linkText("PIM");
    private By addEmployeeTab = By.xpath("//a[text()='Add Employee']");

    public PIMPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void goToAddEmployeePage() {
        wait.until(ExpectedConditions.elementToBeClickable(pimMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(addEmployeeTab)).click();
    }
}
