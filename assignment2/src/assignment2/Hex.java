/**
 * 
 */
package assignment2;

/**
 * @author Robert Desktop
 *
 */
public class Hex {
	
	private final String charString;
	
	public Hex(String hex) {
		this.charString = hexToChar(hex);
	}
	
	private String hexToChar(String hex) {
		StringBuilder output = new StringBuilder();
		final int HEX_LENGTH = 2;
	    for (int i = 0; i < hex.length(); i+=HEX_LENGTH) {
	        String str = hex.substring(i, i+HEX_LENGTH);
	        int charASCII = Integer.parseInt(str, 16);
	        char ch = (char) charASCII;
	        output.append(ch);
	    }
		return output.toString();
	}

	public char getChar(int i) {
		char ch = charString.charAt(i);
		return ch;
	}
}
