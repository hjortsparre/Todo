package todo.gwt.client.service;

import todo.gwt.client.dto.ResponseDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AccountServiceAsync {

	public static final AccountServiceAsync instance = GWT
			.create(AccountService.class);

	void login(String email, String password,
			AsyncCallback<ResponseDTO> callback);

	void register(String email, String password,
			AsyncCallback<ResponseDTO> callback);

}
