package methods;

import org.testng.Reporter;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.util.List;

/**
 * Handles logging to the TestNG file and attaching the log at the end of each
 * failed test.
 */
public class Logger {
	/**
	 * @param msg - Any text that needs to be recorded in the TestNG log.
	 */
	public static void log(String msg) {
		Reporter.log(msg, true);
	}

	/**
	 * @param msg  - Any text that needs to be recorded in the TestNG log.
	 * @param args - The format specifier
	 */
	public static void log(String msg, Object... args) {
		Reporter.log(String.format(msg, (Object[]) args), true);
	}

	/**
	 * Method to add the TestNG log to an Allure report
	 *
	 * @param strings Log lines
	 */
	@Attachment
	public static String attachTestNGlog(List<String> strings) {
		StringBuilder builder = new StringBuilder();
		for (String s : strings) {
			builder.append(s).append("\n");
		}
		Logger.log("Attach TestNG log file");
		return builder.toString();
	}

}
