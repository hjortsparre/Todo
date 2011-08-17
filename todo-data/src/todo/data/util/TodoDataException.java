package todo.data.util;

public class TodoDataException extends Exception {

	public enum Type {
		DATABASE_ERROR(101, "Couldn't complete database action"), INCORRECT_PK(
				201, "The supplied primary key isn't used by this entity");

		private int code;
		private String message;

		Type(int code, String message) {
			this.code = code;
			this.message = message;
		}

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}

	}

	private Type type;
	private Exception cause;

	public TodoDataException(Type type) {
		this.type = type;
	}

	public TodoDataException(Type type, Exception cause) {
		this.type = type;
		this.cause = cause;
	}

	public Type getType() {
		return this.type;
	}

	@Override
	public String getMessage() {
		if (cause != null) {
			return cause.getMessage();
		} else {
			return type.getMessage();
		}
	}

}
