package ca.cmpt213.as5.wrappers;

public class ApiCourseOfferingWrapper {
    public long courseOfferingId;
    public String location;
    public String instructors;
    public String term;
    public long semesterCode;
    public int year;

    public ApiCourseOfferingWrapper(long courseOfferingId, String location, String instructors, String term, int semesterCode, int year) {
        this.courseOfferingId = courseOfferingId;
        this.location = location;
        this.instructors = instructors;
        this.term = term;
        this.semesterCode = semesterCode;
        this.year = year;
    }
}