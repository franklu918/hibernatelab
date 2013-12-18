package lab.hibernate.many2many.unidirectional.xml;

import lab.hibernate.many2many.unidirectional.Student;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: Frank
 * Date: 13-12-14
 *
 * 多对多单向关联关系
 *
 */

public class XmlTeacher {

    private int id;

    private String name;

    private Set<Student> students = new HashSet<Student>();

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

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
