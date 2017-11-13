package com.a1ubkh4n.app.classroom.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Classroom implements Parcelable {
    private int id;
    private String name;
    private int studentNumber;

    public Classroom() {
        id = 0;
        name = "";
        studentNumber = 0;
    }

    public Classroom(String name) {
        id = 0;
        this.name = name;
        studentNumber = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(studentNumber);
    }

    public static final Parcelable.Creator<Classroom> CREATOR
            = new Parcelable.Creator<Classroom>() {
        public Classroom createFromParcel(Parcel in) {
            return new Classroom(in);
        }

        public Classroom[] newArray(int size) {
            return new Classroom[size];
        }
    };

    private Classroom(Parcel in) {
        id = in.readInt();
        name = in.readString();
        studentNumber = in.readInt();
    }
}
