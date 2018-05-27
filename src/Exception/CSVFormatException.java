package Exception;

/**
The Java doc should describe the general meaning of the exception and the situations in which it might occur. The goal is to help other developers to understand your API and to avoid common error scenarios.
 */

@SuppressWarnings("serial")
public class CSVFormatException extends Exception{
	
	public CSVFormatException(String message) {
		
		System.out.println(message);
	}

}
	