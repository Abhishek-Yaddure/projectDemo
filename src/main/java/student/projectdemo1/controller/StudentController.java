package student.projectdemo1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.projectdemo1.dto.CertifiedRequest;
import student.projectdemo1.entity.Courses;
import student.projectdemo1.entity.Student;
import student.projectdemo1.service.CourseService;
import student.projectdemo1.service.StudentService;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/save")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return new ResponseEntity<>(studentService.create(student), HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Student>> getAll() {
//        studentService.getStudentBasedOnCourse(courses);
//        studentService.getStudentWithCourses(sid);
        return ResponseEntity.ok(studentService.getAll());
    }

    @GetMapping("/{sid}")
    public ResponseEntity<Student> getStudentById(@PathVariable("sid") Long sid) {
        return new ResponseEntity<>(studentService.getByid(sid), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student, @PathVariable("id") Long sid) {
        return new ResponseEntity<>(studentService.update(student, sid), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long sid) {
        boolean deleted= studentService.delete(sid);
        return "deleted sucsessfully";
    }

    @GetMapping("/getByCourse/{numberOfCourse}")
    public ResponseEntity<List<Student>> getByNumberOfCourse(@PathVariable("numberOfCourse") Long courses) {
        return ResponseEntity.ok(studentService.getStudentBasedOnCourse(courses));
    }

    @GetMapping("/get/{cname}")
    public List<Student> getByCourseName(@PathVariable("cname") String cname) {
        return studentService.getByCourseName(cname);
    }

    @GetMapping("/pagination")
    public Page<Student> studentPagination(@RequestParam(defaultValue = "0", required = false) Integer pagenumber, @RequestParam(defaultValue = "5", required = false) Integer pagesize) {
        return studentService.getStudentpagination(pagenumber, pagesize);
    }

    @PostMapping("/enroll")
    public String enrollCourse(@RequestParam String name, @RequestParam String cname) {
        studentService.enrollStudentInCourse(name, cname);
        return "Enrolled successfully";
    }

    @GetMapping("/getStudentwithcourse/{studentid}")
    public Student getstudentWithCourse(@PathVariable("studentid")  Long sid) {
        return studentService.getStudentWithCourses(sid);
    }

    @PostMapping("/Certified")
    public String studentCertified(@RequestBody CertifiedRequest certifiedRequest) {
        return studentService.studentCertified(certifiedRequest);
    }

    @GetMapping("/getbymail/{email}")
    public List<Student> students(@PathVariable("email") String email){
        return studentService.getStudentByemail(email);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(
            @RequestParam(value = "type", required = true) String type,
            @RequestParam(value = "courseId", required = false) Long courseId,
            @RequestParam(value = "studentId", required = false) Long studentId,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "numberofcourse", required = false) Long numberofcourse,
            @RequestParam(value = "pagination", defaultValue = "0",required = false) int pagenumber, @RequestParam(value = "pagination", defaultValue = "5",required = false)int pagesize

    ) {
        switch (type) {
            case "getAll":
                List<Student> allStudents = studentService.getAll();
                return ResponseEntity.ok(allStudents);

            case "getById":
                if (studentId != null) {
                    Student student = studentService.getByid(studentId);
                    if (student != null) {
                        return ResponseEntity.ok(student);
                    }
                }
                // Handle not found case
            case "getByCourse":
                if (courseId != null) {
                    List<Student> studentsInCourse = studentService.getStudentBasedOnCourse(courseId);
                    if (studentsInCourse != null) {
                        return ResponseEntity.ok(studentsInCourse);
                    }
                }
                return ResponseEntity.badRequest().body("Please provide a proper course ID.");
            case "getcoursebyid":
                if (cid != null) {
                    Courses courses = courseService.getById(cid);
                    return ResponseEntity.ok(courses);
                }
                // Handle not found case
            case "getstudentbymail":
               if (email != null) {
                    List<Student> students = studentService.getStudentByemail(email);
                    return ResponseEntity.ok(students);
                }

            case "paging":
                    Page<Student> students = studentService.getStudentpagination(pagenumber,pagesize);
                    return ResponseEntity.ok(students);

                // Handle not found case
            default:
                return ResponseEntity.badRequest().body("Invalid search type.");
        }
    }
}

