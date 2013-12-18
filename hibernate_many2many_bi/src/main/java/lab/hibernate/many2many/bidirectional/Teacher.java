package lab.hibernate.many2many.bidirectional;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: Frank
 * Date: 13-12-14
 *
 * 多对多双向关联关系
 *
 */
@Entity
@Table(name = "t_teacher")
public class Teacher {

    private int id;

    private String name;

    private Set<Student> students = new HashSet<Student>();

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "t_teacher_student",
            joinColumns = @JoinColumn(name = "teacherid", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "studentid", referencedColumnName = "id")
    )
    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
