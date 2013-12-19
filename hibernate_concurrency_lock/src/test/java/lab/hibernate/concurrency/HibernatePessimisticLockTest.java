package lab.hibernate.concurrency;

import lab.hibernate.concurrency.pessimistic_lock.Account;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class HibernatePessimisticLockTest {
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
		
		Account a = new Account();
		a.setBalance(100);
		session.save(a);
			
		session.getTransaction().commit();
		session.close();
	}
	
	@Test
	public void testOperation1() {
		Session session = sf.openSession();
		session.beginTransaction();
		
		Account a = (Account)session.load(Account.class, 1);
		int balance = a.getBalance();
		//do some caculations
		balance = balance - 10;
		a.setBalance(balance);
		session.getTransaction().commit();
		session.close();
	}

    /**
     * 悲观锁
     * 用于防止不可重复读
     */
	@Test
	public void testPessimisticLock() {
		Session session = sf.openSession();
		session.beginTransaction();
		
		Account a = (Account)session.load(Account.class, 1, LockMode.UPGRADE);
		int balance = a.getBalance();
		//do some caculation
		balance = balance - 10;
		a.setBalance(balance);
		session.getTransaction().commit();
		session.close();
	}

}
