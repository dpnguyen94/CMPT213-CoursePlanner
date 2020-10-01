package ca.cmpt213.as5.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseObserver implements Observer {
    List<String> events;

    public CourseObserver() {
        events = new ArrayList<>();
    }

    public List<String> getEvents() {
        return events;
    };

    @Override
    public void stateChanged(int semesterCode, int enrollmentCap, int enrollmentTotal, String sectionType) {
        Semester semester = new Semester(semesterCode);

        Date date = new Date();
        String message = date.toString() + ": " + "Added section " + sectionType + " with enrollment (" + enrollmentTotal
                + "/" + enrollmentCap + ")" + " to offering " + semester.getTerm() + " " + semester.getYear();
        events.add(message);
    }
}
