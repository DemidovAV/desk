package com.demoDesk.desk.models.Enums;

public enum Priority {
    HIGH(1),
    MEDIUM(2),
    LOW(3);

    private final int priority;

    Priority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}