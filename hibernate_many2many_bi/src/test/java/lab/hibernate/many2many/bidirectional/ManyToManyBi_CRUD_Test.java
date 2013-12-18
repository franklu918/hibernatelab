package lab.hibernate.many2many.bidirectional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Author: Frank
 * Date: 13-12-17
 */
public class ManyToManyBi_CRUD_Test {

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

    /**
     * 多对多保存测试
     * 双方都不设置Cascade
     * 没有设置Cascade，Session.save(obj)，只会保存obj对象，不会关联保存其他对象。
     * 关系中必须建立双方的关联关系
     */
    @Test
    public void save_NoCascade_Test() {
        Teacher teacher1 = new Teacher();
        teacher1.setName("t1");
        Teacher teacher2 = new Teacher();
        teacher2.setName("t2");

        Student student1 = new Student();
        student1.setName("s1");
        Student student2 = new Student();
        student2.setName("s2");

        // 多对多关联关系中以Teacher为主
        // 建立teacher到student的关联关系即可
        // 但为记忆方便，可以双方相互建立关联关系
        student1.getTeachers().add(teacher1);
        teacher1.getStudents().add(student1);

        student1.getTeachers().add(teacher2);
        teacher2.getStudents().add(student1);

        student2.getTeachers().add(teacher1);
        teacher1.getStudents().add(student2);

        student2.getTeachers().add(teacher2);
        teacher2.getStudents().add(student2);

        Session session = sf.getCurrentSession();
        session.beginTransaction();
        session.save(student1);
        session.save(student2);
        session.save(teacher1);
        session.save(teacher2);
        session.getTransaction().commit();

    }

    /**
     * 多对多级联保存
     * 设置其中一方为cascade即可
     */
    @Test
    public void save_cascade_test() {
        Teacher teacher1 = new Teacher();
        teacher1.setName("t1");
        Teacher teacher2 = new Teacher();
        teacher2.setName("t2");

        Student student1 = new Student();
        student1.setName("s1");
        Student student2 = new Student();
        student2.setName("s2");

        student1.getTeachers().add(teacher1);
        teacher1.getStudents().add(student1);

        student1.getTeachers().add(teacher2);
        teacher2.getStudents().add(student1);

        student2.getTeachers().add(teacher1);
        teacher1.getStudents().add(student2);

        student2.getTeachers().add(teacher2);
        teacher2.getStudents().add(student2);

        Session session = sf.getCurrentSession();
        session.beginTransaction();
//        session.save(student1);
//        session.save(student2);
        // 级联保存与teacher相关联的student
        session.save(teacher1);
        session.save(teacher2);
        session.getTransaction().commit();
    }

    /**
     * 多对多load或get
     * fetch默认为lazy
     */
    @Test
    public void getTest() {
        save_cascade_test();

        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Teacher teacher = (Teacher)session.get(Teacher.class, 1);
        for (Student student : teacher.getStudents()) {
            System.out.println(student.getName());
        }
        session.getTransaction().commit();
    }
}
