package student.projectdemo1.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import student.projectdemo1.entity.Courses;
import student.projectdemo1.entity.Student;
@Data
@Getter
@Setter
@Entity
@Table(name = "student_course")
//@SQLDelete(sql = "UPDATE student_course SET deleted = true WHERE id=?")
//@Where(clause = "deleted=false")
public class StudentCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String certification;

    @Column(name = "marks")
    private int marks;

//    @Column(name = "deleted" , columnDefinition = "BOOLEAN default false")
//    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Courses course;

    @Override
    public String toString() {
        return "StudentCourse{" +
                "id=" + id +
                ", certification='" + certification + '\'' +
                ", marks=" + marks +
                ", student=" + student +
                ", course=" + course +
                '}';
    }
// Constructors, getters, setters, and other necessary methods
}
