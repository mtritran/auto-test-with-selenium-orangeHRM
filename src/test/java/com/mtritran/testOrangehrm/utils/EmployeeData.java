package com.mtritran.testOrangehrm.utils;

public class EmployeeData {
    private String firstName;
    private String middleName;
    private String lastName;
    private String employeeId;
    private String username;
    private String password;

    public EmployeeData() {}

    public EmployeeData(EmployeeData source) {
        if (source != null) {
            this.firstName = source.firstName;
            this.middleName = source.middleName;
            this.lastName = source.lastName;
            this.employeeId = source.employeeId;
            this.username = source.username;
            this.password = source.password;
        }
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}