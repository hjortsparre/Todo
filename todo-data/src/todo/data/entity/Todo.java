package todo.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Todo {

	@Id
	@GeneratedValue
	private long id;

	private String name;

	@ManyToOne
	private Account checkedOutBy;

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

	public Account getCheckedOutBy() {
		return checkedOutBy;
	}

	public void setCheckedOutBy(Account checkedOutBy) {
		this.checkedOutBy = checkedOutBy;
	}

}
