package student.projectdemo1.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student")
//@SQLDelete(sql = "UPDATE student SET isActive = true WHERE sid=?")
//@Where(clause = "isActive=false")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sid;

    @Pattern(regexp = "^[A-Za-z]{3,15}$", message = "Invalid name. It should contain only alphabets and be between 3 and 15 characters in length.")
    @Column(name = "name")
    private String name;

    @Column(name = "age")
    @Min(value = 18, message = "Age should be at least 18")
    @Max(value = 35, message = "Age should not exceed 35")
    private int age;

    @Column(name = "is_active", columnDefinition = "boolean default false")
    private boolean isActive;

    @Column(unique = true)
    @Email(message = "email format inValid ")
    @NotNull(message = " email should not null")
    @NotEmpty(message = "email should not be empty")
    private String email;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Courses> courses = new ArrayList<>();

//    @OneToMany(cascade = CascadeType.ALL)
//    private List<StudentCourse> studentCourse;

//    @Column(table = "student_course", name = "certification")
//    private String certification;
}
