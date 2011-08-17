package todo.data.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class TodoDataLogger {

	static class HtmlFormatter extends Formatter {

		private SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss:SSSS");

		@Override
		public String format(LogRecord rec) {

			StringBuffer buf = new StringBuffer();

			buf.append("<br />");

			if (rec.getLevel().intValue() >= Level.WARNING.intValue()) {
				buf.append("<b>");
				buf.append(rec.getLevel());
				buf.append("</b>");
			} else {
				buf.append(rec.getLevel());
			}

			buf.append("&nbsp;");
			buf.append("&nbsp;");
			buf.append("-");
			buf.append("&nbsp;");
			buf.append("&nbsp;");

			buf.append("Thread: " + rec.getThreadID());

			buf.append("&nbsp;");
			buf.append("&nbsp;");
			buf.append("-");
			buf.append("&nbsp;");
			buf.append("&nbsp;");

			buf.append(format.format(new Date(rec.getMillis())));

			buf.append("&nbsp;");
			buf.append("&nbsp;");
			buf.append("-");
			buf.append("&nbsp;");
			buf.append("&nbsp;");

			buf.append(formatMessage(rec));

			return buf.toString();

		}

	}

	private static final Logger logger = Logger.getLogger(TodoDataLogger.class
			.getName());

	static {
		try {

			String logPath = System.getProperty("logPath");
			if (logPath == null) {
				logPath = "./";
			}
			if (!logPath.endsWith("/")) {
				logPath += "/";
			}

			Formatter formatter = new HtmlFormatter();
			FileHandler fileHandler = new FileHandler(logPath
					+ TodoDataLogger.class.getSimpleName() + ".log.html", true);
			fileHandler.setFormatter(formatter);

			logger.addHandler(fileHandler);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void info(Class callingClass, String methodName,
			String... parameters) {

		logger.log(Level.INFO,
				createMessage(callingClass, methodName, null, null, parameters));
	}

	public static void warning(Class callingClass, String methodName,
			Exception e, String... parameters) {

		logger.log(
				Level.WARNING,
				createMessage(callingClass, methodName, e.getClass(),
						e.getMessage(), parameters));
	}

	public static void severe(Class callingClass, String methodName,
			Exception e, String... parameters) {

		// Maybe contact admin via email?

		logger.log(
				Level.SEVERE,
				createMessage(callingClass, methodName, e.getClass(),
						e.getMessage(), parameters));
	}

	private static String createMessage(Class callingClass, String methodName,
			Class exceptionClass, String exceptionMessage, String... parameters) {
		StringBuilder message = new StringBuilder();
		message.append(callingClass.getSimpleName());
		message.append(" ");
		message.append(methodName);
		message.append("(");
		for (String parameter : parameters) {
			message.append(parameter).append(", ");
		}
		message.deleteCharAt(message.length() - 1);
		message.deleteCharAt(message.length() - 1);
		message.append(")");

		if (exceptionClass != null) {
			message.append(" - Threw exception: "
					+ exceptionClass.getSimpleName());
		}
		if (exceptionMessage != null) {
			message.append(" - With message: " + exceptionMessage);
		}

		return message.toString();
	}

}
