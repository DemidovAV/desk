package com.demoDesk.desk.models.Enums;

public enum EmployeeStatus {
    VACATION("Отпуск"),
    ILL("Больничный"),
    WORKING("Трудоспособен");

    private final String title;
    EmployeeStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
