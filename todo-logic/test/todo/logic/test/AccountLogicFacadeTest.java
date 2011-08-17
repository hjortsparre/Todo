package todo.logic.test;

import junit.framework.Assert;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import todo.data.entity.Account;
import todo.data.util.EMF;
import todo.logic.facade.AccountLogicFacade;
import todo.logic.util.TodoLogicException;

public class AccountLogicFacadeTest {

	private AccountLogicFacade accountBean = new AccountLogicFacade();

	@BeforeClass
	public static void setupClass() {
		System.setProperty("testing", "true");
	}

	@After
	public void tearDown() {
		EMF.close();
	}

	@Test
	public void testRegisterAndLogin() throws Exception {
		accountBean.register("marcus.bendtsen@gmail.com", "secret");
		Account account = accountBean.login("marcus.bendtsen@gmail.com",
				"secret");
		Assert.assertNotNull(account);
	}

	@Test
	public void testLoginFailure() throws Exception {
		try {
			accountBean.login("marcus.bendtsen@gmail.com", "secret");
		} catch (TodoLogicException e) {
			Assert.assertEquals(TodoLogicException.Type.AUTHENTICATION_FAILED,
					e.getType());
		}
	}

}
