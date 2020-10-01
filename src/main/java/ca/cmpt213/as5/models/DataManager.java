package ca.cmpt213.as5.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading and sorting data into the models
 */

public class DataManager {
    private List<Subject> subjects;

    public DataManager(List<List<String>> data) {
        subjects = new ArrayList<>();
        loadData(data);
        sortData();
    }

    private void loadData(List<List<String>> data) {
        boolean isFirstRow = true;
        for (List<String> row: data) {
            if (isFirstRow) {
                isFirstRow = false;
                continue;
            }

            int semesterCode = Integer.parseInt(row.get(0));
            String subjectName = row.get(1).trim();
            String catalogNbr = row.get(2).trim();
            String location = row.get(3).trim();

            int enrollmentCap = Integer.parseInt(row.get(4));
            int enrollmentTotal = Integer.parseInt(row.get(5));

            String instructor = String.join("," , row.subList(6, row.size() - 1));
            String sectionType = row.get(row.size() - 1);

            addSubject(semesterCode, subjectName, catalogNbr, location,
                    enrollmentCap, enrollmentTotal, instructor, sectionType);

        }
    }

    public void sortData() {
        java.util.Collections.sort(subjects);
        for (Subject subject: subjects) {
            java.util.Collections.sort(subject.getCourses());
            for (Course course: subject.getCourses()) {
                java.util.Collections.sort(course.getOfferings());
                for (Offering offering: course.getOfferings()) {
                    java.util.Collections.sort(offering.getSections());
                }
            }
        }
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void addSubject(int semesterCode, String subjectName, String catalogNbr, String location,
                           int enrollmentCap, int enrollmentTotal, String instructor, String sectionType) {
        boolean isNewSubject = true;
        for (Subject subject: subjects) {
            if (subject.getName().equals(subjectName)) {
                isNewSubject = false;
                subject.addCourse(semesterCode, catalogNbr, location, enrollmentCap, enrollmentTotal, instructor, sectionType);
                break;
            }
        }

        if (isNewSubject) {
            subjects.add(new Subject(semesterCode, subjectName, catalogNbr, location,
                    enrollmentCap, enrollmentTotal, instructor, sectionType));
        }
    }

    public Subject getSubjectById(int subjectId) {
        if (subjectId < 0 || subjectId > subjects.size() - 1) throw new IllegalArgumentException();
        return subjects.get(subjectId);
    }
}
