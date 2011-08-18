package todo.gwt.client.service;

import java.util.List;

import todo.gwt.client.dto.ResponseDTO;
import todo.gwt.client.dto.TodoDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TodoServiceAsync {

	public static final TodoServiceAsync instance = GWT
			.create(TodoService.class);

	void list(AsyncCallback<List<TodoDTO>> callback);

	void checkIn(String email, String password, long todoId,
			AsyncCallback<ResponseDTO> callback);

	void checkOut(String email, String password, long todoId,
			AsyncCallback<ResponseDTO> callback);
}
