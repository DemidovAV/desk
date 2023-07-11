package com.demoDesk.desk.models.personel;
// Статус работника: отпуск, больничный, работает
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
