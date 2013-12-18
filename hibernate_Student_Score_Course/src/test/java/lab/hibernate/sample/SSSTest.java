package lab.hibernate.sample;

import lab.hibernate.sample.model.Course;
import lab.hibernate.sample.model.Score;
import lab.hibernate.sample.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Author: Frank
 * Date: 13-12-13
 */
public class SSSTest {

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
    public void crateTableTest() {
        // 调用create会创建表,如果已在会被drop掉
        new SchemaExport(new Configuration().configure()).create(true,true);

    }

    @Test
    public void saveScoreTest() {
        Student student = new Student("七匹狼");
        Student student1 = new Student("马克华菲");
        Course course = new Course("电子商务");
        Course course1 = new Course("全网营销");

        Session session = sf.getCurrentSession();
        session.beginTransaction();
        session.save(student);
        session.save(student1);
        session.save(course);
        session.save(course1);
        session.getTransaction().commit();

        Score score = new Score(80,student,course);
        Score score1 = new Score(60,student,course1);
        Score score2 = new Score(70, student1, course);

        Session session1 = sf.getCurrentSession();
        session1.beginTransaction();
        session1.save(score);
        session1.save(score1);
        session1.save(score2);
        session1.getTransaction().commit();

    }

    @Test
    public void getTest() {

        saveScoreTest();

        Student student = null;

        Session session = sf.getCurrentSession();
        session.beginTransaction();
        student = (Student)session.get(Student.class, 2);
        System.out.println(student.getName());
        for (Score score : student.getScores()) {
            System.out.println("course:" + score.getCourse().getName() + "," +
                    "score:" + score.getScore());
        }
        session.getTransaction().commit();

    }


}
