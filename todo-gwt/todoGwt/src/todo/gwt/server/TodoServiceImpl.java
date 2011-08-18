package todo.gwt.server;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import todo.gwt.client.dto.ResponseDTO;
import todo.gwt.client.dto.TodoDTO;
import todo.gwt.client.service.TodoService;
import todo.gwt.server.util.WebServiceRequest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class TodoServiceImpl extends RemoteServiceServlet implements
		TodoService {

	private Gson gson = new Gson();
	private WebServiceRequest webServiceRequest = new WebServiceRequest();

	public List<TodoDTO> list() {
		ResponseDTO responseDTO = webServiceRequest.list();

		Type collectionType = new TypeToken<Collection<TodoDTO>>() {
		}.getType();
		List<TodoDTO> todos = gson.fromJson(responseDTO.getPayload(),
				collectionType);

		return todos;
	}

	@Override
	public ResponseDTO checkIn(String email, String password, long todoId) {
		return webServiceRequest.checkIn(email, password, todoId + "");
	}

	@Override
	public ResponseDTO checkOut(String email, String password, long todoId) {
		return webServiceRequest.checkOut(email, password, todoId + "");
	}
}
