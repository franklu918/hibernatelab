package lab.hibernate.one2one.bidirectional.foreignkey;

import javax.persistence.*;

/**
 * Author: Frank
 * Date: 13-12-13
 *
 * 一对一双向外键关联
 * 在关联方和被关联方加 @OneToOne 注解
 * 为避免多余的外键关联
 * 在其中一方使用注解 @OneToOne(mappedBy="")
 *
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
