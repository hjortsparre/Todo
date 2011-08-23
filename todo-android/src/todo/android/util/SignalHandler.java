package todo.android.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* Singleton */
public final class SignalHandler {

	/* Singleton stuff */
	private static final SignalHandler instance = new SignalHandler();

	private SignalHandler() {

	}

	/* ------------- */

	public enum Type {
		LOGIN_SUCCESS, TODO_ITEM_CREATED
	}

	public interface Handler {
		public void onEvent();
	}

	private HashMap<Type, List<Handler>> handlers = new HashMap<Type, List<Handler>>();

	public static void addHandler(Type type, Handler handler) {
		if (instance.handlers.containsKey(type)) {
			instance.handlers.get(type).add(handler);
		} else {
			instance.handlers.put(type, new ArrayList<Handler>());
			instance.handlers.get(type).add(handler);
		}
	}

	public static void fireSignal(Type type) {
		for (Handler handler : instance.handlers.get(type)) {
			handler.onEvent();
		}
	}

}
