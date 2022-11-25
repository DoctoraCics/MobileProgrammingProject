package com.example.mobileprogrammingproject.model;

public class Subject {
    private int Subject_Id;
    private String Subject_Name;

    public Subject(int Subject_Id, String Subject_Name){
        this.Subject_Id = Subject_Id;
        this.Subject_Name = Subject_Name;
    }

    @Override
    public String toString(){
        return this.Subject_Name;
    }

    public int getSubjectId(){
        return this.Subject_Id;
    }
}
