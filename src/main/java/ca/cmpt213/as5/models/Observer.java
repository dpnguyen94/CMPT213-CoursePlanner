package ca.cmpt213.as5.models;

public interface Observer {
    void stateChanged(int semesterCode, int enrollmentCap, int enrollmentTotal, String sectionType);
}
