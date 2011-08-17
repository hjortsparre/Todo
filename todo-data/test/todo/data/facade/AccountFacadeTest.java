package todo.data.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import todo.data.entity.Account;
import todo.data.util.EMF;
import todo.data.util.TodoDataException;
import todo.data.util.TodoDataException.Type;

public class AccountFacadeTest {

	private AccountFacade accountFacade = new AccountFacade();

	@BeforeClass
	public static void beforeClass() {
		System.setProperty("testing", "true");
	}

	@After
	public void after() {
		EMF.close();
	}

	@Test
	public void testCreate() throws Exception {
		long id = accountFacade.create("marcus.bendtsen@liu.se", "secret");
		Assert.assertTrue(id != 0);
	}

	@Test
	public void testCreateEmaiNotlUnique() throws Exception {
		accountFacade.create("marcus.bendtsen@liu.se", "secret");
		try {
			accountFacade.create("marcus.bendtsen@liu.se", "secret");
		} catch (TodoDataException e) {
			Assert.assertEquals(TodoDataException.Type.EMAIL_NOT_UNIQUE,
					e.getType());
		}
	}

	@Test
	public void testFind() throws Exception {
		long id = accountFacade.create("marcus.bendtsen@liu.se", "secret");
		Account account = accountFacade.find(id);
		Assert.assertEquals(id, account.getId());
		Assert.assertEquals("marcus.bendtsen@liu.se", account.getEmail());
		Assert.assertEquals("secret", account.getPassword());
	}

	@Test
	public void testFindByEmail() throws Exception {
		long id = accountFacade.create("marcus.bendtsen@liu.se", "secret");
		Account account = accountFacade.findByEmail("marcus.bendtsen@liu.se");
		Assert.assertEquals(id, account.getId());
		Assert.assertEquals("marcus.bendtsen@liu.se", account.getEmail());
		Assert.assertEquals("secret", account.getPassword());
	}

	@Test
	public void testUpdateEmployeeConcurrency() throws Exception {

		final long accountId = accountFacade.create("marcus.bendtsen@liu.se",
				"secret");

		final List<Thread> threads = new ArrayList<Thread>();
		final HashMap<Thread, Type> failures = new HashMap<Thread, Type>();

		for (int i = 1000; i < 1200; i++) {
			final int c = i;
			threads.add(new Thread() {
				@Override
				public void run() {
					try {
						accountFacade.updateEmployeeId(accountId, c + "-abcd");
					} catch (TodoDataException e) {
						failures.put(this, e.getType());
					}
				}
			});
		}

		for (Thread thread : threads) {
			thread.start();
		}

		// Wait for all threads to die, or database will be dropped from
		// underneath the threads and will
		// cause incorrect failures
		for (Thread thread : threads) {
			thread.join();
		}

		Assert.assertTrue(failures.size() != 0);

		for (Type type : failures.values()) {
			Assert.assertEquals(Type.CONCURRENCY_VERSION_ERROR, type);
		}

	}
}
