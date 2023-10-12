package student.projectdemo1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import student.projectdemo1.entity.Courses;
import student.projectdemo1.entity.Student;
import student.projectdemo1.service.CourseService;
import student.projectdemo1.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;
    @PostMapping("/save")
    public ResponseEntity<Courses> createCourse(@RequestBody Courses courses){
        return new ResponseEntity<>(courseService.createCourse(courses), HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public List<Courses> getAll() {
        return courseService.getALl();
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Courses> getById(@PathVariable("id") Long cid){
        return new ResponseEntity<>(courseService.getById(cid),HttpStatus.OK) ;
    }

    @PutMapping("/updateCourse/{id}")
    public ResponseEntity<Courses> updateCourse(@RequestBody Courses courses,@PathVariable("id") Long cid){
        return new ResponseEntity<>(courseService.updateCourse(courses,cid),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public String deletCourse(@PathVariable("id") Long cid){
        courseService.deleteCourse(cid);
        return "course Deleted Successfully";
    }

    @PostMapping("/enroll")
    public String enrollStudentById(Long sid,Long cid){
        courseService.enrollStudentInCourse(sid,cid);
        return "enrolled sucessfully";
    }

    @GetMapping("/pagination")
    public Page<Courses> allCourse(@RequestParam(defaultValue ="0", required = false) int pagenumber, @RequestParam(defaultValue = "2",required = false) int pagesize){
        return courseService.getAllCourses(pagenumber, pagesize);
    }

    @GetMapping("/getByStudentId/{id}")
    public ResponseEntity<Student> getStudentid(@PathVariable("id") Long sid){
        return new ResponseEntity<>(studentService.getByid(sid),HttpStatus.OK);
    }


    }



