package todo.data.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import todo.data.entity.Account;
import todo.data.entity.Todo;
import todo.data.util.EMF;
import todo.data.util.TodoDataException;
import todo.data.util.TodoDataException.Type;
import todo.data.util.TodoDataLogger;

public class TodoFacade {

	public void checkOut(long todoId, long accountId) throws TodoDataException {

		TodoDataLogger.info(TodoFacade.class, "checkOut", todoId + "",
				accountId + "");

		EntityManager entityManager = EMF.createEntityManager();

		try {

			entityManager.getTransaction().begin();

			Todo todo = entityManager.find(Todo.class, todoId);
			if (todo.getCheckedOutBy() != null) {
				throw new TodoDataException(Type.TODO_ALREADY_CHECKEDOUT);
			}

			Account account = entityManager.find(Account.class, accountId);
			todo.setCheckedOutBy(account);

			entityManager.merge(todo);
			entityManager.flush();

			entityManager.getTransaction().commit();

		} catch (TodoDataException e) {

			TodoDataLogger.warning(AccountFacade.class, "checkOut", e, todoId
					+ "", accountId + "");

			throw e;

		} catch (Exception e) {

			TodoDataLogger.severe(AccountFacade.class, "checkOut", e, todoId
					+ "", accountId + "");

			throw new TodoDataException(Type.DATABASE_ERROR, e);

		} finally {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}

			entityManager.close();
		}

	}

	public void checkIn(long todoId) throws TodoDataException {

		TodoDataLogger.info(TodoFacade.class, "checkIn", todoId + "");

		EntityManager entityManager = EMF.createEntityManager();

		try {

			entityManager.getTransaction().begin();

			Todo todo = entityManager.find(Todo.class, todoId);
			todo.setCheckedOutBy(null);

			entityManager.merge(todo);
			entityManager.flush();

			entityManager.getTransaction().commit();

		} catch (Exception e) {

			TodoDataLogger.warning(AccountFacade.class, "checkIn", e, todoId
					+ "");

			throw new TodoDataException(Type.DATABASE_ERROR, e);

		} finally {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}

			entityManager.close();
		}

	}

	public long create(String name) throws TodoDataException {

		TodoDataLogger.info(TodoFacade.class, "create", name);

		EntityManager entityManager = EMF.createEntityManager();

		try {

			Todo todo = new Todo();
			todo.setName(name);

			entityManager.getTransaction().begin();

			entityManager.persist(todo);

			entityManager.getTransaction().commit();

			return todo.getId();

		} catch (Exception e) {

			TodoDataLogger.severe(TodoFacade.class, "create", e, name);

			throw new TodoDataException(TodoDataException.Type.DATABASE_ERROR,
					e);

		} finally {

			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}

			entityManager.close();

		}

	}

	public Todo find(long id) throws TodoDataException {

		TodoDataLogger.info(TodoFacade.class, "find", id + "");

		EntityManager entityManager = EMF.createEntityManager();

		try {

			Todo todo = entityManager.find(Todo.class, id);
			if (todo == null) {
				throw new TodoDataException(TodoDataException.Type.INCORRECT_PK);
			}

			return todo;

		} catch (TodoDataException e) {

			TodoDataLogger.warning(TodoFacade.class, "find", e, id + "");

			throw e;

		} catch (Exception e) {

			TodoDataLogger.severe(TodoFacade.class, "find", e, id + "");

			throw new TodoDataException(TodoDataException.Type.DATABASE_ERROR,
					e);

		} finally {

			entityManager.close();

		}

	}

	public List<Todo> findAll() throws TodoDataException {

		TodoDataLogger.info(TodoFacade.class, "findAll", "");

		EntityManager entityManager = EMF.createEntityManager();

		try {

			Query query = entityManager.createQuery("SELECT t FROM Todo t");

			return query.getResultList();

		} catch (Exception e) {

			TodoDataLogger.severe(TodoFacade.class, "findAll", e, "");

			throw new TodoDataException(TodoDataException.Type.DATABASE_ERROR,
					e);

		} finally {

			entityManager.close();

		}
	}

	public List<Todo> findByName(String name) throws TodoDataException {

		TodoDataLogger.info(TodoFacade.class, "findByName", name);

		EntityManager entityManager = EMF.createEntityManager();

		try {

			Query query = entityManager
					.createQuery("SELECT t FROM Todo t WHERE t.name = :name");
			query.setParameter("name", name);

			return query.getResultList();

		} catch (Exception e) {

			TodoDataLogger.severe(TodoFacade.class, "findByName", e, name);

			throw new TodoDataException(TodoDataException.Type.DATABASE_ERROR,
					e);

		} finally {

			entityManager.close();

		}
	}

}
