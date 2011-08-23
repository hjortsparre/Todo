package todo.ws.util;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import todo.data.util.EMF;

/*
 * This servlet should NEVER be put into production, it's a helper to run unit tests!
 */

@WebServlet("/DatabaseServlet")
public class DatabaseServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println("Dropping database");
		
		EMF.close();
		EMF.createEntityManager();
	}

}
