package todo.webapp.util;

import todo.webapp.dto.ResponseDTO;

import com.google.gson.Gson;

//Wrapp our requests to the webservice layer
//makes it easier to refire on failure, and change endpoints
public class WebServiceRequest {

	private Gson gson = new Gson();

	private static final String accountEndpoint = "http://localhost:8080/todo-ws/rest/account";
	private static final String todoEndpoint = "http://localhost:8080/todo-ws/rest/todo";

	public ResponseDTO register(String email, String password) {
		try {
			String outcome = HTTPHelper.get(accountEndpoint + "/register",
					"email", email, "password", password);
			return gson.fromJson(outcome, ResponseDTO.class);
		} catch (Exception e) {
			return null;
		}
	}

	public ResponseDTO login(String email, String password) {
		try {
			String outcome = HTTPHelper.get(accountEndpoint + "/login",
					"email", email, "password", password);
			return gson.fromJson(outcome, ResponseDTO.class);
		} catch (Exception e) {
			return null;
		}
	}

	public ResponseDTO checkOut(String email, String password, String todoId) {
		try {
			String outcome = HTTPHelper.get(todoEndpoint + "/check/out",
					"email", email, "password", password, "todoId", todoId);
			return gson.fromJson(outcome, ResponseDTO.class);
		} catch (Exception e) {
			return null;
		}
	}

	public ResponseDTO checkIn(String email, String password, String todoId) {
		try {
			String outcome = HTTPHelper.get(todoEndpoint + "/check/in",
					"email", email, "password", password, "todoId", todoId);
			return gson.fromJson(outcome, ResponseDTO.class);
		} catch (Exception e) {
			return null;
		}
	}

	public ResponseDTO list() {
		try {
			String outcome = HTTPHelper.get(todoEndpoint, new String[] {});
			return gson.fromJson(outcome, ResponseDTO.class);
		} catch (Exception e) {
			return null;
		}

	}

	public ResponseDTO createTodo(String email, String password, String name) {
		try {
			String outcome = HTTPHelper.get(todoEndpoint + "/create", "email",
					email, "password", password, "name", name);
			return gson.fromJson(outcome, ResponseDTO.class);
		} catch (Exception e) {
			return null;
		}

	}

}
