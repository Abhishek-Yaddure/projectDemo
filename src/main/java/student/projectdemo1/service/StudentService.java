package student.projectdemo1.service;

import org.springframework.data.domain.Page;
import student.projectdemo1.CourseRequest;
import student.projectdemo1.dto.CertifiedRequest;
import student.projectdemo1.entity.Courses;
import student.projectdemo1.entity.Student;

import java.util.List;

public interface StudentService {
    Student create(Student student);
    List<Student> getAll();
    Student getByid(Long sid);
    Student update(Student student, Long sid);
    boolean delete(Long sid);

    public void enrollStudentInCourse(String name, String cname);

    List<Student> getStudentBasedOnCourse(Long courses);
    List<Student> getByCourseName(String cname);
    Page<Student> getStudentpagination(int pagenumber, int pagesize);
//    List<Student> getByCertification(String certification);

    Student  getStudentWithCourses(Long sid);

    String studentCertified(CertifiedRequest certifiedRequest);

    List<Student> getStudentByemail(String email);
//    Courses getStudentIdWithCourses(long id);

//    public Page<Student> getStudentDetails(Long sid, String email, int pagenumber, int pagesize);
}
