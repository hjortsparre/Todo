package todo.ws.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HTTPHelper {

	public static String get(String path, String... parameters)
			throws Exception {

		if (parameters != null) {
			path += "?";
			for (int i = 0; i < parameters.length; i += 2) {
				path += parameters[i] + "="
						+ URLEncoder.encode(parameters[i + 1], "UTF-8") + "&";
			}
		}
		URL url = new URL(path);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.connect();

		int responseCode = connection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			InputStream in = connection.getInputStream();

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));

			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line + "\n");
			}
			if (builder.length() > 0) {
				builder.deleteCharAt(builder.length() - 1);
			}
			in.close();
			return builder.toString();

		} else {
			return responseCode + "";
		}

	}

}
