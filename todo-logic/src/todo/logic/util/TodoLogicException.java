package todo.logic.util;

public class TodoLogicException extends Exception {

	public enum Type {
		
		AUTHENTICATION_FAILED(501, "The authentication could not be completed"), 
		TODO_NOT_CHECKED_OUT_BY_THIS_ACCOUNT(502, "This account isn't the current holder of this todo");

		private String message;
		private int code;

		Type(int code, String message) {
			this.code = code;
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}

	private Type type;
	private Exception cause;

	public TodoLogicException(Type type) {
		this.type = type;
	}

	public TodoLogicException(Type type, Exception e) {
		this.type = type;
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
