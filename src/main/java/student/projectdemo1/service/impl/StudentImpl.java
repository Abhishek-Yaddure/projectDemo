package student.projectdemo1.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import student.projectdemo1.dto.CertifiedRequest;
import student.projectdemo1.entity.Courses;
import student.projectdemo1.entity.Student;
import student.projectdemo1.entity.StudentCourse;
import student.projectdemo1.exception.ValidationException;
import student.projectdemo1.repository.CourseRepository;
import student.projectdemo1.repository.StudentCourseRepository;
import student.projectdemo1.repository.StudentRepository;
import student.projectdemo1.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Override
    public Student create(Student student) {
        Student student1 = null;
        List<Student> exitingStudent = studentRepository.findStudentByEmail(student.getEmail());
        if (exitingStudent.isEmpty()) {
            student.setActive(true);
            student1 = studentRepository.save(student);
        } else {
            throw new ValidationException("student is alredy present");
        }
        return student1;
    }

    @Override
    public List<Student> getAll() {
        return studentRepository.findByIsActive(true);
//        return studentRepository.findAll();
    }

    @Override
    public Student getByid(Long sid) {
        return studentRepository.findById(sid).get();
    }

    @Override
    public Student update(Student student, Long sid) {
        Student existingStudent = studentRepository.findById(sid).get();
        existingStudent.setName(student.getName());
        existingStudent.setAge(student.getAge());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setCourses(student.getCourses());
        return studentRepository.save(existingStudent);
    }
    @Override
    public boolean delete(Long sid) {
        Optional<Student> existingStudent = studentRepository.findById(sid);
        Student student = existingStudent.get();
        if (student.isActive() == false) {
            throw new ValidationException("student is already deleted");
        }
        student.setActive(Boolean.FALSE);
        studentRepository.save(student);

        if (existingStudent.isPresent()) {
//          studentRepository.deleteById(sid);
            return true;
        } else {
            return false;
        }
//          studentRepository.deleteById(sid);
    }

//    public void enrollStudentInCourse(Long studentId, Long courseId) {
//        Student student = studentRepository.findById(studentId).orElse(null);
//        Courses course = courseRepository.findById(courseId).orElse(null);
//        if (student != null && course != null) {
//            student.getCourses().add(course);
//            course.getStudents().add(student);
//            studentRepository.save(student);
//            courseRepository.save(course);
//        }
//    }

    public void enrollStudentInCourse(String name, String cname) {

        Student student = studentRepository.findByName(name);
        Courses course = courseRepository.findByCname(cname);



        if (student != null && course != null) {
            student.getCourses().add(course);
            course.getStudents().add(student);
            studentRepository.save(student);
            courseRepository.save(course);
        }
    }

    @Override
    public List<Student> getStudentBasedOnCourse(Long courses) {
             List<Student> students = studentRepository.findStudentBasedOnCourse(courses);
             if(students.isEmpty()){
                 throw new ValidationException("there is no Students with this number of " +courses+ " courses ");
             }
           return students;
    }

    @Override
    public List<Student> getByCourseName(String cname) {
        return studentRepository.findBYCourseNBame(cname);
    }

    @Override
    public Page<Student> getStudentpagination(int pagenumber, int pagesize) {
        Sort sort = Sort.by(Sort.Direction.ASC, "sid");
        Pageable pageable = PageRequest.of(pagenumber, pagesize, sort);
        return studentRepository.findAll(pageable);
    }

//    @Override
//    public List<Student> getByCertification(String certification) {
//        List<Student> students =studentRepository.findByCertification(certification);
////        System.out.println("students"+students);
//        return students;
//    }

    @Override
    public Student getStudentWithCourses(Long sid) {
        return studentRepository.findById(sid).get();
    }

    @Override
    public String studentCertified(CertifiedRequest certifiedRequest) {
        Courses courses = courseRepository.findByCname(certifiedRequest.getCname());
        if (courses == null) {
            throw new ValidationException("course is not found");
        }
        Optional<Student> exitingStudent = studentRepository.findByEmail(certifiedRequest.getEmail());
        StudentCourse studentCourse = studentCourseRepository.findByStudentAndCourse(exitingStudent.get(), courses);
        if (studentCourse == null) {
            throw new ValidationException("Student not Enrolled to course..!!!!");
        }
        if (exitingStudent.isEmpty()) {
            throw new ValidationException("student is not found");
        } else {
            if (certifiedRequest.getMarks() >= 70) {
                studentCourse.setCertification("Certified");
                studentCourse.setMarks(certifiedRequest.getMarks());
                studentCourseRepository.save(studentCourse);
            } else {
                studentCourse.setMarks(certifiedRequest.getMarks());
                studentCourseRepository.save(studentCourse);
                throw new ValidationException("Not Eligible for Certification....! ");
            }
        }
        return "Congratulation...! Certified ";
    }

    @Override
    public List<Student> getStudentByemail(String email) {
        return studentRepository.findStudentByEmail(email);
    }


//    @Override
//    public Courses getStudentIdWithCourses(long id) {
//        Student student=studentRepository.findById(id).get();
//        StudentCourse studentCourses=studentCourseRepository.findByStudentAndCourse(student);
//        List<Long> ids=studentCourses.stream().map(st->st.getCourse().getCid()).collect(Collectors.toList());
//        Courses coursesList=courseRepository.findById(studentCourses.getCourse().getCid()).get();
//        return coursesList;
//    }
}
