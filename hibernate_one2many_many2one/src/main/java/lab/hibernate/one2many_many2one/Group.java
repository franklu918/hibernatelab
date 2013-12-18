package lab.hibernate.one2many_many2one;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: Frank
 * Date: 13-12-13
 *
 * 一对多和多对一关联（即一对多双向关联，或叫多对一双向关联）
 *
 * 在一方的 @OneToMany 必须加 mappedBy属性，否则在数据表会生成两个外键关联关系
 * 加mappedBy，则只使用多方为主定义的外键关联关系。
 *
 * 步骤：
 * 1. 一方增加多方对象集合属性
 * 2. 在一方的多方对象集合属性上加注解 @OneToMany(mappedBy="多方中的一方属性")
 * 3. 多方增加一方属性
 * 4. 在多方的一方属性上加注解 @ManyToOne，如需指定多方表中外键属性名可以加 @JoinColumn(name="")
 *
 */
@Entity
@Table(name = "t_group")
public class Group {

    private int id;

    private String name;

    private Set<User> users = new HashSet<User>();

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

    /**
     * 如果没有加mappedBy，在数据表会产生两个外键关联
     * @return
     */
    @OneToMany(mappedBy = "group")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
