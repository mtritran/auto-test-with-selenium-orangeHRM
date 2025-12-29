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

public class EachTest extends BaseTest {

    // --- SETUP CHO TESTNG (Giữ nguyên để nếu giảng viên chạy TestNG vẫn được) ---
    @BeforeClass(dependsOnMethods = "setup")
    public void loginAndPrepare() {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        LoginPage login = new LoginPage(driver, wait);
        login.loginAs(TestDataUtil.getLogin("username"), TestDataUtil.getLogin("password"));
        slow(2000);
    }

    // =========================================================================
    //                            HÀM MAIN (CHẠY THỦ CÔNG)
    // =========================================================================
    public static void main(String[] args) {
        // 1. Khởi tạo đối tượng Test
        AllTests app = new AllTests();

        try {
            System.out.println("=== BẮT ĐẦU CHẠY THỦ CÔNG ===");

            // 2. Gọi hàm Setup (Mở trình duyệt) - Lấy từ BaseTest
            app.setup();

            // 3. Gọi hàm Login (Bắt buộc phải chạy trước)
            app.loginAndPrepare();

            // ============================================================
            // 4. CHỌN TEST CASE CẦN CHẠY (Bỏ comment dòng bạn muốn test)
            // ============================================================

            // --- Nhóm 1: Basic Info ---
            // app.addEmployeeMissingFirstName();
            // app.addEmployeeMissingLastName();
            // app.addEmployeeDuplicateId();
            // app.addEmployeeMinimalInfo();  // <--- Đang chạy thử cái này
            // app.addEmployeeFullInfo();

            // --- Nhóm 2: Login Details ---
             app.addEmployeeDuplicateUsername();
            // app.addEmployeeMissingLoginUsername();
            // app.addEmployeeConfirmPasswordMismatch();
            // app.addEmployeePasswordTooShort();
            // app.addEmployeePasswordWeak();
            // app.addEmployeeCreateLoginSuccess();

            // --- Nhóm 3: Upload ---
            // app.uploadWrongFileFormat();
            // app.uploadPhotoTooLarge();
            // app.uploadValidPhoto();

            // --- Nhóm 4: Navigation ---
            // app.cancelAddEmployee();

            System.out.println("=== KẾT THÚC TEST CASE (PASS) ===");

        } catch (AssertionError e) {
            System.err.println("!!! TEST FAIL !!!");
            System.err.println("Lý do: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("!!! LỖI HỆ THỐNG !!!");
            e.printStackTrace();
        } finally {
            // 5. Đóng trình duyệt (Optional - Nếu muốn xem kết quả thì comment lại)
             app.tearDown();
        }
    }


    // =========================================================================
    //                        CÁC TEST CASE (LOGIC CHÍNH)
    // =========================================================================

    @Test(priority = 1)
    public void addEmployeeMissingFirstName() {
        AddEmployeePage addEmp = openAddEmployeePage();
        EmployeeData data = EmployeeFactory.createMinimalEmployee();
        addEmp.enterLastName(data.getLastName());
        addEmp.clickSave();
        Assert.assertTrue(addEmp.isFirstNameRequiredErrorShown(), "Required error for First Name");
    }

    @Test(priority = 2)
    public void addEmployeeMissingLastName() {
        AddEmployeePage addEmp = openAddEmployeePage();
        EmployeeData data = EmployeeFactory.createMinimalEmployee();
        addEmp.enterFirstName(data.getFirstName());
        addEmp.clickSave();
        Assert.assertTrue(addEmp.isLastNameRequiredErrorShown(), "Required error for Last Name");
    }

    @Test(priority = 3)
    public void addEmployeeDuplicateId() {
        AddEmployeePage addEmp = openAddEmployeePage();
        EmployeeData data = EmployeeFactory.createDuplicateIdEmployee();
        fillBasicDetails(addEmp, data);
        addEmp.clickSave();
        Assert.assertTrue(addEmp.isEmployeeIdDuplicateErrorShown(), "Duplicate ID error displayed");
    }

    @Test(priority = 4)
    public void addEmployeeMinimalInfo() {
        AddEmployeePage addEmp = openAddEmployeePage();
        EmployeeData data = EmployeeFactory.createMinimalEmployee();
        addEmp.enterFirstName(data.getFirstName()).enterLastName(data.getLastName());
        addEmp.clickSave();
        Assert.assertTrue(addEmp.isPersonalDetailsPageLoaded(), "Redirect to Personal Details");
    }

    @Test(priority = 5)
    public void addEmployeeFullInfo() {
        AddEmployeePage addEmp = openAddEmployeePage();
        EmployeeData data = EmployeeFactory.createFullInfoEmployee();
        fillBasicDetails(addEmp, data);
        addEmp.clickSave();
        Assert.assertTrue(addEmp.isPersonalDetailsPageLoaded(), "Redirect to Personal Details");
    }

    @Test(priority = 6)
    public void addEmployeeDuplicateUsername() {
        AddEmployeePage addEmp = openAddEmployeePage();
        EmployeeData data = EmployeeFactory.createDefaultEmployee();
        fillBasicDetails(addEmp, data);
        fillLoginDetails(addEmp, "Admin", data.getPassword(), data.getPassword());
        addEmp.clickSave();
        Assert.assertTrue(addEmp.isUsernameDuplicateErrorShown(), "Expected: Duplicate Username error is displayed");
    }

    @Test(priority = 7)
    public void addEmployeeMissingLoginUsername() {
        AddEmployeePage addEmp = openAddEmployeePage();
        EmployeeData data = EmployeeFactory.createDefaultEmployee();
        fillBasicDetails(addEmp, data);
        addEmp.enableCreateLoginDetails();
        addEmp.enterLoginPassword(data.getPassword());
        addEmp.enterLoginConfirmPassword(data.getPassword());
        addEmp.clickSave();
        Assert.assertTrue(addEmp.isLoginUsernameRequiredErrorShown(), "Required error should appear under Username field");
    }

    @Test(priority = 8)
    public void addEmployeeConfirmPasswordMismatch() {
        AddEmployeePage addEmp = openAddEmployeePage();
        EmployeeData data = EmployeeFactory.createDefaultEmployee();
        fillBasicDetails(addEmp, data);
        fillLoginDetails(addEmp, data.getUsername(), data.getPassword(), data.getPassword() + "!!!");
        addEmp.clickSave();
        Assert.assertTrue(addEmp.isConfirmPasswordMismatchErrorShown(), "Expected 'Passwords do not match' error");
    }

    @Test(priority = 9)
    public void addEmployeePasswordTooShort() {
        AddEmployeePage addEmp = openAddEmployeePage();
        EmployeeData data = EmployeeFactory.createDefaultEmployee();
        fillBasicDetails(addEmp, data);
        fillLoginDetails(addEmp, data.getUsername(), "short", "short");
        addEmp.clickSave();
        Assert.assertTrue(addEmp.isPasswordTooShortErrorShown(), "Password too short error");
    }

    @Test(priority = 10)
    public void addEmployeePasswordWeak() {
        AddEmployeePage addEmp = openAddEmployeePage();
        EmployeeData data = EmployeeFactory.createDefaultEmployee();
        fillBasicDetails(addEmp, data);
        fillLoginDetails(addEmp, data.getUsername(), "abcdefgh", "abcdefgh");
        addEmp.clickSave();
        Assert.assertTrue(addEmp.isPasswordWeakErrorShown(), "Expected: Weak Password error is displayed");
    }

    @Test(priority = 11)
    public void addEmployeeCreateLoginSuccess() {
        AddEmployeePage addEmp = openAddEmployeePage();
        EmployeeData data = EmployeeFactory.createEmployeeWithLogin();
        fillBasicDetails(addEmp, data);
        fillLoginDetails(addEmp, data.getUsername(), data.getPassword(), data.getPassword());
        addEmp.clickSave();
        Assert.assertTrue(addEmp.isPersonalDetailsPageLoaded(), "Should redirect success");
    }

    @Test(priority = 12)
    public void uploadWrongFileFormat() {
        AddEmployeePage addEmp = openAddEmployeePage();
        String wrongFile = TestDataUtil.getImage("wrongFormat");
        addEmp.uploadProfilePhoto(wrongFile);
        Assert.assertTrue(addEmp.isFileTypeNotAllowedErrorShown(), "Expected 'File type not allowed' error");
    }

    @Test(priority = 13)
    public void uploadPhotoTooLarge() {
        AddEmployeePage addEmp = openAddEmployeePage();
        String tooLarge = TestDataUtil.getImage("tooLarge");
        addEmp.uploadProfilePhoto(tooLarge);
        Assert.assertTrue(addEmp.isFileSizeExceedErrorShown(), "Expected error message 'Attachment Size Exceeded'");
    }

    @Test(priority = 14)
    public void uploadValidPhoto() {
        AddEmployeePage addEmp = openAddEmployeePage();
        String imgPath = TestDataUtil.getImage("valid");
        addEmp.uploadProfilePhoto(imgPath);
        Assert.assertTrue(addEmp.isProfilePhotoUpdated(), "Expected profile photo to be updated");
    }

    @Test(priority = 15)
    public void cancelAddEmployee() {
        AddEmployeePage addEmp = openAddEmployeePage();
        addEmp.clickCancel();
        wait.until(ExpectedConditions.urlContains("/pim/viewEmployeeList"));
        EmployeeListPage listPage = new EmployeeListPage(driver, wait);
        Assert.assertTrue(listPage.isLoaded(), "Expected Employee List page to load");
    }

    // --- HELPER METHODS ---
    private AddEmployeePage openAddEmployeePage() {
        PIMPage pim = new PIMPage(driver, wait);
        pim.goToAddEmployeePage();
        return new AddEmployeePage(driver, wait);
    }

    private void fillBasicDetails(AddEmployeePage page, EmployeeData data) {
        if (data.getFirstName() != null) page.enterFirstName(data.getFirstName());
        if (data.getMiddleName() != null) page.enterMiddleName(data.getMiddleName());
        if (data.getLastName() != null) page.enterLastName(data.getLastName());
        if (data.getEmployeeId() != null) page.enterEmployeeId(data.getEmployeeId());
    }

    private void fillLoginDetails(AddEmployeePage page, String user, String pass, String confirmPass) {
        page.enableCreateLoginDetails();
        page.enterLoginUsername(user);
        page.enterLoginPassword(pass);
        page.enterLoginConfirmPassword(confirmPass);
    }
}