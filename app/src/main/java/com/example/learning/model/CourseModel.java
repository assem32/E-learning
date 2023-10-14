package com.example.learning.model;

public class CourseModel {
    private String instructorId  ;
    private String courseId ;
    private String courseName ;
    private double assignmentGrade ;
    private double attendanceGrade ;
    private double projectsGrade ;

    public CourseModel(){}

    public CourseModel(String instructorId, String courseId, String courseName, double assignmentGrade, double attendanceGrade, double projectsGrade) {
        this.instructorId = instructorId;
        this.courseId = courseId;
        this.courseName = courseName;
        this.assignmentGrade = assignmentGrade;
        this.attendanceGrade = attendanceGrade;
        this.projectsGrade = projectsGrade;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public double getAssignmentGrade() {
        return assignmentGrade;
    }

    public double getAttendanceGrade() {
        return attendanceGrade;
    }

    public double getProjectsGrade() {
        return projectsGrade;
    }
}
