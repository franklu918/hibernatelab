package lab.hibernate.id;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Date;

/**
 * Author: Frank
 * Date: 13-12-10
 */
public class TeacherTest {

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
    /**
     * AUTO 生成策略
     * 默认id生成策略AUTO
     * selects IDENTITY, SEQUENCE or TABLE depending upon the capabilities of the underlying database.
     */
    public void autoIdTest() {
        Teacher teacher = new Teacher();
        teacher.setName("t1");
        teacher.setTitle("title");
        teacher.setBirthDay(new Date());
        teacher.setPosition(Position.A);

        Session session = sf.openSession();
        session.beginTransaction();
        session.save(teacher);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    /**
     * IDENTITY 生成策略
     * supports identity columns in DB2, MySQL, MS SQL Server, Sybase and HypersonicSQL.
     * The returned identifier is of type long, short or int.
     */
    public void identityIdTest() {
        Teacher3 teacher3 = new Teacher3();
        teacher3.setName("use identity");
        teacher3.setTitle("ttt");

        Session session = sf.openSession();
        session.beginTransaction();
        session.save(teacher3);
        session.getTransaction().commit();
        session.close();
    }


    /**
     * SEQUENCE 生成策略
     * uses a hi/lo algorithm to efficiently generate identifiers of type long, short or int,
     * given a named database sequence.
     * mysql用不了，可以在Oracle中使用
     *
     */
    @Test
    public void sequenceIdTest() {
        Teacher4 teacher4 = new Teacher4();
        teacher4.setName("use sequence");

        Session session = sf.openSession();
        session.beginTransaction();
        session.save(teacher4);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Table 方式ID生成策略
     */
    @Test
    public void tableIdTest() {
        Teacher5 teacher5 = new Teacher5();
        teacher5.setName("table generator");

        Session session = sf.openSession();
        session.beginTransaction();
        session.save(teacher5);
        session.getTransaction().commit();
        session.close();
    }

}
