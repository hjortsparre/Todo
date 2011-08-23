package todo.android;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import todo.android.webservice.ResponseDTO;
import todo.android.webservice.TodoDTO;
import todo.android.webservice.WebServiceRequest;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TodoActivity extends Activity {

	private Gson gson = new Gson();
	private WebServiceRequest webServiceRequest = new WebServiceRequest();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		ResponseDTO responseDTO = webServiceRequest.list();

		System.out.println(responseDTO);

		Type collectionType = new TypeToken<Collection<TodoDTO>>() {
		}.getType();
		List<TodoDTO> todos = gson.fromJson(responseDTO.getPayload(),
				collectionType);

		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		ListView listView = new ListView(this);
		for (TodoDTO todo : todos) {
			TextView textView = new TextView(this);
			textView.setText(todo.getName());
			linearLayout.addView(textView);
		}

		setContentView(linearLayout);
	}

}