package todo.data.facade;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import todo.data.util.EMF;

public class TodoFacadeTest {

	private TodoFacade todoFacade = new TodoFacade();

	@BeforeClass
	public static void beforeClass() {
		System.setProperty("testing", "true");
	}

	@After
	public void after() {
		EMF.close();
	}

	@Test
	public void testCreate() {

		long id = todoFacade.create("Todo Item");
		Assert.assertTrue(id != 0);

	}

}
