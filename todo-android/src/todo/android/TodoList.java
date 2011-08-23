package todo.android;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import todo.android.util.Session;
import todo.android.util.SignalHandler;
import todo.android.util.SignalHandler.Handler;
import todo.android.webservice.ResponseDTO;
import todo.android.webservice.TodoDTO;
import todo.android.webservice.WebServiceRequest;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TodoList extends LinearLayout {

	private Gson gson = new Gson();
	private WebServiceRequest webServiceRequest = new WebServiceRequest();

	private LinearLayout todoListLayout;
	private TextView nameLabel;
	private EditText name;
	private Button createButton;

	public TodoList(Context context) {
		super(context);
		todoListLayout = new LinearLayout(getContext());
		todoListLayout.setOrientation(LinearLayout.VERTICAL);

		nameLabel = new TextView(getContext());
		nameLabel.setText("Name:");
		name = new EditText(getContext());
		createButton = new Button(getContext());
		createButton.setText("Create");

		this.setOrientation(LinearLayout.VERTICAL);
		
		this.addView(nameLabel);
		this.addView(name);
		this.addView(createButton);
		this.addView(todoListLayout);
		
		bindEvents();
		bindHandlers();
	}

	private void bindEvents() {
		SignalHandler.addHandler(SignalHandler.Type.TODO_ITEM_CREATED,
				new Handler() {

					@Override
					public void onEvent() {
						update();
					}
				});
	}

	private void bindHandlers() {
		createButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				ResponseDTO responseDTO = webServiceRequest.createTodo(
						Session.suppliedEmail, Session.suppliedPassword, name
								.getText().toString());
				
				if (responseDTO.getCode() == 0) {
					SignalHandler.fireSignal(SignalHandler.Type.TODO_ITEM_CREATED);
				}

			}
		});

	}

	public void update() {

		ResponseDTO responseDTO = webServiceRequest.list();

		Type collectionType = new TypeToken<Collection<TodoDTO>>() {
		}.getType();
		List<TodoDTO> todos = gson.fromJson(responseDTO.getPayload(),
				collectionType);

		todoListLayout.removeAllViews();
		for (TodoDTO todo : todos) {
			TextView textView = new TextView(getContext());
			textView.setText(todo.getName());
			todoListLayout.addView(textView);
		}
	}

}
