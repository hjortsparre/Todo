package todo.data.facade;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.Query;

import todo.data.entity.Account;
import todo.data.util.EMF;
import todo.data.util.TodoDataException;
import todo.data.util.TodoDataException.Type;
import todo.data.util.TodoDataLogger;

public class AccountFacade {

	public long create(String email, String password) throws TodoDataException {

		TodoDataLogger.info(AccountFacade.class, "create", email, password);

		EntityManager entityManager = EMF.createEntityManager();

		try {

			if (findByEmail(email) != null) {
				throw new TodoDataException(Type.EMAIL_NOT_UNIQUE);
			}

			Account account = new Account();
			account.setEmail(email);
			account.setPassword(password);

			entityManager.getTransaction().begin();

			entityManager.persist(account);

			entityManager.getTransaction().commit();

			return account.getId();

		} catch (TodoDataException e) {

			TodoDataLogger.warning(AccountFacade.class, "create", e, email,
					password);

			throw e;

		} catch (Exception e) {

			TodoDataLogger.severe(AccountFacade.class, "create", e, email,
					password);

			throw new TodoDataException(Type.DATABASE_ERROR, e);

		} finally {

			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}

			entityManager.close();

		}

	}

	public void updateEmployeeId(long accountId, String employeeId)
			throws TodoDataException {

		TodoDataLogger.info(AccountFacade.class, "updateEmployeeId", accountId
				+ "", employeeId);

		EntityManager entityManager = EMF.createEntityManager();

		try {

			entityManager.getTransaction().begin();

			Account account = entityManager.find(Account.class, accountId);
			account.setEmployeeId(employeeId);

			entityManager.merge(account);
			entityManager.flush();

			entityManager.getTransaction().commit();

		} catch (OptimisticLockException e) {

			TodoDataLogger.warning(AccountFacade.class, "updateEmployeeId", e,
					accountId + "", employeeId);

			throw new TodoDataException(Type.CONCURRENCY_VERSION_ERROR, e);

		} catch (Exception e) {

			TodoDataLogger.severe(AccountFacade.class, "updateEmployeeId", e,
					accountId + "", employeeId);

			throw new TodoDataException(Type.DATABASE_ERROR, e);

		} finally {

			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}

			entityManager.close();

		}

	}

	public Account find(long id) throws TodoDataException {

		TodoDataLogger.info(AccountFacade.class, "find", id + "");

		EntityManager entityManager = EMF.createEntityManager();

		try {

			Account account = entityManager.find(Account.class, id);

			if (account == null) {
				throw new TodoDataException(Type.INCORRECT_PK);
			}

			return account;

		} catch (TodoDataException e) {

			TodoDataLogger.warning(AccountFacade.class, "find", e, id + "");

			return null;

		} catch (Exception e) {

			TodoDataLogger.severe(AccountFacade.class, "find", e, id + "");

			throw new TodoDataException(Type.DATABASE_ERROR, e);

		} finally {

			entityManager.close();

		}
	}

	public Account findByEmail(String email) throws TodoDataException {

		TodoDataLogger.info(AccountFacade.class, "findByEmail", email);

		EntityManager entityManager = EMF.createEntityManager();

		try {

			Query query = entityManager
					.createQuery("SELECT a FROM Account a WHERE a.email = :email");
			query.setParameter("email", email);

			return (Account) query.getSingleResult();

		} catch (NoResultException e) {

			TodoDataLogger
					.warning(AccountFacade.class, "findByEmail", e, email);

			return null;

		} catch (Exception e) {

			TodoDataLogger.severe(AccountFacade.class, "findByEmail", e, email);

			throw new TodoDataException(Type.DATABASE_ERROR, e);

		} finally {

			entityManager.close();

		}

	}

}
