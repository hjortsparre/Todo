package todo.android.webservice;

import java.io.Serializable;

public class TodoDTO implements Serializable {

	private long id;
	private String name;
	private AccountDTO checkedOutBy;

	public TodoDTO() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AccountDTO getCheckedOutBy() {
		return checkedOutBy;
	}

	public void setCheckedOutBy(AccountDTO checkedOutBy) {
		this.checkedOutBy = checkedOutBy;
	}

}
