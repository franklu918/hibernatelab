package lab.hibernate.one2many.unidirectional;

import javax.persistence.*;

/**
 * Author: Frank
 * Date: 13-12-13
 *
 *一对多单向关联
 *
 * 本例中User为多方Group为一方
 *
 *
 */
@Entity
@Table(name = "t_user")
public class User {

    private int id;

    private String name;

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

}
