package com.demoDesk.desk.models.Enums;

public enum EmployeeStatus {
    VACATION("Vacation"),
    ILL("Ill"),
    WORKING("Workable");

    private final String title;
    EmployeeStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
