package lab.hibernate.id;

import javax.persistence.*;
import java.util.Date;

/**
 * Author: Frank
 * 默认生成策略，Auto
 * 在mysql中为autoincream，在oracle中为sequence
 * Date: 13-12-10
 */
@Entity
@Table(name = "teacher")

public class Teacher {

    private int id;

    private String name;

    private String title;

    private Date birthDay;

    private Position position;

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

    public String getTitle() {
        return title;

    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Temporal(TemporalType.DATE)
    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    @Enumerated(EnumType.ORDINAL)
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
