package todo.ws.service;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import todo.ws.dto.ResponseDTO;

import com.google.gson.Gson;

public class TodoServiceTest {

	private static final Gson gson = new Gson();
	private static final String todoEndpoint = "http://localhost:8080/todo-ws/rest/todo";
	private static final String accountEndpoint = "http://localhost:8080/todo-ws/rest/account";

	@After
	public void tearDown() throws Exception {
		HTTPHelper.get("http://localhost:8080/todo-ws/DatabaseServlet");
	}

	@Test
	public void testCreate() throws Exception {

		HTTPHelper.get(accountEndpoint + "/register", "email",
				"marcus.bendtsen@liu.se", "password", "secret");

		String outcome = HTTPHelper.get(todoEndpoint + "/create", "email",
				"marcus.bendtsen@liu.se", "password", "secret", "name",
				"This is a new todo task, needs to be done soone.");

		Assert.assertNotNull(outcome);

		ResponseDTO responseDTO = gson.fromJson(outcome, ResponseDTO.class);
		Assert.assertEquals(0, responseDTO.getCode());

	}
}
