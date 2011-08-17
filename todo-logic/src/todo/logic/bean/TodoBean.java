package todo.logic.bean;

import java.util.List;

import todo.data.entity.Account;
import todo.data.entity.Todo;
import todo.data.facade.TodoFacade;
import todo.logic.util.TodoLogicException;
import todo.logic.util.TodoLogicLogger;

public class TodoBean {

	private AccountBean accountBean = new AccountBean();
	private TodoFacade todoFacade = new TodoFacade();

	private TwitterBean twitterBean = new TwitterBean();

	public Todo create(String email, String password, String name)
			throws Exception {

		TodoLogicLogger.info(TodoBean.class, "create", email, password, name);

		Account account = accountBean.login(email, password);
		long id = todoFacade.create(name);

		Todo todo = todoFacade.find(id);

		if (todo != null) {
			twitterBean.tweet(account.getEmail() + " created a new Todo: "
					+ todo.getName());
		}

		return todo;

	}

	public void checkOut(String email, String password, long todoId)
			throws Exception {

		TodoLogicLogger.info(TodoBean.class, "checkOut", email, password,
				todoId + "");

		// Will either return account or throw an exception
		Account account = accountBean.login(email, password);

		todoFacade.checkOut(todoId, account.getId());

		twitterBean.tweet(account.getEmail() + " checked out Todo with id: "
				+ todoId);

	}

	public void checkIn(String email, String password, long todoId)
			throws Exception {

		TodoLogicLogger.info(TodoBean.class, "checkIn", email, password);

		Account account = accountBean.login(email, password);

		Todo todo = todoFacade.find(todoId);

		if (todo.getCheckedOutBy().getId() == account.getId()) {
			todoFacade.checkIn(todoId);
		} else {

			TodoLogicException e = new TodoLogicException(
					TodoLogicException.Type.TODO_NOT_CHECKED_OUT_BY_THIS_ACCOUNT);

			TodoLogicLogger.warning(TodoBean.class, "checkIn", e, todoId + "");

			throw e;
		}

		twitterBean.tweet(account.getEmail() + " checked in Todo with id: "
				+ todoId);

	}

	public List<Todo> list() throws Exception {

		TodoLogicLogger.info(TodoBean.class, "list", "");

		return todoFacade.findAll();
	}

}
