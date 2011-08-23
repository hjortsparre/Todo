package todo.ws.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import todo.data.util.EMF;

@WebListener
public class ApplicationConfigListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		EMF.close();
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		System.setProperty("testing","true");
		System.setProperty("logPath", "/home/marcus/workspace");

		EMF.createEntityManager(); // Force init to run
	}

}
