package todo.android;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import todo.android.webservice.ResponseDTO;
import todo.android.webservice.TodoDTO;
import todo.android.webservice.WebServiceRequest;
import android.app.Activity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TodoActivity extends Activity {
    
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        

		
    }
}