package lab.hibernate.many2one.unidirectional.xml;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Author: Frank
 * Date: 13-12-13
 *
 * 多对一单向关联
 * XML配置方式
 *
 * 本例中Group为一方，User为多方
 *
 */

public class XmlGroup {

    private int id;

    private String name;

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
