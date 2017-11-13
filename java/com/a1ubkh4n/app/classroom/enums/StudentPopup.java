package com.a1ubkh4n.app.classroom.enums;

/**
 * Created by a1ubkh4n on 4/6/2016.
 */
public enum StudentPopup {
    CHANGE_NAME(0),
    DELETE_STUDENT(1);

    private final int value;

    StudentPopup(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
