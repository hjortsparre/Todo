package todo.android.webservice;

import java.io.Serializable;

public class ResponseDTO implements Serializable {

	private int code;
	private String message;
	private String payload;
	
	public ResponseDTO() {
		
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

}
