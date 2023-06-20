package com.demoDesk.desk.models.Enums;

public enum RequestStatus {
    IN_PROGRESS ("Выполняется"),
    CANCELED ("Отмена"),
    COMPLETE ("Выполнено");

    private final String title;
    RequestStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
