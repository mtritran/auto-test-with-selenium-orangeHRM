package com.mtritran.testOrangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddEmployeePage {

    private WebDriver driver;
    private WebDriverWait wait;

    public static int logCounter = 1;

    // --- LOCATORS ---
    private By firstNameInput = By.name("firstName");
    private By middleNameInput = By.name("middleName");
    private By lastNameInput = By.name("lastName");
    private By employeeIdInput = By.xpath("//label[text()='Employee Id']/../following-sibling::div//input");

    private By btnSave = By.cssSelector("button[type='submit']");
    private By btnCancel = By.xpath("//button[normalize-space()='Cancel']");

    // Toggle & Login Details
    private By createLoginToggle = By.xpath("//span[contains(@class,'oxd-switch-input')]");
    private By usernameInput = By.xpath("//label[text()='Username']/../following-sibling::div//input");
    private By passwordInput = By.xpath("//label[text()='Password']/../following-sibling::div//input");
    private By confirmPasswordInput = By.xpath("//label[text()='Confirm Password']/../following-sibling::div//input");

    // Upload
    private By uploadInput = By.xpath("//input[@type='file']");
    private By profileImg = By.xpath("//div[contains(@class,'orangehrm-employee-image')]//img");

    // Errors & Messages
    private By loader = By.cssSelector("div.oxd-form-loader");

    private By requiredError = By.xpath("//span[contains(@class, 'oxd-input-field-error-message') and text()='Required']");
    private By employeeIdExistsError = By.xpath("//span[text()='Employee Id already exists']");

    private By usernameRequiredError = By.xpath("//label[text()='Username']/ancestor::div[contains(@class,'oxd-input-group')]//span[text()='Required']");
    private By passwordTooShortError = By.xpath("//label[text()='Password']/ancestor::div[contains(@class,'oxd-input-group')]//span[contains(@class, 'oxd-input-field-error-message')]");
    private By passwordMismatchError = By.xpath("//label[text()='Confirm Password']/ancestor::div[contains(@class,'oxd-input-group')]//span[text()='Passwords do not match']");
    private By fileError = By.xpath("//div[contains(@class,'orangehrm-employee-image')]//span[contains(@class,'oxd-input-field-error-message')]");

    private By usernameErrorMsg = By.xpath("//label[text()='Username']/ancestor::div[contains(@class,'oxd-input-group')]//span[contains(@class, 'oxd-input-field-error-message')]");
    private By passwordErrorMsg = By.xpath("//label[text()='Password']/ancestor::div[contains(@class,'oxd-input-group')]//span[contains(@class, 'oxd-input-field-error-message')]");


    public AddEmployeePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // --- ACTIONS ---

    public AddEmployeePage enterFirstName(String name) {
        setText(firstNameInput, name);
        return this;
    }

    public AddEmployeePage enterMiddleName(String name) {
        setText(middleNameInput, name);
        return this;
    }

    public AddEmployeePage enterLastName(String name) {
        setText(lastNameInput, name);
        return this;
    }

    public AddEmployeePage enterEmployeeId(String id) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(employeeIdInput));
        input.sendKeys(Keys.CONTROL + "a");
        input.sendKeys(Keys.DELETE);
        input.sendKeys(id);
        return this;
    }

    public AddEmployeePage enableCreateLoginDetails() {
        waitForLoader();
        wait.until(ExpectedConditions.elementToBeClickable(createLoginToggle)).click();
        return this;
    }

    public AddEmployeePage enterLoginUsername(String value) {
        setText(usernameInput, value);
        return this;
    }

    public AddEmployeePage enterLoginPassword(String value) {
        setText(passwordInput, value);
        return this;
    }

    public AddEmployeePage enterLoginConfirmPassword(String value) {
        setText(confirmPasswordInput, value);
        return this;
    }

    public AddEmployeePage uploadProfilePhoto(String absolutePath) {
        wait.until(ExpectedConditions.presenceOfElementLocated(uploadInput)).sendKeys(absolutePath);
        return this;
    }

    public void clickSave() {
        waitForLoader();
        WebElement saveBtn = wait.until(ExpectedConditions.presenceOfElementLocated(btnSave));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveBtn);
    }

    public void clickCancel() {
        waitForLoader();
        WebElement cancelBtn = wait.until(ExpectedConditions.presenceOfElementLocated(btnCancel));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cancelBtn);
    }

    private void printLog(String label, String message) {
        System.out.println(logCounter + ". " + label + ": " + message);
        logCounter++;
    }

    // --- VERIFICATIONS ---

    private boolean                                                                                                    bb checkAndPrint(By locator, String logLabel) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            String text = element.getText();
            printLog(logLabel, text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isFirstNameRequiredErrorShown() {
        return checkAndPrint(requiredError, "First Name Error");
    }

    public boolean isLastNameRequiredErrorShown() {
        return checkAndPrint(requiredError, "Last Name Error");
    }

    public boolean isEmployeeIdDuplicateErrorShown() {
        return checkAndPrint(employeeIdExistsError, "Employee ID Error");
    }

    public boolean isLoginUsernameRequiredErrorShown() {
        return checkAndPrint(usernameRequiredError, "Username Required Error");
    }

    public boolean isPasswordTooShortErrorShown() {
        return checkAndPrint(passwordTooShortError, "Password Length Error");
    }

    public boolean isConfirmPasswordMismatchErrorShown() {
        return checkAndPrint(passwordMismatchError, "Confirm Password Error");
    }

    public boolean isFileSizeExceedErrorShown() {
        return checkAndPrint(fileError, "File Size Error");
    }

    public boolean isFileTypeNotAllowedErrorShown() {
        try {
            WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(fileError));
            String text = error.getText();
            printLog("File Type Error", text);
            return text.contains("File type not allowed");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUsernameDuplicateErrorShown() {
        return checkAndPrint(usernameErrorMsg, "Username Duplicate Error");
    }

    public boolean isPasswordWeakErrorShown() {
        return checkAndPrint(passwordErrorMsg, "Password Weak Error");
    }

    // --- FIX LOG ẢNH BASE64 ---
    public boolean isProfilePhotoUpdated() {
        try {
            WebElement img = wait.until(ExpectedConditions.visibilityOfElementLocated(profileImg));
            String src = img.getAttribute("src");

            boolean isUpdated = src != null && !src.contains("default");

            if (isUpdated) {
                String displaySrc = src;
                if (src.startsWith("data:image")) {
                    displaySrc = "Base64 Image Data (Truncated for clean log)";
                }

                printLog("Profile Photo", "Updated successfully (Source: " + displaySrc + ")");
            }
            return isUpdated;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPersonalDetailsPageLoaded() {
        try {
            boolean isLoaded = wait.until(ExpectedConditions.urlContains("/pim/viewPersonalDetails"));
            if (isLoaded) {
                printLog("Page Navigation", "Redirected to Personal Details Page successfully.");
            }
            return isLoaded;
        } catch (Exception e) {
            return false;
        }
    }

    // --- PRIVATE HELPERS ---

    private void setText(By locator, String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(text);
    }

    private void waitForLoader() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
        } catch (Exception e) {
        }
    }

    private boolean isElementVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}