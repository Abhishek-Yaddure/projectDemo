package student.projectdemo1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import student.projectdemo1.entity.Courses;
import student.projectdemo1.entity.Student;
import student.projectdemo1.entity.StudentCourse;

import java.util.List;
import java.util.Optional;

public interface StudentCourseRepository extends JpaRepository<StudentCourse,Long> {
    StudentCourse findByStudentAndCourse(Student student, Courses courses);
//    @Query("SELECT sc FROM StudentCourse sc WHERE sc.student.id = :studentId")
//    List<StudentCourse> findByCourseByName(String cname);
//    StudentCourse findByStudent(Student student);
}
