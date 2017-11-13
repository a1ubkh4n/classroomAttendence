package com.a1ubkh4n.app.classroom.enums;

/**
 * Created by a1ubkh4n on 4/7/2016.
 */
public enum PastAttendancePopup {
    DELETE_ATTENDANCE(0);

    private final int value;

    PastAttendancePopup(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}