package lab.hibernate.many2one.unidirectional;

import javax.persistence.*;

/**
 * Author: Frank
 * Date: 13-12-13
 *
 * 多对一单向关联
 *
 * 多方包含关联到一方的属性
 * 并在该属性上添加 @ManyToOne 注解
 * 生成的数据表在多方中添加外键关联到一方
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
    @JoinColumn(name = "f_group_id")
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
