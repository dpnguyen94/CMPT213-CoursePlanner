package ca.cmpt213.as5.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents department(s) in the data
 */

public class Subject implements Comparable<Subject> {
    private int subjectId;
    private String name;

    private List<Course> courses;

    public Subject(int semesterCode, String subjectName, String catalogNbr, String location, int enrollmentCap, int enrollmentTotal, String instructor, String sectionType) {
        name = subjectName;
        courses = new ArrayList<>();

        addCourse(semesterCode, catalogNbr, location, enrollmentCap, enrollmentTotal, instructor, sectionType);
    }

    public String getName() {
        return name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourse(int semesterCode, String catalogNbr, String location, int enrollmentCap,
                          int enrollmentTotal, String instructor, String sectionType) {

        boolean isNewCourse = true;
        for (Course course: courses) {
            if (course.getCatalogNbr().equals(catalogNbr)) {
                isNewCourse = false;
                course.addOffering(semesterCode, location, enrollmentCap, enrollmentTotal, instructor, sectionType);
                break;
            }
        }

        if (isNewCourse) {
            courses.add(new Course(semesterCode, catalogNbr, location, enrollmentCap, enrollmentTotal, instructor, sectionType));
        }
    }

    public Course getCourseById(int courseId) {
        if (courseId < 0 || courseId > courses.size() - 1) throw new IllegalArgumentException();
        return courses.get(courseId);
    }

    @Override
    public int compareTo(Subject otherSubject) {
        return name.compareTo(otherSubject.name);
    }
}
