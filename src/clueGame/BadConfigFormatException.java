package clueGame;

/**
 * 
 * @author Lewis Setter
 * @author Joe Graff
 *
 */

/**
 * BadConfigFormatException looks for formatting errors in the config files and will throw and exception if one is found
 */
public class BadConfigFormatException extends Exception{
	
	// default constructor
	public BadConfigFormatException() {
		System.out.println("Error of with configuration files");
	}
	
	public BadConfigFormatException(String message) {
		System.out.println(message);
	}
}
