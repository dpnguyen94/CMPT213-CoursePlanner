package ca.cmpt213.as5.models;

public class Semester implements Comparable<Semester> {
    private String code;
    private String term;
    private int year;

    public Semester(int semesterCode) {
        code = Integer.toString(semesterCode);

        switch (code.charAt(3)) {
            case '1': term = "Spring"; break;
            case '4': term = "Summer"; break;
            case '7': term = "Fall"; break;
            default: term="N/A"; break;
        }

        year = 1900 + 100 * Character.getNumericValue(code.charAt(0))
                + 10 * Character.getNumericValue(code.charAt(1))
                + Character.getNumericValue(code.charAt(2));
    }

    public String getCode() {
        return code;
    }

    public String getTerm() {
        return term;
    }

    public int getYear() {
        return year;
    }

    @Override
    public int compareTo(Semester otherSemester) {
        return code.compareTo(otherSemester.code);
    }
}
