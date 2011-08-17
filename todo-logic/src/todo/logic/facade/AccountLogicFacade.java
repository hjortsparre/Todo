package todo.logic.facade;

import todo.data.entity.Account;
import todo.data.facade.AccountFacade;
import todo.data.util.TodoDataException;
import todo.logic.util.TodoLogicException;
import todo.logic.util.TodoLogicLogger;

public class AccountLogicFacade {

	private TwitterLogicFacade twitterLogicFacade = new TwitterLogicFacade();
	private AccountFacade accountFacade = new AccountFacade();

	public void register(String email, String password)
			throws TodoLogicException, TodoDataException {

		TodoLogicLogger.info(AccountLogicFacade.class, "register", email,
				password);

		// This will create a new account, if it fails an exception will
		// be thrown.
		accountFacade.create(email, password);

		// Here we could send a confirmation email to the new user so they
		// confirm their email address

		twitterLogicFacade.tweet("We have a new member! Welcome: " + email);

	}

	public Account login(String email, String password)
			throws TodoLogicException, TodoDataException {

		TodoLogicLogger
				.info(AccountLogicFacade.class, "login", email, password);

		Account account = accountFacade.findByEmail(email);
		if (account == null || !(account.getPassword().equals(password))) {

			TodoLogicException e = new TodoLogicException(
					TodoLogicException.Type.AUTHENTICATION_FAILED);

			TodoLogicLogger.warning(AccountFacade.class, "login", e, email,
					password);

			throw e;
		}

		return account;

	}
}
