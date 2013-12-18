package lab.hibernate.coreapi;

import javax.persistence.*;
import java.util.Date;

/**
 * Author: Frank
 * Date: 13-12-12
 */
@Entity
public class Teacher {

    private int id;

    private String name;

    private Date birthDay;

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

    @Temporal(TemporalType.DATE)
    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }
}
