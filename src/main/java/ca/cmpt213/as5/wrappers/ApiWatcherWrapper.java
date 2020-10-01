package ca.cmpt213.as5.wrappers;

import java.util.List;

public class ApiWatcherWrapper {
    public long id;
    public ApiDepartmentWrapper department;
    public ApiCourseWrapper course;
    public List<String> events;

    public ApiWatcherWrapper(long id, ApiDepartmentWrapper department, ApiCourseWrapper course, List<String> events) {
        this.id = id;
        this.department = department;
        this.course = course;
        this.events = events;
    }
}
