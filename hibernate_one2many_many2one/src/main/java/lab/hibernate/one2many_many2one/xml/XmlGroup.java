package lab.hibernate.one2many_many2one.xml;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Frank
 * Date: 13-12-13
 *
 * 多对一多对一双向关联
 * XML配置方式
 *
 * 本例中Group为一方，User为多方
 *
 */

public class XmlGroup {

    private int id;

    private String name;

    private Set<XmlUser> users = new HashSet<XmlUser>();

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

    public Set<XmlUser> getUsers() {
        return users;
    }

    public void setUsers(Set<XmlUser> users) {
        this.users = users;
    }
}
