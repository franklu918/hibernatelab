package lab.hibernate.coreapi;

import org.hibernate.FlushMode;
import org.hibernate.Query;
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
 * Date: 13-12-12
 */

/**
 *
 * 对象的三种状态：transient, persistent, detached
 *
 * transient：内存中一个对象，没有ID，缓存中也没有,数据库没有;
 * persistent：内存中有，缓存中有，数据库有ID
 * detached ：内存有，缓存没有，数据库有ID
 */
public class HibernateCoreApiTest {


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
     * save方法将对象状态从transient->persistent
     *
     */
    @Test
    public void saveTest() {
        Teacher teacher = new Teacher();
        teacher.setName("xxx");
        // 此时teacher 对象处于transient状态
        // 没有id，数据库也没有
        printTeacher("处于transient状态", teacher);

        // 使用getCurrentSession必须设置current_session_context_class
        // 否则汇报“No CurrentSessionContext configured ”异常
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        session.save(teacher);
        // 此时teacher处于persistent状态
        // 有id，数据库也有
        printTeacher("处于persistent状态", teacher);

        session.getTransaction().commit();

        printTeacher("处于detached状态", teacher);

    }

    @Test
    public void deleteTest() {
        Teacher teacher = new Teacher();
        teacher.setName("xxx");

        Session session = sf.getCurrentSession();
        session.beginTransaction();
        session.save(teacher);

        // teacher 处于persistent状态
        // delete方法将其变为transient状态
//        session.delete(teacher);
        printTeacher("delete方法调用后", teacher);
        session.getTransaction().commit();

        Session session2 = sf.getCurrentSession();
        session2.beginTransaction();

        // teacher 处于transient状态
        session2.delete(teacher);
        session2.getTransaction().commit();

    }

    /**
     * load返回的是代理对象，等到真正用到对象的内容时才发出sql语句
     * 会延迟,即commit后不能访问到数据;
     * 访问不存在的id时不报错[前提是不调用],因为没有执行sql语句;
     */
    @Test
    public void loadTest() {
        int sampleId = generateTeacherSample();

        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Teacher teacher = (Teacher)session.load(Teacher.class, sampleId);
        // 打印代理对象
        System.out.println(teacher.getClass().getName());
        session.getTransaction().commit();

        // 可以取到id
        System.out.println(teacher.getId());
        // session关闭后取不到数据
        System.out.println(teacher.getName());

    }

    /**
     * get直接从数据库加载，不会延迟
     */
    @Test
    public void getTest() {
        int sampleId = generateTeacherSample();
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Teacher teacher = (Teacher)session.get(Teacher.class, sampleId);
        System.out.println(teacher.getClass().getName());
        session.getTransaction().commit();

    }

    /**
     * update 将detached对象变为persistent对象
     */
    @Test
    public void updateDetachedObjectTest() {
        Teacher teacher = new Teacher();
        teacher.setName("update");

        Session session = sf.getCurrentSession();
        session.beginTransaction();
        session.save(teacher);

        //techear 变为detached对象
        session.getTransaction().commit();

        teacher.setName("new update");

        Session session1 = sf.getCurrentSession();
        session1.beginTransaction();
        session1.update(teacher);
        //teacher变为persistent对象
        session1.getTransaction().commit();

    }


    /**
     * 更新Transient对象会报错
     */
    @Test
    public void updateTransientObjectTest() {
        Teacher teacher = new Teacher();
        teacher.setName("update");

        Session session = sf.getCurrentSession();
        session.beginTransaction();
        session.update(teacher);
        session.getTransaction().commit();
        //
    }

    /**
     * 更新自己设定id的transient对象可以（前提是数据库有对应记录）
     */
    @Test
    public void updateTransientObjectWithIdTest() {
        int sampleId = generateTeacherSample();

        // transient 对象，设置id，数据库已存在该记录。
        Teacher teacher = new Teacher();
        teacher.setId(sampleId);
        teacher.setName("myupdate");

        Session session = sf.getCurrentSession();
        session.beginTransaction();
        session.update(teacher);
        session.getTransaction().commit();
    }

    /**
     * persistent状态的对象只要设定不同字段值就会发生更新
     * @return
     */
    @Test
    public void updatePersistentObjectTest(){
        int sampleId = generateTeacherSample();

        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Teacher teacher = (Teacher)session.get(Teacher.class, sampleId);
        // teacher 为persistent对象
        teacher.setName("autoupdate");
        // 字段改变会自动更新
        session.getTransaction().commit();

    }

    /**
     * 只更新修改的字段
     * 使用HQL方式
     * @return
     */
    @Test
    public void updatePartTest() {
        int sampleId = generateTeacherSample();
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("update Teacher t set t.name = 'partupdate' where t.id = " + sampleId);
        query.executeUpdate();
        session.getTransaction().commit();
    }


    /**
     * clear方法
     * 无论是load还是get，都会首先查找缓存（一级缓存）,
     * 如果没有，才会去数据库查找。
     * 调用clear方法可以强制清除session缓存
     */
    @Test
    public void clearTest() {
        int sampleId = generateTeacherSample();
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Teacher teacher = (Teacher)session.load(Teacher.class, sampleId);
        // 从数据库查询teacher，并加到缓存中
        printTeacher("从数据库查询后，从缓存中获取", teacher);

        printTeacher("已在缓存中，不需查数据库", teacher);
        // 清除缓存
        session.clear();
        Teacher teacher1 = (Teacher)session.load(Teacher.class, sampleId);
        System.out.println("调用clear清除缓存");
        printTeacher("缓存被清除，重新查数据库", teacher1);
        session.getTransaction().commit();

    }

    /**
     * flush()方法
     * 可以强制进行从内存到数据库的同步
     * 和数据库打交道(即clear()不能替代)
     * 进行commit()或rollback()时默认调用flush()
     * 可以设置FlushMode
     * @return
     */
    @Test
    public void flushTest() {
        int sampleId = generateTeacherSample();

        Session session = sf.getCurrentSession();
//        session.setFlushMode(FlushMode.AUTO);
        session.beginTransaction();
        Teacher teacher = (Teacher)session.get(Teacher.class, sampleId);

        teacher.setName("flush_update1");
        // 调用flush强制同步，会发出update语句
        // 若注释掉flush，则只会发一条update语句
        session.flush();

        teacher.setName("flush_update2");
        // commit中会调用flush方法发出第二条update语句
        session.getTransaction().commit();
    }

    /**
     *
     * @return
     */
    @Test
    public void schemaExportTest() {
        // 调用create会创建表,如果已在会被drop掉
        new SchemaExport( new Configuration().configure()).create(true,true);

    }

    private int generateTeacherSample() {
        Teacher teacher = new Teacher();
        teacher.setName("sample");
        int id = -1;
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        session.save(teacher);
        id = teacher.getId();
        session.getTransaction().commit();

        return id;
    }


    private void printTeacher(String msg, Teacher teacher) {
        System.out.println(msg);
        System.out.println("teacher: " + teacher.getId() + "," + teacher.getName());
    }
}
