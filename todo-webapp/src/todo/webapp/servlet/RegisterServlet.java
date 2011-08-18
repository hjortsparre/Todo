package todo.webapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import todo.webapp.util.WebServiceRequest;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

	private WebServiceRequest webServiceRequest = new WebServiceRequest();

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		String outcome = webServiceRequest.register(email, password);

		request.setAttribute("outcome", outcome);

		getServletContext().getRequestDispatcher("/index.jsp").forward(request,
				response);
	}

}
