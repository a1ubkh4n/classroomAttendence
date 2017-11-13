package com.a1ubkh4n.app.classroom.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by a1ubkh4n on 11/2/2016.
 */
public class AttendanceStatistics implements Parcelable {
    private int id;
    private int classroomId;
    private int studentId;

    private int presencePercentage; //percentage of presence
    private int availableClasses;   //number of classes that could be attended
    private int attendedClasses;    //number of classes student attended

    private String studentName;

    public AttendanceStatistics() {
        id = 0;
        classroomId = 0;
        studentId = 0;
        presencePercentage = 0;
        availableClasses = 0;
        attendedClasses = 0;
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

    public int getPresencePercentage() {
        return presencePercentage;
    }

    public void setPresencePercentage(int presencePercentage) {
        this.presencePercentage = presencePercentage;
    }

    public int getAvailableClasses() {
        return availableClasses;
    }

    public void setAvailableClasses(int availableClasses) {
        this.availableClasses = availableClasses;
    }

    public int getAttendedClasses() {
        return attendedClasses;
    }

    public void setAttendedClasses(int attendedClasses) {
        this.attendedClasses = attendedClasses;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(classroomId);
        dest.writeInt(studentId);
        dest.writeInt(presencePercentage);
        dest.writeInt(availableClasses);
        dest.writeInt(attendedClasses);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AttendanceStatistics> CREATOR = new Creator<AttendanceStatistics>() {
        @Override
        public AttendanceStatistics createFromParcel(Parcel in) {
            return new AttendanceStatistics(in);
        }

        @Override
        public AttendanceStatistics[] newArray(int size) {
            return new AttendanceStatistics[size];
        }
    };

    protected AttendanceStatistics(Parcel in) {
        id = in.readInt();
        classroomId = in.readInt();
        studentId = in.readInt();
        presencePercentage = in.readInt();
        availableClasses = in.readInt();
        attendedClasses = in.readInt();
        studentName = in.readString();
    }
}
