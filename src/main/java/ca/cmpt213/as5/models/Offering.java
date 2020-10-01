package ca.cmpt213.as5.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Offering implements Comparable<Offering> {
    private int offeringId;
    private Semester semester;
    private List<String> instructors;
    private String location;

    private List<Section> sections;

    public Offering(int semesterCode, String location, int enrollmentCap, int enrollmentTotal, String instructor, String sectionType) {
        semester = new Semester(semesterCode);
        this.location = location;
        instructors = new ArrayList<>();
        sections = new ArrayList<>();

        addSection(enrollmentCap, enrollmentTotal, instructor, sectionType);
    }

    public Semester getSemester() {
        return semester;
    }

    public String getLocation() {
        return location;
    }

    public String getInstructors() {
        return String.join(", ", instructors);
    }

    public List<Section> getSections() {
        return sections;
    }

    public void addSection(int enrollmentCap, int enrollmentTotal, String instructor, String sectionType) {
        addInstructors(instructor);

        boolean isNewSection = true;
        for (Section section: sections) {
            if (section.getType().equals(sectionType)) {
                isNewSection = false;
                section.addCap(enrollmentCap);
                section.addTotal(enrollmentTotal);
                break;
            }
        }

        if (isNewSection) {
            sections.add(new Section(enrollmentCap, enrollmentTotal, sectionType));
        }
    }

    private void addInstructors(String instructor) {
        List<String> instructorList = Arrays.asList(instructor.replace("\"", "").trim().split(","));
        for (String elem: instructorList) {
            String name = elem.trim();
            if (name.equals("<null>") || name.equals("(null)")) {
                name = "(nothing)";
            }
            if (!instructors.contains(name)) {
                instructors.add(name);
            }
        }
    }

    @Override
    public int compareTo(Offering otherOffering) {
        return semester.compareTo(otherOffering.semester);
    }
}
