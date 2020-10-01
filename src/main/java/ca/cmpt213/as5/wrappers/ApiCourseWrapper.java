package ca.cmpt213.as5.wrappers;

public class ApiCourseWrapper {
    public long courseId;
    public String catalogNumber;

    public ApiCourseWrapper(long courseId, String catalogNumber) {
        this.courseId = courseId;
        this.catalogNumber = catalogNumber;
    }
}