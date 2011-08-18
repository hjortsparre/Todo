package todo.gwt.client.service;

import todo.gwt.client.dto.ResponseDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("account")
public interface AccountService extends RemoteService{

	public ResponseDTO login(String email, String password);
	public ResponseDTO register(String email, String password);
	
}
