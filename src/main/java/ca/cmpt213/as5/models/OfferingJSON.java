package ca.cmpt213.as5.models;

public class OfferingJSON {
    private int semester;
    private String subjectName;
    private String catalogNumber;
    private String location;
    private int enrollmentCap;
    private int enrollmentTotal;
    private String component;
    private String instructor;

    public int getSemester() {
        return semester;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getCatalogNumber() {
        return catalogNumber;
    }

    public String getLocation() {
        return location;
    }

    public int getEnrollmentCap() {
        return enrollmentCap;
    }

    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }

    public String getComponent() {
        return component;
    }

    public String getInstructor() {
        return instructor;
    }

    @Override
    public String toString() {
        return "OfferingJSON{" +
                "semester=" + semester +
                ", subjectName='" + subjectName + '\'' +
                ", catalogNumber='" + catalogNumber + '\'' +
                ", location='" + location + '\'' +
                ", enrollmentCap=" + enrollmentCap +
                ", enrollmentTotal=" + enrollmentTotal +
                ", component='" + component + '\'' +
                ", instructor='" + instructor + '\'' +
                '}';
    }
}
