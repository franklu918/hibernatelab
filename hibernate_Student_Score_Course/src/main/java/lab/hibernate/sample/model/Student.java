package lab.hibernate.sample.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: Frank
 * Date: 13-12-17
 */
@Entity
@Table(name = "t_student")
public class Student {

    private int id;

    private String name;

    private Set<Score> scores = new HashSet<Score>();

    public Student(String name) {
        this.name = name;
    }

    public Student() {
    }

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

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    public Set<Score> getScores() {
        return scores;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }
}
