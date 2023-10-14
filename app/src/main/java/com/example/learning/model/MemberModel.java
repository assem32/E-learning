package com.example.learning.model;

public class MemberModel {



    private String studentEmail ;
    private String courseId ;

    private String studentId ;





    private double attendanceGrade ;
    private double quizGrade ;




    public  MemberModel(){}

    public MemberModel( double attendanceGrade, double quizGrade,String studentEmail, String studentId) {
        this.studentEmail = studentEmail;
        this.studentId = studentId;

        this.attendanceGrade = attendanceGrade;
        this.quizGrade = quizGrade;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getStudentId() {
        return studentId;
    }



    public double getAttendanceGrade() {
        return attendanceGrade;
    }

    public double getQuizGrade() {
        return quizGrade;
    }
}
