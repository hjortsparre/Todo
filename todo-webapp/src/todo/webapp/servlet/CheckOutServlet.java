package todo.webapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import todo.webapp.dto.ResponseDTO;
import todo.webapp.util.WebServiceRequest;

@WebServlet("/CheckOutServlet")
public class CheckOutServlet extends HttpServlet {

	private WebServiceRequest webServiceRequest = new WebServiceRequest();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {

			String email = request.getSession().getAttribute("email")
					.toString();
			String password = request.getSession().getAttribute("password")
					.toString();

			String todoId = request.getParameter("todoId");

			ResponseDTO responseDTO = webServiceRequest.checkOut(email,
					password, todoId);

			request.setAttribute("message", responseDTO.getMessage());

			getServletContext().getRequestDispatcher("/list.jsp").forward(
					request, response);

		} catch (Exception e) {

		}
	}
}
