package com.mtritran.testOrangehrm.tests;

import com.mtritran.testOrangehrm.core.BaseTest;
import com.mtritran.testOrangehrm.pages.AddEmployeePage;
import com.mtritran.testOrangehrm.pages.EmployeeListPage;
import com.mtritran.testOrangehrm.pages.LoginPage;
import com.mtritran.testOrangehrm.pages.PIMPage;
import com.mtritran.testOrangehrm.utils.EmployeeData;
import com.mtritran.testOrangehrm.utils.EmployeeFactory;
import com.mtritran.testOrangehrm.utils.TestDataUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AllTests extends BaseTest {

    // CHẠY 1 LẦN DUY NHẤT: Đăng nhập
    @BeforeClass(dependsOnMethods = "setup")
    public void loginAndPrepare() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        LoginPage login = new LoginPage(driver, wait);
        // Login data là static, ít thay đổi nên gọi trực tiếp TestDataUtil
        login.loginAs(TestDataUtil.getLogin("username"), TestDataUtil.getLogin("password"));
        slow(2000);
    }

    // --- NHÓM 1: BASIC INFO ---
    @Test(priority = 1)
    public void addEmployeeMissingFirstName() {
        PIMPage pim = new PIMPage(driver, wait);
        pim.goToAddEmployeePage();
        AddEmployeePage addEmp = new AddEmployeePage(driver, wait);

        // Lấy template minimal, nhưng chỉ nhập LastName
        EmployeeData data = EmployeeFactory.createMinimalEmployee();
        addEmp.enterLastName(data.getLastName());

        addEmp.clickSave();
        Assert.assertTrue(addEmp.isFirstNameRequiredErrorShown(), "Required error for First Name");
    }

    @Test(priority = 2)
    public void addEmployeeMissingLastName() {
        PIMPage pim = new PIMPage(driver, wait);
        pim.goToAddEmployeePage();
        AddEmployeePage addEmp = new AddEmployeePage(driver, wait);

        EmployeeData data = EmployeeFactory.createMinimalEmployee();
        addEmp.enterFirstName(data.getFirstName());

        addEmp.clickSave();
        Assert.assertTrue(addEmp.isLastNameRequiredErrorShown(), "Required error for Last Name");
    }

    @Test(priority = 3)
    public void addEmployeeDuplicateId() {
        PIMPage pim = new PIMPage(driver, wait);
        pim.goToAddEmployeePage();
        AddEmployeePage addEmp = new AddEmployeePage(driver, wait);

        EmployeeData data = EmployeeFactory.createDuplicateIdEmployee();

        addEmp.enterFirstName(data.getFirstName())
                .enterMiddleName(data.getMiddleName())
                .enterLastName(data.getLastName())
                .enterEmployeeId(data.getEmployeeId());

        addEmp.clickSave();
        Assert.assertTrue(addEmp.isEmployeeIdDuplicateErrorShown(), "Duplicate ID error displayed");
    }

    @Test(priority = 4)
    public void addEmployeeMinimalInfo() {
        PIMPage pim = new PIMPage(driver, wait);
        pim.goToAddEmployeePage();
        AddEmployeePage addEmp = new AddEmployeePage(driver, wait);

        EmployeeData data = EmployeeFactory.createMinimalEmployee();

        addEmp.enterFirstName(data.getFirstName())
                .enterLastName(data.getLastName());

        addEmp.clickSave();
        Assert.assertTrue(addEmp.isPersonalDetailsPageLoaded(), "Redirect to Personal Details");
    }

    @Test(priority = 5)
    public void addEmployeeFullInfo() {
        PIMPage pim = new PIMPage(driver, wait);
        pim.goToAddEmployeePage();
        AddEmployeePage addEmp = new AddEmployeePage(driver, wait);

        EmployeeData data = EmployeeFactory.createFullInfoEmployee();

        addEmp.enterFirstName(data.getFirstName())
                .enterMiddleName(data.getMiddleName())
                .enterLastName(data.getLastName())
                .enterEmployeeId(data.getEmployeeId());

        addEmp.clickSave();
        Assert.assertTrue(addEmp.isPersonalDetailsPageLoaded(), "Redirect to Personal Details");
    }

    // --- NHÓM 2: LOGIN DETAILS ---
    @Test(priority = 6)
    public void addEmployeeDuplicateUsername() {
        PIMPage pim = new PIMPage(driver, wait);
        pim.goToAddEmployeePage();

        AddEmployeePage addEmp = new AddEmployeePage(driver, wait);
        EmployeeData data = EmployeeFactory.createDefaultEmployee();

        addEmp.enterFirstName(data.getFirstName());
        addEmp.enterLastName(data.getLastName());

        addEmp.enableCreateLoginDetails();

        // Username 'Admin' luôn tồn tại
        addEmp.enterLoginUsername("Admin");

        addEmp.enterLoginPassword(data.getPassword());
        addEmp.enterLoginConfirmPassword(data.getPassword());

        addEmp.clickSave();

        Assert.assertTrue(
                addEmp.isUsernameDuplicateErrorShown(),
                "Expected: Duplicate Username error is displayed"
        );
    }

    @Test(priority = 7)
    public void addEmployeeMissingLoginUsername() {
        PIMPage pim = new PIMPage(driver, wait);
        pim.goToAddEmployeePage();
        AddEmployeePage addEmp = new AddEmployeePage(driver, wait);

        EmployeeData data = EmployeeFactory.createDefaultEmployee();

        addEmp.enterFirstName(data.getFirstName());
        addEmp.enterLastName(data.getLastName());
        addEmp.enterEmployeeId(data.getEmployeeId());

        addEmp.enableCreateLoginDetails();
        addEmp.enterLoginPassword(data.getPassword());
        addEmp.enterLoginConfirmPassword(data.getPassword());

        addEmp.clickSave();

        Assert.assertTrue(addEmp.isLoginUsernameRequiredErrorShown(), "Required error should appear under Username field");
    }

    @Test(priority = 8)
    public void addEmployeeConfirmPasswordMismatch() {
        PIMPage pim = new PIMPage(driver, wait);
        pim.goToAddEmployeePage();
        AddEmployeePage add = new AddEmployeePage(driver, wait);

        EmployeeData data = EmployeeFactory.createDefaultEmployee();

        add.enterFirstName(data.getFirstName());
        add.enterLastName(data.getLastName());

        add.enableCreateLoginDetails();
        add.enterLoginUsername(data.getUsername());
        add.enterLoginPassword(data.getPassword());
        add.enterLoginConfirmPassword(data.getPassword() + "!!!"); // Mismatch

        add.clickSave();
        Assert.assertTrue(add.isConfirmPasswordMismatchErrorShown(), "Expected 'Passwords do not match' error");
    }

    @Test(priority = 9)
    public void addEmployeePasswordTooShort() {
        PIMPage pim = new PIMPage(driver, wait);
        pim.goToAddEmployeePage();
        AddEmployeePage addEmp = new AddEmployeePage(driver, wait);

        EmployeeData data = EmployeeFactory.createDefaultEmployee();

        addEmp.enterFirstName(data.getFirstName())
                .enterLastName(data.getLastName());

        addEmp.enableCreateLoginDetails();
        addEmp.enterLoginUsername(data.getUsername());

        // Case này đặc biệt, cần nhập sai pass nên hardcode "short" ở đây là hợp lý
        addEmp.enterLoginPassword("short");
        addEmp.enterLoginConfirmPassword("short");

        addEmp.clickSave();
        Assert.assertTrue(addEmp.isPasswordTooShortErrorShown(), "Password too short error");
    }

    @Test(priority = 10)
    public void addEmployeePasswordWeak() {
        PIMPage pim = new PIMPage(driver, wait);
        pim.goToAddEmployeePage();

        AddEmployeePage addEmp = new AddEmployeePage(driver, wait);
        EmployeeData data = EmployeeFactory.createDefaultEmployee();

        addEmp.enterFirstName(data.getFirstName());
        addEmp.enterLastName(data.getLastName());

        addEmp.enableCreateLoginDetails();
        addEmp.enterLoginUsername(data.getUsername());

        addEmp.enterLoginPassword("abcdefgh");
        addEmp.enterLoginConfirmPassword("abcdefgh");

        addEmp.clickSave();

        Assert.assertTrue(
                addEmp.isPasswordWeakErrorShown(),
                "Expected: Weak Password error is displayed"
        );
    }

    @Test(priority = 11)
    public void addEmployeeCreateLoginSuccess() {
        PIMPage pim = new PIMPage(driver, wait);
        pim.goToAddEmployeePage();
        AddEmployeePage addEmp = new AddEmployeePage(driver, wait);

        EmployeeData data = EmployeeFactory.createEmployeeWithLogin();

        addEmp.enterFirstName(data.getFirstName())
                .enterMiddleName(data.getMiddleName())
                .enterLastName(data.getLastName())
                .enterEmployeeId(data.getEmployeeId());

        addEmp.enableCreateLoginDetails();
        addEmp.enterLoginUsername(data.getUsername())
                .enterLoginPassword(data.getPassword())
                .enterLoginConfirmPassword(data.getPassword());

        addEmp.clickSave();
        Assert.assertTrue(addEmp.isPersonalDetailsPageLoaded(), "Should redirect success");
    }

    // --- NHÓM 3: UPLOAD PHOTO ---
    @Test(priority = 12)
    public void uploadWrongFileFormat() {
        PIMPage pim = new PIMPage(driver, wait);
        pim.goToAddEmployeePage();

        AddEmployeePage addEmp = new AddEmployeePage(driver, wait);
        String wrongFile = TestDataUtil.getImage("wrongFormat");
        addEmp.uploadProfilePhoto(wrongFile);

        Assert.assertTrue(addEmp.isFileTypeNotAllowedErrorShown(), "Expected 'File type not allowed' error");
    }

    @Test(priority = 13)
    public void uploadPhotoTooLarge() {
        PIMPage pim = new PIMPage(driver, wait);
        pim.goToAddEmployeePage();

        AddEmployeePage addEmp = new AddEmployeePage(driver, wait);

        String tooLarge = TestDataUtil.getImage("tooLarge");
        addEmp.uploadProfilePhoto(tooLarge);

        Assert.assertTrue(addEmp.isFileSizeExceedErrorShown(), "Expected error message 'Attachment Size Exceeded'");
    }

    @Test(priority = 14)
    public void uploadValidPhoto() {
        PIMPage pim = new PIMPage(driver, wait);
        pim.goToAddEmployeePage();

        AddEmployeePage addEmp = new AddEmployeePage(driver, wait);
        String imgPath = TestDataUtil.getImage("valid");
        addEmp.uploadProfilePhoto(imgPath);

        // Assert này check xem ảnh có hiển thị không, không cần click Save
        Assert.assertTrue(addEmp.isProfilePhotoUpdated(), "Expected profile photo to be updated");
    }

    // --- NHÓM 4: NAVIGATION ---
    @Test(priority = 15)
    public void cancelAddEmployee() {
        PIMPage pim = new PIMPage(driver, wait);
        pim.goToAddEmployeePage();

        AddEmployeePage addEmp = new AddEmployeePage(driver, wait);
        addEmp.clickCancel();

        wait.until(ExpectedConditions.urlContains("/pim/viewEmployeeList"));

        EmployeeListPage listPage = new EmployeeListPage(driver, wait);
        Assert.assertTrue(listPage.isLoaded(), "Expected Employee List page to load");
    }
}