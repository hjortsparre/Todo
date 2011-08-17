package todo.logic.test;

import junit.framework.Assert;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import todo.data.entity.Todo;
import todo.data.util.EMF;
import todo.logic.facade.AccountLogicFacade;
import todo.logic.facade.TodoLogicFacade;
import todo.logic.util.TodoLogicException;

public class TodoLogicFacadeTest {

	private TodoLogicFacade todoBean = new TodoLogicFacade();
	private AccountLogicFacade accountBean = new AccountLogicFacade();

	@BeforeClass
	public static void setup() {
		System.setProperty("testing", "true");
	}

	@After
	public void tearDown() {
		EMF.close();
	}

	@Test
	public void testCheckInFailure() throws Exception {

		accountBean.register("marcus.bendtsen@liu.se", "secret");
		accountBean.register("marbe@ida.liu.se", "secret");

		Todo todo = todoBean.create("marcus.bendtsen@liu.se", "secret",
				"New Todo");

		todoBean.checkOut("marcus.bendtsen@liu.se", "secret", todo.getId());

		try {
			todoBean.checkIn("marbe@ida.liu.se", "secret", todo.getId());
		} catch (TodoLogicException e) {
			Assert.assertEquals(
					TodoLogicException.Type.TODO_NOT_CHECKED_OUT_BY_THIS_ACCOUNT,
					e.getType());
		}
	}
}
