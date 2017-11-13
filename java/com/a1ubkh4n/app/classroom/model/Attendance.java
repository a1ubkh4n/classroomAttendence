package com.a1ubkh4n.app.classroom.model;

/**
 * Created by a1ubkh4n on 4/6/2016.
 */
public class Attendance {
    private int id;
    private int classroomId;
    private int studentId;
    private int present;
    private String dateTime;
    private String studentName;


    public Attendance() {
        id = 0;
        classroomId = 0;
        studentId = 0;
        present = 0;
        dateTime = "";
        studentName = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(int classroomId) {
        this.classroomId = classroomId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getPresent() {
        return present;
    }

    public void setPresent(int present) {
        this.present = present;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

}
