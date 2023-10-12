package student.projectdemo1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import student.projectdemo1.entity.Courses;
import student.projectdemo1.entity.Student;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Courses, Long> {
    Courses findByCname(String cname);

    List<Courses> findCourseByCname(String cname);

    List<Courses> findByIsActive(boolean active);

//    Optional<Courses> findCourseByStudentId(Long studentId);
}