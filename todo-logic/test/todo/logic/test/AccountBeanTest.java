package todo.logic.test;

import junit.framework.Assert;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import todo.data.entity.Account;
import todo.data.util.EMF;
import todo.logic.bean.AccountBean;
import todo.logic.util.TodoLogicException;

public class AccountBeanTest {

	private AccountBean accountBean = new AccountBean();

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

	@Test(expected = TodoLogicException.class)
	public void testLoginFailure() throws Exception {
		accountBean.login("marcus.bendtsen@gmail.com", "secret");
	}

}
