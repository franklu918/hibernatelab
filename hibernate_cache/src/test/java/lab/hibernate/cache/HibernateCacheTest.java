package lab.hibernate.cache;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class HibernateCacheTest {

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
	public void testSave() {
		Session session = sf.openSession();
		session.beginTransaction();
		
		for(int i=0; i<10; i++) {
			Category c = new Category();
			c.setName("c" + i);
			Topic t = new Topic();
			t.setCategory(c);
			t.setTitle("t" + i);
			t.setCreateDate(new Date());
			session.save(c);
			session.save(t);
		}
			
		session.getTransaction().commit();
		session.close();
	}

    /**
     * 使用一级缓存，即session级别的缓存
     */
	@Test
	public void testCache1() {
		Session session = sf.openSession();
		session.beginTransaction();
		Category c = (Category)session.load(Category.class, 1);
		System.out.println(c.getName());
		
		Category c2 = (Category)session.load(Category.class, 1);
		System.out.println(c2.getName());
		session.getTransaction().commit();
		session.close();
		
	}
	

    /**
     * 类使用二级缓存
     * 在Category中增加 @Cache 注解
     * Category使用二级缓存
     * join fetch
     */
	@Test
	public void testCache2() {
		Session session = sf.openSession();
		session.beginTransaction();
		Category c = (Category)session.load(Category.class, 1);
		System.out.println(c.getName());
		
		session.getTransaction().commit();
		session.close();
		
		Session session2 = sf.openSession();
		session2.beginTransaction();
		Category c2 = (Category)session2.load(Category.class, 1);
		System.out.println(c2.getName());
		
		session2.getTransaction().commit();
		session2.close();
	}

    /**
     * 查询缓存示例
     * 查询缓存依赖于二级缓存
     *
     */
	@Test
	public void testQueryCache() {
		Session session = sf.openSession();
		session.beginTransaction();
		List<Category> categories = (List<Category>)session.createQuery("from Category")
									.setCacheable(true).list();
		session.getTransaction().commit();
		session.close();
		
		Session session2 = sf.openSession();
		session2.beginTransaction();
		List<Category> categories2 = (List<Category>)session2.createQuery("from Category")
		.setCacheable(true).list();
		session2.getTransaction().commit();
		session2.close();
	}

}
