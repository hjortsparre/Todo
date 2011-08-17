package todo.logic.test;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import todo.data.entity.Todo;
import todo.data.util.EMF;
import todo.logic.bean.AccountBean;
import todo.logic.bean.TodoBean;
import todo.logic.util.TodoLogicException;

public class TodoBeanTest {

	private TodoBean todoBean = new TodoBean();
	private AccountBean accountBean = new AccountBean();

	@BeforeClass
	public static void setup() {
		System.setProperty("testing", "true");
	}

	@After
	public void tearDown() {
		EMF.close();
	}

	@Test(expected = TodoLogicException.class)
	public void testCheckInFailure() throws Exception {

		accountBean.register("marcus.bendtsen@liu.se", "secret");
		accountBean.register("marbe@ida.liu.se", "secret");

		Todo todo = todoBean.create("marcus.bendtsen@liu.se", "secret",
				"New Todo");

		todoBean.checkOut("marcus.bendtsen@liu.se", "secret", todo.getId());

		todoBean.checkIn("marbe@ida.liu.se", "secret", todo.getId());

	}
}
