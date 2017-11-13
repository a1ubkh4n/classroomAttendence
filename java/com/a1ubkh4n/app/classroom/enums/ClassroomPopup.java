package com.a1ubkh4n.app.classroom.enums;

/**
 * Created by a1ubkh4n on 4/6/2016.
 */
public enum ClassroomPopup {
    CHANGE_NAME(0),
    DELETE_CLASSROOM(1);

    private final int value;

    ClassroomPopup(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}