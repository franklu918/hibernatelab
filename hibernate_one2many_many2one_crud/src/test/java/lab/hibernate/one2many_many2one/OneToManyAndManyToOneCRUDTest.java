package lab.hibernate.one2many_many2one;

import org.hibernate.Query;
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
 * Date: 13-12-13
 */
public class OneToManyAndManyToOneCRUDTest {

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
     * 先保存一方再保存多方
     * 不使用用级联
     *
     * 一方和多方都不设cascade
     *
     */
    @Test
    public void NoCascadeSaveTest() {
        Group group = new Group();
        group.setName("g1");

        User user = new User();
        user.setName("u1");
        user.setGroup(group);

        Session session = sf.getCurrentSession();
        session.beginTransaction();
        session.save(group);
        session.save(user);
        session.getTransaction().commit();
    }

    /**
     * 先保存多方再保存一方
     * 不使用用级联
     * 执行时多执行了update语句来设置关联关系
     *
     * 一方和多方都不设cascade
     */
    @Test
    public void NoCascadeSaveTest2() {
        Group group = new Group();
        group.setName("g1");

        User user = new User();
        user.setName("u1");
        user.setGroup(group);
        // 用下面的语句不能建立关联关系
        //group.getUsers().add(user);

        Session session = sf.getCurrentSession();
        session.beginTransaction();
        // 先保存User, Group还不存在, 执行insert into user ...,groupid为null
        session.save(user);
        // 保存Group, 执行insert into group ...
        // 同时还会执行update语句，将User的groupid进行更新
        session.save(group);
        session.getTransaction().commit();

    }

    /**
     * 多方级联保存
     * 在多方设置cascade
     *
     * 推荐使用
     */
    @Test
    public void ManySideCascadeSaveTest() {
        Group group = new Group();
        group.setName("g1");

        User user = new User();
        user.setName("u1");
        user.setGroup(group);

        User user2 = new User();
        user2.setName("u2");
        user2.setGroup(group);

        Session session = sf.getCurrentSession();
        session.beginTransaction();
        session.save(user);
        session.save(user2);
        session.getTransaction().commit();
    }

    /**
     * 更新
     * 处于persistent的对象
     */
    @Test
    public void updateTest() {
        int userId = saveSampleData();
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        User user = (User)session.load(User.class, userId);
        user.setName("updatedName");
        user.getGroup().setName("updateName");
        session.getTransaction().commit();
    }

    /**
     * 更新多方级联更新一方
     * 跨session，处于detached的对象的更新
     */
    @Test
    public void updateTest2() {
        int userId = saveSampleData();

        Session session = sf.getCurrentSession();
        session.beginTransaction();
        User user = (User)session.get(User.class, userId);
        session.getTransaction().commit();

        // user变为了detached对象
        user.setName("updated");
        user.getGroup().setName("updated");

        Session session1 = sf.getCurrentSession();
        session1.beginTransaction();
        session1.update(user);
        //级联更新一方
        session1.getTransaction().commit();

    }



    /**
     * 一方级联保存
     * 在一方设置cascade
     */
    @Test
    public void OneSideCascadeSaveTest() {
        Group group = new Group();
        group.setName("g1");

        User user = new User();
        user.setName("u1");
        user.setGroup(group);

        User user2 = new User();
        user2.setName("u2");
        user2.setGroup(group);

        group.getUsers().add(user);
        group.getUsers().add(user2);

        Session session = sf.getCurrentSession();
        session.beginTransaction();
        session.save(group);
        session.getTransaction().commit();
    }

    /**
     * cascade并不影响读取，影响增删改
     * Fetch影响读取
     */

    /**
     * 多方读取
     * 多方加了ManyToOne默认会把一方也取出来，不管设不设cascade
     * fetch类型默认为FetchType.LAZY
     */
    @Test
    public void ManySideReadTest() {
        int sampleId = saveSampleData();

        Session session = sf.getCurrentSession();
        session.beginTransaction();
        User user = (User)session.get(User.class, sampleId);
        session.getTransaction().commit();

    }

    /**
     * 一方读取
     * 多方OneToMany默认不会把多方数据取出来
     * 即fetch类型为FetchType.EAGER
     * @return
     */
    @Test
    public void OneSideReadTest() {
        int sampleGroupId = saveSampleData2();
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Group group = (Group)session.get(Group.class, sampleGroupId);
        session.getTransaction().commit();
    }

    /**
     * 删除多方级联删除一方
     * @return
     */
    @Test
    public void deleteUserTest() {
        int sampleId = saveSampleData();
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        User user = (User)session.load(User.class, sampleId);
        session.delete(user);
        session.getTransaction().commit();
    }

    /**
     * 删除多方
     * 如果设置了CascadeAll，可以先将关联关系设置为null再删除
     * @return
     */
    @Test
    public void deleteUserNoCascadeTest() {
        int sampleId = saveSampleData();
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        User user = (User)session.load(User.class, sampleId);
        user.setGroup(null);
        session.delete(user);
        session.getTransaction().commit();
    }

    /**
     * 删除多方
     * HQL方式
     * @return
     */
    @Test
    public void deleteUserNoCascadeTest2() {
        int sampleId = saveSampleData();
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("delete from User u where u.id = " + sampleId);
        q.executeUpdate();
        session.getTransaction().commit();
    }

    /**
     * 删除一方级联删除多方
     * 一方设置为Cascade
     * @return
     */
    @Test
    public void deleteGroupTest() {
        int groupId = saveSampleData2();
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Group g = (Group)session.load(Group.class, groupId);
        session.delete(g);
        session.getTransaction().commit();
    }

    /**
     * 删除一方不级联删除多方
     * 如果一方设置了Cascade，先将一方关联多方关系设置为null，再删除一方
     * @return
     */
    @Test
    public void deleteGroupNoCascadeTest() {
        int groupId = saveSampleData2();
        Session session = sf.getCurrentSession();
        session.beginTransaction();
        Group g = (Group)session.load(Group.class, groupId);
        g.setUsers(null);
        session.delete(g);
        session.getTransaction().commit();
    }

    private int saveSampleData() {
        Group group = new Group();
        group.setName("g1");

        User user = new User();
        user.setName("u1");
        user.setGroup(group);

        Session session = sf.getCurrentSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();

        return user.getId();
    }

    private int saveSampleData2() {
        Group group = new Group();
        group.setName("g1");

        User user = new User();
        user.setName("u1");
        user.setGroup(group);

        User user2 = new User();
        user2.setName("u2");
        user2.setGroup(group);

        Session session = sf.getCurrentSession();
        session.beginTransaction();
        session.save(user);
        session.save(user2);
        session.getTransaction().commit();

        return group.getId();
    }



}
