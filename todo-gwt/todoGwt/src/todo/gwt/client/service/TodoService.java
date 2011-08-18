package todo.gwt.client.service;

import java.util.List;

import todo.gwt.client.dto.ResponseDTO;
import todo.gwt.client.dto.TodoDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("todo")
public interface TodoService extends RemoteService {
	List<TodoDTO> list();
	ResponseDTO checkIn(String email, String password, long todoId);
	ResponseDTO checkOut(String email, String password, long todoId);
}
