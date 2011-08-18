package todo.webapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import todo.webapp.util.HTTPHelper;
import todo.webapp.util.WebServiceRequest;

@WebServlet("/CheckInServlet")
public class CheckInServlet extends HttpServlet {

	private WebServiceRequest webServiceRequest = new WebServiceRequest();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {

			String email = request.getSession()
					.getAttribute("email").toString();
			String password = request.getSession()
					.getAttribute("password").toString();

			String todoId = request.getParameter("todoId");

			String outcome = webServiceRequest
					.checkIn(email, password, todoId);

			request.setAttribute("outcome", outcome);
			getServletContext().getRequestDispatcher("/list.jsp").forward(
					request, response);

		} catch (Exception e) {

		}

	}

}
