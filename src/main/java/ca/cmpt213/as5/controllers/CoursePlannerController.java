package ca.cmpt213.as5.controllers;

import ca.cmpt213.as5.models.*;
import ca.cmpt213.as5.wrappers.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CoursePlannerController {
    private final String FILE_PATH = "data/course_data_2018.csv";

    private CsvFileReader reader = new CsvFileReader();
    List<List<String>> data = reader.getData(FILE_PATH);
    private DataManager manager = new DataManager(data);

    @GetMapping("/api/about")
    public ApiAboutWrapper getAboutMessage() {
        return new ApiAboutWrapper("Course Planner 2020", "Jason Nguyen");
    }

    @GetMapping("/api/dump-model")
    public void dumpModel() {
        for (Subject subject: manager.getSubjects()) {
            for (Course course: subject.getCourses()) {
                System.out.println(subject.getName() + " " + course.getCatalogNbr());
                for (Offering offering: course.getOfferings()) {
                    System.out.println("\t" + offering.getSemester().getCode() + " in " + offering.getLocation() + " by " + offering.getInstructors());
                    for (Section section: offering.getSections()) {
                        System.out.println("\t\tType=" + section.getType() + ", Enrollment=" + section.getEnrollmentTotal() + "/" + section.getEnrollmentCap());
                    }
                }
            }
        }
    }

    @GetMapping("/api/departments")
    public List<ApiDepartmentWrapper> getDepartments() {
        List<ApiDepartmentWrapper> list = new ArrayList<>();
        for (int subjectIndex = 0; subjectIndex < manager.getSubjects().size(); subjectIndex ++) {
            Subject subject = manager.getSubjectById(subjectIndex);

            list.add(new ApiDepartmentWrapper(subjectIndex, subject.getName()));
        }

        return list;
    }

    @GetMapping("/api/departments/{id}/courses")
    public List<ApiCourseWrapper> getCourses(@PathVariable("id") int subjectId) {
        List<ApiCourseWrapper> list = new ArrayList<>();

        Subject subject = manager.getSubjectById(subjectId);
        for (int courseIndex = 0; courseIndex < subject.getCourses().size(); courseIndex ++) {
            Course course = subject.getCourseById(courseIndex);
            list.add(new ApiCourseWrapper(courseIndex, course.getCatalogNbr()));
        }

        return list;
    }

    @GetMapping("/api/departments/{subjectId}/courses/{courseId}/offerings")
    public List<ApiCourseOfferingWrapper> getOfferings(@PathVariable("subjectId") int subjectId, @PathVariable("courseId") int courseId) {
        List<ApiCourseOfferingWrapper> list = new ArrayList<>();

        Subject subject = manager.getSubjectById(subjectId);
        Course course = subject.getCourseById(courseId);

        for (int offeringIndex = 0; offeringIndex < course.getOfferings().size(); offeringIndex ++) {
            Offering offering = course.getOfferingById(offeringIndex);
            Semester semester = offering.getSemester();

            list.add(new ApiCourseOfferingWrapper(offeringIndex, offering.getLocation(), offering.getInstructors(),
                    semester.getTerm(), Integer.parseInt(semester.getCode()), semester.getYear()));
        }

        return list;
    }

    @GetMapping("/api/departments/{subjectId}/courses/{courseId}/offerings/{offeringId}")
    public List<ApiOfferingSectionWrapper> getSections(@PathVariable("subjectId") int subjectId, @PathVariable("courseId") int courseId, @PathVariable("offeringId") int offeringId) {
        List<ApiOfferingSectionWrapper> list = new ArrayList<>();

        Subject subject = manager.getSubjectById(subjectId);
        Course course = subject.getCourseById(courseId);
        Offering offering = course.getOfferingById(offeringId);

        for (Section section: offering.getSections()) {
            list.add(new ApiOfferingSectionWrapper(section.getType(), section.getEnrollmentCap(), section.getEnrollmentTotal()));
        }

        return list;
    }

    @PostMapping("/api/addoffering")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOffering(@RequestBody OfferingJSON data) {
        manager.addSubject(data.getSemester(), data.getSubjectName(), data.getCatalogNumber(), data.getLocation(),
                data.getEnrollmentCap(), data.getEnrollmentTotal(), data.getInstructor(), data.getComponent());
        manager.sortData();
    }

    @GetMapping("/api/watchers")
    public List<ApiWatcherWrapper> getWatchers() {
        List<ApiWatcherWrapper> list = new ArrayList<>();

        int watcherIndex = 0;
        for (int subjectIndex = 0; subjectIndex < manager.getSubjects().size(); subjectIndex ++) {
            Subject subject = manager.getSubjectById(subjectIndex);

            for (int courseIndex = 0; courseIndex < subject.getCourses().size(); courseIndex ++) {
                Course course = subject.getCourseById(courseIndex);

                for (CourseObserver observer: course.getObservers()) {
                    ApiDepartmentWrapper subjectWrapper = new ApiDepartmentWrapper(subjectIndex, subject.getName());
                    ApiCourseWrapper courseWrapper = new ApiCourseWrapper(courseIndex, course.getCatalogNbr());

                    list.add(new ApiWatcherWrapper(watcherIndex, subjectWrapper, courseWrapper, observer.getEvents()));
                    watcherIndex ++;
                }
            }
        }

        return list;
    }

    @PostMapping("api/watchers")
    @ResponseStatus(HttpStatus.CREATED)
    public void addWatcher(@RequestBody WatcherJSON watcher) {
        int subjectId = watcher.getDeptId();
        int courseId = watcher.getCourseId();

        Subject subject = manager.getSubjectById(subjectId);
        Course course = subject.getCourseById(courseId);

        course.addObserver();
    }

    @GetMapping("api/watchers/{id}")
    public List<String> getWatcher(@PathVariable("id") int watcherId) {
        int watcherIndex = 0;

        for (Subject subject: manager.getSubjects()) {
            for (Course course: subject.getCourses()) {
                for (CourseObserver observer: course.getObservers()) {
                    if (watcherIndex == watcherId) {
                        return observer.getEvents();
                    }
                    watcherIndex ++;
                }
            }
        }

        throw new IllegalArgumentException();
    }

    @DeleteMapping("/api/watchers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWatcher(@PathVariable("id") int watcherId) {
        int watcherIndex = 0;

        for (Subject subject: manager.getSubjects()) {
            for (Course course: subject.getCourses()) {
                for (CourseObserver observer: course.getObservers()) {
                    if (watcherIndex == watcherId) {
                        course.getObservers().remove(observer);
                        return;
                    }
                    watcherIndex ++;
                }
            }
        }

        throw new IllegalArgumentException();
    }

    // Create Exception Handle
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Request ID not found.")
    @ExceptionHandler(IllegalArgumentException.class)
    public void badIdExceptionHandler() {}
}
