package todo.data.facade;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import todo.data.entity.Account;
import todo.data.util.EMF;
import todo.data.util.TodoDataException;

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

}
