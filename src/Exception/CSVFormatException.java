package Exception;

/**
The Javadoc should describe the general meaning of the exception and the situations in which it might occur. The goal is to help other developers to understand your API and to avoid common error scenarios.
 */

public class CSVFormatException extends Exception{
	public CSVFormatException(String message) {
		super(message);
	}

}
	