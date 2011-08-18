package todo.webapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import todo.webapp.util.WebServiceRequest;

@WebServlet("/CreateTodoServlet")
public class CreateTodoServlet extends HttpServlet {

	private WebServiceRequest webServiceRequest = new WebServiceRequest();

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String email = request.getSession().getAttribute("email").toString();
		String password = request.getSession().getAttribute("password")
				.toString();

		String name = request.getParameter("name");

		String outcome = webServiceRequest.create(email, password, name);

		request.setAttribute("outcome", outcome);

		getServletContext().getRequestDispatcher("/list.jsp").forward(request,
				response);

	}

}
