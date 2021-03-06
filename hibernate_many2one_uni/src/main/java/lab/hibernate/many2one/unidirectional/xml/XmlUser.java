package lab.hibernate.many2one.unidirectional.xml;

import javax.persistence.*;

/**
 * Author: Frank
 * Date: 13-12-13
 *
 * 多对一单向关联
 *
 * XML配置方式
 *
 *
 *
 */

public class XmlUser {

    private int id;

    private String name;

    private XmlGroup group;

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

    public XmlGroup getGroup() {
        return group;
    }

    public void setGroup(XmlGroup group) {
        this.group = group;
    }
}
