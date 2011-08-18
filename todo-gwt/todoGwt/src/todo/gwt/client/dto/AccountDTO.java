package todo.gwt.client.dto;

import java.io.Serializable;

public class AccountDTO implements Serializable {

	private String email;

	public AccountDTO() {

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
