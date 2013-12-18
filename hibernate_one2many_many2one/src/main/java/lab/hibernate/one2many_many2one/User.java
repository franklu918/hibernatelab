package lab.hibernate.one2many_many2one;

import javax.persistence.*;

/**
 * Author: Frank
 * Date: 13-12-13
 *
 *一对多和多对一关联（即一对多双向关联，或叫多对一双向关联）
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

    private Group group;

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

    @ManyToOne
    @JoinColumn(name = "groupid")
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
