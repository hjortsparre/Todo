package todo.webapp.util;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import todo.webapp.dto.TodoDTO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class WebServiceRequest {

	private Gson gson = new Gson();

	private static final String accountEndpoint = "http://localhost:8080/todo-ws/rest/account";
	private static final String todoEndpoint = "http://localhost:8080/todo-ws/rest/todo";

	public String register(String email, String password) {

		try {
			String outcome = HTTPHelper.get(accountEndpoint + "/register",
					"email", email, "password", password);

			if (outcome.isEmpty()) {
				return "Account created successfully";
			} else {
				int code = Integer.parseInt(outcome);
				if (code == 202) {
					return "Sorry this email is already taken";
				} else if (code == 103) {
					return "Please ensure that email and password are both provided";
				} else {
					return null;
				}
			}

		} catch (Exception e) {
			return null;
		}

	}

	public String login(String email, String password) {
		try {
			String outcome = HTTPHelper.get(accountEndpoint + "/login",
					"email", email, "password", password);

			if (outcome.isEmpty()) {
				return "";
			}

			int code = Integer.parseInt(outcome);
			if (code == 501) {
				return "Sorry, please check your username and password again";
			} else {
				return null;
			}

		} catch (Exception e) {
			return null;
		}
	}

	public String checkOut(String email, String password, String todoId) {
		try {

			String outcome = HTTPHelper.get(todoEndpoint + "/check/out",
					"email", email, "password", password, "todoId", todoId);

			if (outcome.isEmpty()) {
				return "Item checked out";
			}

			int code = Integer.parseInt(outcome);
			if (code == 501) {
				return "Sorry, please check your username and password again";
			} else if (code == 203) {
				return "Sorry, this item is already checked out";
			} else {
				return "";
			}

		} catch (Exception e) {
			return null;
		}
	}

	public String checkIn(String email, String password, String todoId) {
		try {

			String outcome = HTTPHelper.get(todoEndpoint + "/check/in",
					"email", email, "password", password, "todoId", todoId);

			if (outcome.isEmpty()) {
				return "Item checked in";
			}

			int code = Integer.parseInt(outcome);
			if (code == 501) {
				return "Sorry, please check your username and password again";
			} else if (code == 502) {
				return "Sorry, this item is not checked out by you";
			} else {
				return "";
			}

		} catch (Exception e) {
			return null;
		}
	}

	public List<TodoDTO> list() {

		try {

			String outcome = HTTPHelper.get(todoEndpoint, new String[] {});

			// If this deserialization fails, we probably received an error code
			Type collectionType = new TypeToken<Collection<TodoDTO>>() {
			}.getType();
			List<TodoDTO> todos = gson.fromJson(outcome, collectionType);

			return todos;

		} catch (Exception e) {
			return null; // We probably will end up here if we have a
							// deserialization exception
		}

	}

	public String create(String email, String password, String name) {

		try {

			String outcome = HTTPHelper.get(todoEndpoint + "/create", "email",
					email, "password", password, "name", name);

			return outcome;

		} catch (Exception e) {
			return null;
		}

	}

}
