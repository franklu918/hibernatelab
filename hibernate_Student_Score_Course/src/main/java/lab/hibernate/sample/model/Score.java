package lab.hibernate.sample.model;

import javax.persistence.*;

/**
 * Author: Frank
 * Date: 13-12-17
 */
@Entity
@Table(name = "t_score")
public class Score {

    private int id;

    private int score;

    private Student student;

    private Course course;

    public Score() {
    }

    public Score(int score) {
        this.score = score;
    }

    public Score(int score, Student student, Course course) {
        this.score = score;
        this.student = student;
        this.course = course;
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @ManyToOne
    @JoinColumn(name = "student_id")
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @ManyToOne
    @JoinColumn(name = "course_id")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
