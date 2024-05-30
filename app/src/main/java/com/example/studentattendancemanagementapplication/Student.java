package com.example.studentattendancemanagementapplication;

public class Student {

    private int studentId;
    private String firstName;
    private String lastName;
    private int absenceCount;

    public Student(int studentId, String firstName, String lastName, int absenceCount) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.absenceCount = absenceCount;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }
}
