package todo.logic.facade;

import java.util.List;

import todo.data.entity.Account;
import todo.data.entity.Todo;
import todo.data.facade.TodoFacade;
import todo.data.util.TodoDataException;
import todo.logic.util.TodoLogicException;
import todo.logic.util.TodoLogicLogger;

public class TodoLogicFacade {

	private AccountLogicFacade accountLogicFacade = new AccountLogicFacade();
	private TodoFacade todoFacade = new TodoFacade();

	private TwitterLogicFacade twitterBean = new TwitterLogicFacade();

	public Todo create(String email, String password, String name)
			throws TodoLogicException, TodoDataException {

		TodoLogicLogger.info(TodoLogicFacade.class, "create", email, password,
				name);

		// If this login fails an exception is thrown
		Account account = accountLogicFacade.login(email, password);

		long id = todoFacade.create(name);

		Todo todo = todoFacade.find(id);

		if (todo != null) {
			twitterBean.tweet(account.getEmail() + " created a new Todo: "
					+ todo.getName());
		}

		return todo;

	}

	public void checkOut(String email, String password, long todoId)
			throws TodoLogicException, TodoDataException {

		TodoLogicLogger.info(TodoLogicFacade.class, "checkOut", email,
				password, todoId + "");

		// Will either return account or throw an exception
		Account account = accountLogicFacade.login(email, password);

		todoFacade.checkOut(todoId, account.getId());

		twitterBean.tweet(account.getEmail() + " checked out Todo with id: "
				+ todoId);

	}

	public void checkIn(String email, String password, long todoId)
			throws TodoLogicException, TodoDataException {

		TodoLogicLogger.info(TodoLogicFacade.class, "checkIn", email, password);

		// If the login fails an exception is thrown
		Account account = accountLogicFacade.login(email, password);

		Todo todo = todoFacade.find(todoId);

		if (todo.getCheckedOutBy().getId() == account.getId()) {

			todoFacade.checkIn(todoId);

		} else {

			TodoLogicException e = new TodoLogicException(
					TodoLogicException.Type.TODO_NOT_CHECKED_OUT_BY_THIS_ACCOUNT);

			TodoLogicLogger.warning(TodoLogicFacade.class, "checkIn", e, todoId
					+ "");

			throw e;
		}

		twitterBean.tweet(account.getEmail() + " checked in Todo with id: "
				+ todoId);

	}

	public List<Todo> list() throws TodoLogicException, TodoDataException {

		TodoLogicLogger.info(TodoLogicFacade.class, "list", "");

		return todoFacade.findAll();
	}

}
