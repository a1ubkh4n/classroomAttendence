package com.a1ubkh4n.app.classroom.interfaces;

/**
 * Created by a1ubkh4n on 3/9/2016.
 */
public interface DateBackListener {
    /**
     * Date
     * @param dayOfMonth int
     * @param month int
     * @param year int
     */
    void OnPress(int dayOfMonth, int month, int year);

    /**
     * Time
     * @param minute int
     * @param hour int
     */
    void OnPress(int minute, int hour);
}
