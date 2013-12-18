package lab.hibernate.one2one.unidirectional.foreignkey;

import javax.persistence.*;

/**
 * Author: Frank
 * Date: 13-12-13
 *
 * 一对一单向外键关联关系
 * 在关联方加 @OneToOne 注解
 * 通过 @JoinColumn（name=""）指定外键字段名
 *
 * 该例中Husband一对一关联到Wife
 * 在Husband中可以导航到Wife
 * 在Husband的Wife属性上加注解
 */
@Entity
public class Husband {
    private int id;
    private String name;
    private Wife wife;

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

    @OneToOne
    @JoinColumn(name = "wifeid")
    public Wife getWife() {
        return wife;
    }

    public void setWife(Wife wife) {
        this.wife = wife;
    }
}
