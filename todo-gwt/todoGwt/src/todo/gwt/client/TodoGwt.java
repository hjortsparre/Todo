package todo.gwt.client;

import java.util.List;

import todo.gwt.client.component.LoginAndRegisterForm;
import todo.gwt.client.component.TodoTable;
import todo.gwt.client.dto.TodoDTO;
import todo.gwt.client.service.TodoServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TodoGwt implements EntryPoint {

	private static VerticalPanel wrapper = new VerticalPanel();
	private static TodoTable todoTable = new TodoTable();

	public static String suppliedEmail;
	public static String suppliedPassword;

	public void onModuleLoad() {
		wrapper.add(new LoginAndRegisterForm());
		RootPanel.get().add(wrapper);
	}

	public static void onLogin() {
		TodoServiceAsync.instance.list(new AsyncCallback<List<TodoDTO>>() {

			@Override
			public void onSuccess(List<TodoDTO> result) {
				todoTable.show(result);
			}

			@Override
			public void onFailure(Throwable caught) {

			}
		});
		wrapper.clear();
		wrapper.add(todoTable);
	}
}