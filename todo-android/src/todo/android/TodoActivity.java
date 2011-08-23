package todo.android;

import todo.android.util.SignalHandler;
import todo.android.util.SignalHandler.Handler;
import todo.android.util.SignalHandler.Type;
import android.app.Activity;
import android.os.Bundle;

public class TodoActivity extends Activity {

	private TodoLogin todoLogin;
	private TodoList todoList;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		todoLogin = new TodoLogin(this);
		todoList = new TodoList(this);
		
		setContentView(todoLogin);

		SignalHandler.addHandler(Type.LOGIN_SUCCESS, new Handler() {

			@Override
			public void onEvent() {
				todoList.update();
				setContentView(todoList);
			}
		});

		SignalHandler.addHandler(Type.TODO_ITEM_CREATED, new Handler() {

			@Override
			public void onEvent() {
				todoList.update();
			}
		});

	}

}