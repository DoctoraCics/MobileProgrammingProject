package com.example.mobileprogrammingproject.model;

public class Subject {

    private int Subject_Id;
    private String Subject_Name;
    private String Subject_Code;
    private String Subject_Status;

    public Subject(int Subject_Id, String Subject_Name, String Subject_Code, String Subject_Status) {
        this.Subject_Id = Subject_Id;
        this.Subject_Name = Subject_Name;
        this.Subject_Code = Subject_Code;
        this.Subject_Status = Subject_Status;
    }

    public String toString() {
        return this.Subject_Name;
    }

    public String getName() {
        return this.Subject_Name;
    }

    public int getSubjectId() {
        return this.Subject_Id;
    }

    public String getSubject_Code() {
        return Subject_Code;
    }

    public String getSubject_Status() {
        return Subject_Status;
    }
}
