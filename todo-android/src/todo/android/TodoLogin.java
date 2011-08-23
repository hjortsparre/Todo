package todo.android;

import todo.android.util.Session;
import todo.android.util.SignalHandler;
import todo.android.webservice.ResponseDTO;
import todo.android.webservice.WebServiceRequest;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TodoLogin extends LinearLayout {

	private WebServiceRequest request = new WebServiceRequest();

	private TextView emailLabel;
	private EditText email;
	private TextView passwordLabel;
	private EditText password;
	private Button login;

	public TodoLogin(Context context) {
		super(context);
		
		this.setOrientation(LinearLayout.VERTICAL);

		emailLabel = new TextView(getContext());
		emailLabel.setText("Email: ");
		email = new EditText(getContext());

		passwordLabel = new TextView(getContext());
		passwordLabel.setText("Password: ");
		password = new EditText(getContext());

		login = new Button(getContext());
		login.setText("Login");

		this.addView(emailLabel);
		this.addView(email);
		this.addView(passwordLabel);
		this.addView(password);
		this.addView(login);

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				ResponseDTO dto = request.login(email.getText().toString(),
						password.getText().toString());

				if (dto.getCode() == 0) {
					Session.suppliedEmail = email.getText().toString();
					Session.suppliedPassword = password.getText().toString();
					SignalHandler.fireSignal(SignalHandler.Type.LOGIN_SUCCESS);
				}

			}
		});
	}

}