package lab.hibernate.helloworld;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

/**
 * Author: Frank
 * Date: 13-12-10
 */
public class StudentTest {

    @Test
    public void helloworld() {
        Student student = new Student();
        student.setId(1);
        student.setName("FrankLu");
        student.setAge(25);

        Configuration cfg = new Configuration();
        SessionFactory sf = cfg.configure().buildSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(student);
        session.getTransaction().commit();
        session.close();
        sf.close();


    }
}
