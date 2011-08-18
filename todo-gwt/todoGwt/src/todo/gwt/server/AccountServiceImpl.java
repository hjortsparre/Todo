package todo.gwt.server;

import todo.gwt.client.dto.ResponseDTO;
import todo.gwt.client.service.AccountService;
import todo.gwt.server.util.WebServiceRequest;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class AccountServiceImpl extends RemoteServiceServlet implements
		AccountService {

	private WebServiceRequest webServiceRequest = new WebServiceRequest();

	@Override
	public ResponseDTO login(String email, String password) {
		ResponseDTO responseDTO = webServiceRequest.login(email, password);
		return responseDTO;
	}

	@Override
	public ResponseDTO register(String email, String password) {
		return webServiceRequest.register(email, password);
	}

}
