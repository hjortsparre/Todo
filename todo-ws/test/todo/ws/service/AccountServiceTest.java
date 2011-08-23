package todo.ws.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import todo.ws.dto.ResponseDTO;

import com.google.gson.Gson;

public class AccountServiceTest {

	private static final Gson gson = new Gson();
	private static final String accountEndpoint = "http://localhost:8080/todo-ws/rest/account";

	@After
	public void tearDown() throws Exception {
		HTTPHelper.get("http://localhost:8080/todo-ws/DatabaseServlet");
	}
	
	@Test
	public void testRegister() throws Exception {

		String outcome = HTTPHelper.get(accountEndpoint + "/register", "email",
				"marcus.bendtsen@liu.se", "password", "secret");

		Assert.assertNotNull(outcome);

		ResponseDTO responseDTO = gson.fromJson(outcome, ResponseDTO.class);

		Assert.assertEquals(0, responseDTO.getCode());
	}

}
