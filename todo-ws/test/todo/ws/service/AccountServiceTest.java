package todo.ws.service;

import org.junit.Assert;
import org.junit.Test;

public class AccountServiceTest {

	private static final String accountEndpoint = "http://localhost:8080/todo-ws/rest/account";

	@Test
	public void testRegister() throws Exception {

		String outcome = HTTPHelper.get(accountEndpoint + "/register", "email",
				"marcus.bendtsen@liu.se", "password", "secret");

		Assert.assertNotNull(outcome);
		Assert.assertTrue(outcome.isEmpty() || outcome.equals("202"));

	}

}
