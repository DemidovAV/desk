package com.demoDesk.desk.models.Enums;
//Статус тасков - выполняется, выполнено, отменено
public enum RequestStatus {
    IN_PROGRESS ("In progress"),
    COMPLETE ("Complete"),
    CANCELED ("Canceled");

    private final String title;
    RequestStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
