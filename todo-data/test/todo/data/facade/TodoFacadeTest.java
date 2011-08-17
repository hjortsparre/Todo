package todo.data.facade;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import todo.data.entity.Account;
import todo.data.entity.Todo;
import todo.data.util.EMF;
import todo.data.util.TodoDataException;

public class TodoFacadeTest {

	private TodoFacade todoFacade = new TodoFacade();
	private AccountFacade accountFacade = new AccountFacade();

	@BeforeClass
	public static void beforeClass() {
		System.setProperty("testing", "true");
	}

	@After
	public void after() {
		EMF.close();
	}

	@Test
	public void testCreate() throws Exception {
		long id = todoFacade.create("Todo Item");
		Assert.assertTrue(id != 0);
	}

	@Test
	public void testFind() throws Exception {
		long id = todoFacade.create("Todo Item");

		Todo todo = todoFacade.find(id);

		Assert.assertEquals(id, todo.getId());
		Assert.assertEquals("Todo Item", todo.getName());
	}

	@Test
	public void testFindAll() throws Exception {
		long id = todoFacade.create("Todo Item");

		List<Todo> todos = todoFacade.findAll();

		Assert.assertTrue(todos.size() > 0);
	}

	@Test
	public void testFindByName() throws Exception {
		long id = todoFacade.create("Todo Item");

		Todo todo = todoFacade.findByName("Todo Item").get(0);

		Assert.assertEquals(id, todo.getId());
		Assert.assertEquals("Todo Item", todo.getName());
	}

	@Test
	public void testFindByNameNonExisting() throws Exception {
		List<Todo> todos = todoFacade.findByName("Todo Item");
		Assert.assertNotNull(todos);
		Assert.assertEquals(0, todos.size());
	}

	@Test
	public void testCheckOut() throws Exception {

		long accountId = accountFacade.create("marcus.bendtsen@liu.se",
				"secret");
		long todoId = todoFacade.create("New Item");

		todoFacade.checkOut(todoId, accountId);

		Todo todo = todoFacade.find(todoId);
		Account account = accountFacade.find(accountId);

		Assert.assertEquals(account.getId(), todo.getCheckedOutBy().getId());
		Assert.assertEquals(account.getEmail(), todo.getCheckedOutBy()
				.getEmail());

		Assert.assertEquals(account.getCheckedOut().get(0).getId(),
				todo.getId());
		Assert.assertEquals(account.getCheckedOut().get(0).getName(),
				todo.getName());

	}

	@Test
	public void testCheckOutFailure() throws Exception {

		long accountId = accountFacade.create("marcus.bendtsen@liu.se",
				"secret");
		long todoId = todoFacade.create("New Item");

		todoFacade.checkOut(todoId, accountId); // Should work

		try {
			todoFacade.checkOut(todoId, accountId); // Should fail
		} catch (TodoDataException e) {
			Assert.assertEquals(TodoDataException.Type.TODO_ALREADY_CHECKEDOUT,
					e.getType());
		}
	}

	@Test
	public void testCheckIn() throws Exception {

		long accountId = accountFacade.create("marcus.bendtsen@liu.se",
				"secret");
		long todoId = todoFacade.create("New Item");

		todoFacade.checkOut(todoId, accountId);

		Todo todo = todoFacade.find(todoId);
		Account account = accountFacade.find(accountId);

		Assert.assertEquals(account.getId(), todo.getCheckedOutBy().getId());
		Assert.assertEquals(account.getEmail(), todo.getCheckedOutBy()
				.getEmail());

		Assert.assertEquals(account.getCheckedOut().get(0).getId(),
				todo.getId());
		Assert.assertEquals(account.getCheckedOut().get(0).getName(),
				todo.getName());

		todoFacade.checkIn(todoId);

		todo = todoFacade.find(todoId);
		account = accountFacade.find(accountId);

		Assert.assertNull(todo.getCheckedOutBy());
		Assert.assertTrue(account.getCheckedOut().size() == 0);

	}

}
