package todo.webapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import todo.webapp.dto.ResponseDTO;
import todo.webapp.util.WebServiceRequest;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	private WebServiceRequest webServiceRequest = new WebServiceRequest();

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		ResponseDTO responseDTO = webServiceRequest.login(email, password);

		if (responseDTO.getCode() == 0) {
			request.getSession().setAttribute("email", email);
			request.getSession().setAttribute("password", password);
			getServletContext().getRequestDispatcher("/list.jsp").forward(
					request, response);
		} else {
			request.setAttribute("message", responseDTO.getMessage());
			getServletContext().getRequestDispatcher("/index.jsp").forward(
					request, response);
		}
	}

}
