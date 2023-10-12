package student.projectdemo1.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import student.projectdemo1.entity.Courses;
import student.projectdemo1.entity.Student;
import student.projectdemo1.exception.ValidationException;
import student.projectdemo1.repository.CourseRepository;
import student.projectdemo1.repository.StudentRepository;
import student.projectdemo1.service.CourseService;

import java.util.List;

@Service
public class CourseImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Courses createCourse(Courses courses) {
        Courses courses1 = null;
        List<Courses> existingCourses = courseRepository.findCourseByCname(courses.getCname());
        if (existingCourses.isEmpty()){
            courses.setActive(true);
            courses1 = courseRepository.save(courses);
        } else{
            throw new ValidationException("Couse is already preasent");
        }
        return courses1;
}

    @Override
    public List<Courses> getALl() {
//        return courseRepository.findAll();
        return courseRepository.findByIsActive(true);
    }

    @Override
    public Courses getById(Long cid) {
        return courseRepository.findById(cid).get();
    }

    @Override
    public Courses updateCourse(Courses courses, Long cid) {
        Courses existingCourse= courseRepository.findById(cid).get();
        existingCourse.setCname(courses.getCname());
        existingCourse.setCfee(courses.getCfee());
        existingCourse.setDuration(courses.getDuration());
        return courseRepository.save(existingCourse);
    }

    @Override
    public void deleteCourse(Long cid) {
        courseRepository.findById(cid).get();
        courseRepository.deleteById(cid);
    }

    @Override
    public void enrollStudentInCourse(Long sid,Long cid) {

        Student student = studentRepository.findById(sid).get();

        Courses course = courseRepository.findById(cid).orElseThrow(()->new ValidationException("No Course present with this id"));

        if(student.isActive()==false){
            throw new ValidationException("Student is Not Active");
        }

        if(course.isActive()==false){
            throw new ValidationException("Course is Not Active");
        }

        if (student.getCourses().contains(course)) {
            throw new ValidationException("Student is already enrolled in this course.");
        }

        if (student != null && course != null) {
            student.getCourses().add(course);
            course.getStudents().add(student);
            studentRepository.save(student);
            courseRepository.save(course);
        }
    }

    @Override
    public Page<Courses> getAllCourses(int pagenumber, int pagesize) {
        Sort sort = Sort.by(Sort.Direction.ASC ,  "cid" );
        Pageable pageable = PageRequest.of(pagenumber,pagesize,sort);
        return courseRepository.findAll(pageable);
    }

    @Override
    public List<Courses> getCourseByCname(String cname) {
        return courseRepository.findCourseByCname(cname);
    }

    @Override
    public Student getStudentByid(Long sid) {
        return studentRepository.findById(sid).get();
    }
}



