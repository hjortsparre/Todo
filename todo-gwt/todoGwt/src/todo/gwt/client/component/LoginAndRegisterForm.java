package todo.gwt.client.component;

import todo.gwt.client.TodoGwt;
import todo.gwt.client.dto.ResponseDTO;
import todo.gwt.client.service.AccountServiceAsync;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoginAndRegisterForm extends Composite {

	private VerticalPanel wrapper = new VerticalPanel();
	private FlexTable form = new FlexTable();

	private Label message = new Label();
	private Label emailLabel = new Label("Email: ");
	private TextBox email = new TextBox();
	private Label passwordLabel = new Label("Password: ");
	private PasswordTextBox password = new PasswordTextBox();
	private Button loginButton = new Button("Login");
	private Button registerButton = new Button("Register");

	public LoginAndRegisterForm() {

		initWidget(wrapper);
		wrapper.add(message);
		wrapper.add(form);

		form.setWidget(0, 0, emailLabel);
		form.setWidget(0, 1, email);
		form.setWidget(1, 0, passwordLabel);
		form.setWidget(1, 1, password);
		form.setWidget(2, 0, loginButton);
		form.setWidget(2, 1, registerButton);

		loginButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				AccountServiceAsync.instance.login(email.getValue(),
						password.getValue(), new AsyncCallback<ResponseDTO>() {

							@Override
							public void onSuccess(ResponseDTO result) {
								if (result.getCode() == 0) {
									TodoGwt.suppliedPassword = password.getValue();
									TodoGwt.suppliedEmail = email.getValue();
									TodoGwt.onLogin();
								} else {
									message.setText(result.getMessage());
								}
							}

							@Override
							public void onFailure(Throwable caught) {
								message.setText(caught.getMessage());
							}
						});
			}
		});

		registerButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				AccountServiceAsync.instance.register(email.getValue(),
						password.getValue(), new AsyncCallback<ResponseDTO>() {

							@Override
							public void onSuccess(ResponseDTO result) {
								if(result.getCode() == 0) {
									message.setText("Register success!");
								} else {
									message.setText(result.getMessage());
								}
							}

							@Override
							public void onFailure(Throwable caught) {
								message.setText(caught.getMessage());
							}
						});
			}
		});

	}
}
