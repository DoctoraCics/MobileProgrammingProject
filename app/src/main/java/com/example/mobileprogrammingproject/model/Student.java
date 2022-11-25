package com.example.mobileprogrammingproject.model;

public class Student {

    private int Student_Number;
    private double Student_Grade;
    private String Subject_Name;
    private String Student_Status;
    private String Student_Name;

    public Student(int Student_Number, double Student_Grade,
                   String Subject_Name, String Student_Status,
                   String Student_Name){
       this.Student_Number = Student_Number;
       this.Student_Grade = Student_Grade;
       this.Subject_Name = Subject_Name;
       this.Student_Status = Student_Status;
       this.Student_Name = Student_Name;
    }

    public int getStudent_Number() {
        return Student_Number;
    }

    public void setStudent_Number(int student_Number) {
        Student_Number = student_Number;
    }

    public double getStudent_Grade() {
        return Student_Grade;
    }

    public void setStudent_Grade(double student_Grade) {
        Student_Grade = student_Grade;
    }

    public String getSubject_Name() {
        return Subject_Name;
    }

    public void setSubject_Name(String subject_Name) {
        Subject_Name = subject_Name;
    }

    public String getStudent_Status() {
        return Student_Status;
    }

    public void setStudent_Status(String student_Status) {
        Student_Status = student_Status;
    }

    public String getStudent_Name() {
        return Student_Name;
    }

    public void setStudent_Name(String student_Name) {
        Student_Name = student_Name;
    }
    public String toString(){
        String compile = this.Student_Name;
        return compile;
    }
}
