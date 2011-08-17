package todo.data.facade;

import javax.persistence.EntityManager;

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

}
