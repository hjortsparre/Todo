package todo.data.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import todo.data.entity.Todo;
import todo.data.util.EMF;

public class TodoFacade {

	public long create(String name) {

		EntityManager entityManager = EMF.createEntityManager();

		try {

			Todo todo = new Todo();
			todo.setName(name);

			entityManager.getTransaction().begin();

			entityManager.persist(todo);

			entityManager.getTransaction().commit();

			return todo.getId();

		} catch (Exception e) {

			return 0;

		} finally {

			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}

			entityManager.close();

		}

	}

	public Todo find(long id) {

		EntityManager entityManager = EMF.createEntityManager();

		try {

			return entityManager.find(Todo.class, id);

		} catch (Exception e) {
			return null;
		} finally {

			entityManager.close();

		}

	}

	public List<Todo> findAll() {

		EntityManager entityManager = EMF.createEntityManager();

		try {

			Query query = entityManager.createQuery("SELECT t FROM Todo t");

			return query.getResultList();

		} catch (Exception e) {
			return null;
		} finally {

			entityManager.close();

		}
	}

	public List<Todo> findByName(String name) {

		EntityManager entityManager = EMF.createEntityManager();

		try {

			Query query = entityManager
					.createQuery("SELECT t FROM Todo t WHERE t.name = :name");
			query.setParameter("name", name);

			return query.getResultList();

		} catch (Exception e) {
			return null;
		} finally {

			entityManager.close();

		}
	}

}
