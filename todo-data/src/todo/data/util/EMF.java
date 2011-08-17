package todo.data.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMF {

	private static Object lock = new Object();
	private static EntityManagerFactory emf;

	private static void init() {
		synchronized (lock) {

			if (emf != null && emf.isOpen()) {
				return;
			}

			String testingProperty = System.getProperty("testing");
			boolean testing = testingProperty != null && testingProperty.equals("true");

			if (testing) {
				emf = Persistence.createEntityManagerFactory("todo-PU-test");
			} else {
				emf = Persistence.createEntityManagerFactory("todo-PU");
			}
		}
	}

	public static EntityManager createEntityManager() {
		if (emf == null || !emf.isOpen()) {
			init();
		}
		return emf.createEntityManager();
	}

	public static void close() {
		emf.close();
	}

}