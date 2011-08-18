package todo.ws.service;

import junit.framework.Assert;

import org.junit.Test;

public class TodoServiceTest {

	private static final String endpoint = "http://localhost:8080/todo-ws/rest/todo";
	private static final String accountEndpoint = "http://localhost:8080/todo-ws/rest/account";

	@Test
	public void testCreate() throws Exception {

		HTTPHelper.get(accountEndpoint + "/register", "email",
				"marcus.bendtsen@liu.se", "password", "secret");
		
		String outcome = HTTPHelper.get(endpoint + "/create", "email",
				"marcus.bendtsen@liu.se", "password", "secret", "name",
				"This is a new todo task, needs to be done sone.");

		Assert.assertNotNull(outcome);
		Assert.assertTrue(outcome.isEmpty());

	}
	
	@Test
	public void testCheckOut() throws Exception {
		
		HTTPHelper.get(accountEndpoint + "/register", "email",
				"marcus.bendtsen@liu.se", "password", "secret");
		
		String outcome = HTTPHelper.get(endpoint + "/check/out", "email",
				"marcus.bendtsen@liu.se", "password", "secret", "name",
				"This is a new todo task, needs to be done sone.");

		Assert.assertNotNull(outcome);
		Assert.assertTrue(outcome.isEmpty());
	}
}
