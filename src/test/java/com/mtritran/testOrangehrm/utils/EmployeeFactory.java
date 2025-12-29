package com.mtritran.testOrangehrm.utils;

public class EmployeeFactory {

    // Tạo Default Employee với Random ID/User (An toàn tuyệt đối)
    public static EmployeeData createDefaultEmployee() {
        // 1. Lấy template gốc
        EmployeeData template = TestDataUtil.getEmployeeTemplate("default");

        // 2. Clone ra object mới hoàn toàn (Copy Constructor)
        EmployeeData newData = new EmployeeData(template);

        // 3. Modifiy trên object mới (Dữ liệu động)
        long timeStamp = System.currentTimeMillis();
        newData.setEmployeeId(String.valueOf(timeStamp).substring(8)); // 5 số cuối
        newData.setUsername("user" + timeStamp);

        return newData;
    }

    // Case Duplicate ID: Cần giữ nguyên ID trong JSON
    public static EmployeeData createDuplicateIdEmployee() {
        EmployeeData template = TestDataUtil.getEmployeeTemplate("duplicateId");
        return new EmployeeData(template); // Vẫn clone để an toàn
    }

    // Case Minimal: Chỉ lấy First/Last Name
    public static EmployeeData createMinimalEmployee() {
        EmployeeData template = TestDataUtil.getEmployeeTemplate("minimal");
        return new EmployeeData(template);
    }

    public static EmployeeData createFullInfoEmployee() {
        EmployeeData data = createDefaultEmployee();

        // Sinh ID 100% unique mỗi lần test
        long ts = System.currentTimeMillis();
        String newId = String.valueOf(ts).substring(7); // lấy 6 số cuối
        data.setEmployeeId(newId);

        return data;
    }

    // Case Login: Dùng username/pass từ default
    public static EmployeeData createEmployeeWithLogin() {
        // createDefaultEmployee đã tự sinh username unique rồi
        return createDefaultEmployee();
    }
}