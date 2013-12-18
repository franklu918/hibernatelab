package lab.hibernate.one2many.unidirectional;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: Frank
 * Date: 13-12-13
 *
 * 一对多单向关联
 *
 * 在一方增加Set集合类型的属性，集合元素为一方类型
 *
 * 默认情况下会当成多对多的一种特殊情况处理：
 * 如果只在该一方属性集合上增加 @OneToMany 注解，会生成中间表，即数据库表会按多对多方式创建。
 *
 * 重要：必须增加 @JoinCloumn(name="") 才会在多方增加外键
 *
 * 本例中Group为一方，User为多方
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

    @OneToMany
    @JoinColumn(name = "groupid")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
