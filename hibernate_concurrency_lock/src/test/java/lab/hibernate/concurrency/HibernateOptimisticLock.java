package lab.hibernate.concurrency;


import lab.hibernate.concurrency.optimistic_lock.Account1;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class HibernateOptimisticLock {
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
        new SchemaExport( new Configuration().configure()).create(true,true);

    }

	@Test
	public void testSave() {
		Session session = sf.openSession();
		session.beginTransaction();

		Account1 a = new Account1();
		a.setBalance(100);
		session.save(a);

		session.getTransaction().commit();
		session.close();
	}

    /**
     * 乐观锁
     * 防止不可重复读问题
     */
	@Test
	public void testOptimisticLock() {
		Session session = sf.openSession();

		Session session2 = sf.openSession();

		
		session.beginTransaction();
		Account1 a1 = (Account1) session.load(Account1.class, 1);
		

		session2.beginTransaction();
		Account1 a2 = (Account1) session2.load(Account1.class, 1);
		
		a1.setBalance(900);
		a2.setBalance(1100);

		session.getTransaction().commit();
		System.out.println(a1.getVersion());

		session2.getTransaction().commit();
		System.out.println(a2.getVersion());

		session.close();
		session2.close();
	}


}
