package student.projectdemo1.service;

import org.springframework.data.domain.Page;
import student.projectdemo1.entity.Courses;
import student.projectdemo1.entity.Student;

import java.util.List;

public interface CourseService {

    Courses createCourse(Courses courses);

    List<Courses> getALl();

    Courses getById(Long cid);

    Courses updateCourse(Courses courses, Long cid);

    public void deleteCourse(Long cid);

    public void enrollStudentInCourse(Long sid,Long cid);

    Page<Courses> getAllCourses(int pagenumber, int pagesize);

    List<Courses> getCourseByCname(String cname);

    Student getStudentByid(Long sid);
}
