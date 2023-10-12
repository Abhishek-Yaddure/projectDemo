package student.projectdemo1.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses")
//@SQLDelete(sql = "UPDATE courses SET isActive = true WHERE cid=?")
//@Where(clause = "isActive=false")
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cid;

    @Pattern(regexp = "^[A-Za-z]{3,15}$", message = "Invalid name. It should contain only alphabets and be between 3 and 15 characters in length.")
    private String cname;

    @Column(name = "is_active",columnDefinition = "boolean default false")
    private boolean isActive ;

    @NotNull(message = "Course fee should not be null")
    @NotEmpty(message = "course fee should not be empty ")
    private String cfee;

    @NotNull(message = "Duration should not be null")
    @NotEmpty(message = "Duration should not be Empty")
    private String duration;

    @JsonIgnore
    @ManyToMany( mappedBy = "courses",fetch = FetchType.LAZY)
    private List<Student> students = new ArrayList<>();
}
