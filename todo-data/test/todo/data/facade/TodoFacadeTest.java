package todo.data.facade;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import todo.data.entity.Todo;
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

	@Test
	public void testFind() {
		long id = todoFacade.create("Todo Item");

		Todo todo = todoFacade.find(id);

		Assert.assertEquals(id, todo.getId());
		Assert.assertEquals("Todo Item", todo.getName());
	}

	@Test
	public void testFindAll() {
		long id = todoFacade.create("Todo Item");

		List<Todo> todos = todoFacade.findAll();

		Assert.assertTrue(todos.size() > 0);
	}

	@Test
	public void testFindByName() {
		long id = todoFacade.create("Todo Item");

		Todo todo = todoFacade.findByName("Todo Item").get(0);

		Assert.assertEquals(id, todo.getId());
		Assert.assertEquals("Todo Item", todo.getName());
	}

}
