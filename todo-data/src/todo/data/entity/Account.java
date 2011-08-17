package todo.data.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity
public class Account {

	@Id
	@GeneratedValue
	private long id;

	@Version
	private int version;

	private String employeeId;

	@Column(unique = true)
	private String email;

	private String password;

	@OneToMany(mappedBy = "checkedOutBy")
	private List<Todo> checkedOut;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Todo> getCheckedOut() {
		return checkedOut;
	}

	public void setCheckedOut(List<Todo> checkedOut) {
		this.checkedOut = checkedOut;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	

}
