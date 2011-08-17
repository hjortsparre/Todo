package todo.logic.bean;

import todo.data.entity.Account;
import todo.data.facade.AccountFacade;
import todo.logic.util.TodoLogicException;
import todo.logic.util.TodoLogicLogger;

public class AccountBean {

	private TwitterBean twitterBean = new TwitterBean();
	private AccountFacade accountFacade = new AccountFacade();

	public void register(String email, String password) throws Exception {
		
		accountFacade.create(email, password);

		// Here we could send a confirmation email to the new user so they
		// confirm their email address

		twitterBean.tweet("We have a new member! Welcome: " + email);

	}

	public Account login(String email, String password) throws Exception {

		TodoLogicLogger.info(AccountBean.class, "login", email, password);

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
