package lab.hibernate.id;

import lab.hibernate.composite_id.CustomerOrder;
import lab.hibernate.composite_id.CustomerOrder2;
import lab.hibernate.composite_id.CustomerOrder3;
import lab.hibernate.composite_id.CustomerOrder4;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;
import java.util.Random;

/**
 * Author: Frank
 * Date: 13-12-12
 *
 * 测试联合主键
 *
 */
public class CustomerOrderTest {

    private static SessionFactory sf = null;

    @BeforeClass
    public static void setUp() {
        // Hiberante 4.* 必须这样写才不会出错
        Configuration cfg = new Configuration().configure(); //.configure()必须加装这里
        ServiceRegistry serviceRegistry =new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
        sf = cfg.buildSessionFactory(serviceRegistry);
    }

    @AfterClass
    public static void tearDown() {
        sf.close();
    }

    @Test
    public void compositeIdXmlTest() {
        CustomerOrder.Id id = new CustomerOrder.Id();
        id.setCustomerId(1);
        id.setOrderNumber(new Random().nextInt());

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setId(id);
        customerOrder.setOrderDate(new Date());

        Session session = sf.openSession();
        session.beginTransaction();
        session.save(customerOrder);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * 注解方式测试联合主键
     * 第一种注解方式：@Embeddable和@Id方式
     */
    @Test
    public void embeddableCompositeIdTest() {
        CustomerOrder2.CustomerOrderPK pk = new CustomerOrder2.CustomerOrderPK();
        pk.setCustomerId(1);
        pk.setOrderNumber(new Random().nextInt());

        CustomerOrder2 customerOrder2 = new CustomerOrder2();
        customerOrder2.setId(pk);
        customerOrder2.setOrderDate(new Date());

        Session session = sf.openSession();
        session.beginTransaction();
        session.save(customerOrder2);
        session.getTransaction().commit();
        session.close();

    }

    /**
     * 联合主键
     * 第二种注解方式：@EmbeddedId
     *
     */
    @Test
    public void embeddedIdCompositeIdTest() {
        CustomerOrder3.CustomerOrderPK pk = new CustomerOrder3.CustomerOrderPK();
        pk.setCustomerId(1);
        pk.setOrderNumber(new Random().nextInt());

        CustomerOrder3 customerOrder3 = new CustomerOrder3();
        customerOrder3.setId(pk);
        customerOrder3.setOrderDate(new Date());

        Session session = sf.openSession();
        session.beginTransaction();
        session.save(customerOrder3);
        session.getTransaction().commit();
        session.close();

    }

    /**
     * 注解方式联合主键
     * 第三种方式：@IdClass和@Id
     */
    @Test
    public void idClassCompositeIdTest() {
        CustomerOrder4 customerOrder4 = new CustomerOrder4();
        customerOrder4.setCustomerId(1);
        customerOrder4.setOrderNumber(new Random().nextInt());

        Session session = sf.openSession();
        session.beginTransaction();
        session.save(customerOrder4);
        session.getTransaction().commit();
        session.close();
    }


}
