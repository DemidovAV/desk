package com.demoDesk.desk.models.enums;

//Приоритет выполнения тикетов и тасков
public enum Priority {
    /**
     * смотри, ошибка Enums(каталог) надо с маленькой буквы, я переименовал. Старайся в этот каталог засовывать
     * только общие энамы, а те, которые принадлижат к отдельной сущности- перености в свой каталог. например
     * EmployeeStatus я перенес в personel(тут точно personel а не personal?
     * )
     */
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
