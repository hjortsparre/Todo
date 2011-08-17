package todo.data.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import todo.data.entity.Todo;
import todo.data.util.EMF;
import todo.data.util.TodoDataException;
import todo.data.util.TodoDataLogger;

public class TodoFacade {

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
