package de.gaalop;

import java.util.ArrayList;
import java.util.List;

/**
 * General-purpose class for different notifications as status information, warnings or errors which can be passed to
 * observers. Additionally, this class can be used as a central means to store and retrieve notifications, e.g. warnings
 * that occurred at different places.
 * 
 * @author Christian Schwinn
 * 
 */
public abstract class Notifications {

	private static List<Warning> warnings = new ArrayList<Warning>();

	public static void addWarning(Warning warning) {
		warnings.add(warning);
	}

	public static void addWarning(String message) {
		warnings.add(new Warning(message));
	}

	public static List<Warning> getWarnings() {
		return warnings;
	}

	public static boolean hasWarnings() {
		return !warnings.isEmpty();
	}

	/**
	 * Clears the list of warnings stored so far. This method should be called from a "safe" place only, i.e. where
	 * warnings have been displayed to the user, for instance.
	 */
	public static void clearWarnings() {
		warnings.clear();
	}

	/**
	 * Notifies that progress has been made. Can be used to increment a status bar, for example.
	 */
	public static final class Progress extends Notifications {
	}
	
	/**
	 * This class can be used to notify observers that the compilation process has started, for example.
	 */
	public static final class Start extends Notifications {
	}

	/**
	 * This class can be used to notify observers that the compilation process has been finished, for example.
	 */
	public static final class Finished extends Notifications {
	}

	/**
	 * Notifies observers about some error.
	 */
	public static final class Error extends Notifications {
		private Throwable error;

		public Error(Throwable error) {
			this.error = error;
		}

		public Throwable getError() {
			return error;
		}
	}

	/**
	 * This class can be used to notify observers about some status information, for example.
	 */
	public static final class Info extends Notifications {
		private String message;

		public Info(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}

	/**
	 * This class can be used to notify observers about some warnings, for example.
	 */
	public static final class Warning extends Notifications {
		private String message;

		public Warning(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}

	/**
	 * This notification is a container for integer values. It can be used update status bars, for example.
	 */
	public static final class Number extends Notifications {
		private int value;

		public Number(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

}
