package lab.hibernate.id;

import javax.persistence.*;
import java.util.Date;

/**
 * Author: Frank
 * 使用TableSequence
 * Oracle用, mysql用不了
 * Date: 13-12-10
 */
@Entity
@Table(name = "teacher5")
@TableGenerator(
        name = "t5_GEN",
        table = "GENERATOR_TABLE",
        pkColumnName = "table_name",
        valueColumnName = "seq",
        pkColumnValue = "teacher5",
        allocationSize = 1
)
public class Teacher5 {

    private int id;

    private String name;

    private String title;

    private Date birthDay;

    private Position position;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "t5_GEN")
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
