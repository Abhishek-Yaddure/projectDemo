package student.projectdemo1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import student.projectdemo1.entity.Courses;
import student.projectdemo1.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s JOIN s.courses c GROUP BY s HAVING COUNT(c) >= :courses")
    List<Student> findStudentBasedOnCourse(Long courses);

    @Query("select s from Student s join s.courses c where c.cname = :cname ")
    List<Student> findBYCourseNBame(@Param("cname") String cname);

 //  List<Student> findByCertification(String certification);

   Student  findByName(String name);

    @Query("SELECT DISTINCT s FROM Student s JOIN FETCH s.courses WHERE s.sid = :studentId")
    Student findStudentWithCourses(@PathVariable("studentId") Long studentId);

    Optional<Student> findByEmail(String email);
    List<Student> findStudentByEmail(String email);
    List<Student> findByIsActive(boolean active);


}



