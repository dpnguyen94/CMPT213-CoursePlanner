package ca.cmpt213.as5.models;

public class Section implements Comparable<Section> {
    private String type;
    private int enrollmentCap;
    private int enrollmentTotal;

    public Section(int enrollmentCap, int enrollmentTotal, String sectionType) {
        type = sectionType;
        this.enrollmentCap = enrollmentCap;
        this.enrollmentTotal = enrollmentTotal;
    }

    public String getType() {
        return type;
    }

    public int getEnrollmentCap() {
        return enrollmentCap;
    }

    public int getEnrollmentTotal() {
        return enrollmentTotal;
    }


    public void addCap(int enrollmentCap) {
        this.enrollmentCap += enrollmentCap;
    }

    public void addTotal(int enrollmentTotal) {
        this.enrollmentTotal += enrollmentTotal;
    }

    @Override
    public int compareTo(Section otherSection) {
        return type.compareTo(otherSection.type);
    }
}
