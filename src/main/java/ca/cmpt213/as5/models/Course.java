package ca.cmpt213.as5.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Course implements Comparable<Course> {
    private int courseId;
    private String catalogNbr;

    private List<Offering> offerings;
    private List<CourseObserver> observers;

    public Course(int semesterCode, String catalogNbr, String location, int enrollmentCap, int enrollmentTotal, String instructor, String sectionType) {
        this.catalogNbr = catalogNbr;
        offerings = new ArrayList<>();
        observers = new ArrayList<>();

        addOffering(semesterCode, location, enrollmentCap, enrollmentTotal, instructor, sectionType);
    }

    public String getCatalogNbr() {
        return catalogNbr;
    }

    public List<Offering> getOfferings() {
        return offerings;
    }

    public List<CourseObserver> getObservers() {
        return observers;
    }

    public void addOffering(int semesterCode, String location, int enrollmentCap, int enrollmentTotal, String instructor, String sectionType) {
        boolean isNewOffering = true;
        for (Offering offering: offerings) {
            if (offering.getSemester().getCode().equals(Integer.toString(semesterCode)) && offering.getLocation().equals(location)) {
                isNewOffering = false;
                offering.addSection(enrollmentCap, enrollmentTotal, instructor, sectionType);
                break;
            }
        }

        if (isNewOffering) {
            offerings.add(new Offering(semesterCode, location, enrollmentCap, enrollmentTotal, instructor, sectionType));
        }

        notifyObservers(semesterCode, enrollmentCap, enrollmentTotal, sectionType);
    }

    public Offering getOfferingById(int offeringId) {
        if (offeringId < 0 || offeringId > offerings.size() - 1) throw new IllegalArgumentException();
        return offerings.get(offeringId);
    }

    private void notifyObservers(int semesterCode, int enrollmentCap, int enrollmentTotal, String sectionType) {
        for (CourseObserver observer: observers) {
            observer.stateChanged(semesterCode, enrollmentCap, enrollmentTotal, sectionType);
        }
    }

    public void addObserver() {
        observers.add(new CourseObserver());
    }

    @Override
    public int compareTo(Course otherCourse) {
        return catalogNbr.compareTo(otherCourse.catalogNbr);
    }
}
