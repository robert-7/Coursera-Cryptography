/**
 * 
 */
package assignment2;

import java.util.Arrays;

/**
 * @author Robert Desktop
 *
 */
public class BOTP {

	private static int NUM_PHRASES = 7;
	private static int CIPHER_CHARS = 31;
	private static char UNKNOWN = '~';
	private static char SPACE = ' ';
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Get the array of ciphers and messages
		Hex[] ciphers = setCiphersArray();
		char[][] messages = setMessagesArray();
		
		//starting from the first char in each phrase
		for (int ch = 0; ch != CIPHER_CHARS; ch++) {
			char[] cipherChars = new char[NUM_PHRASES];
			for (int i=0; i!=NUM_PHRASES; i++) {
				cipherChars[i] = ciphers[i].getChar(ch);
			}
			char[] decryptedChars = attemptToSolveAllMessageChars(cipherChars);
			messages = addSolutionsToMessages(ch, decryptedChars, messages);
			print(messages); System.out.println();
		}
	}

	private static void print(char[][] messages) {
		for (int i = 0; i != NUM_PHRASES; i++) {
			for (int j = 0; j != CIPHER_CHARS; j++) {
				System.out.print(messages[i][j]);
			} System.out.print('\n');
		}
	}

	private static char[][] addSolutionsToMessages(int ch, char[] decryptedChars,
			char[][] messages) {
		
		for (int m=0; m!=NUM_PHRASES; m++) {
			messages[m][ch] = decryptedChars[m];
		}
		return messages;
	}

	private static char[] attemptToSolveAllMessageChars(char[] cipherChars) {
		char[] decryptedChars = new char[] {'~','~','~','~','~','~','~'};
		int spaceIndex = findIndexOfFirstSpace(cipherChars);
		if (spaceIndex != -1) {
			decryptedChars = solveRemainingChars(spaceIndex, cipherChars);
		} else {
			printPossibleCharsInColumn(cipherChars);
		}
		return decryptedChars;
	}

	private static void printPossibleCharsInColumn(char[] cipherChars) {
		char[] possibleDecryptedChars = new char[NUM_PHRASES]; 
		int count = 0;
		for (int key=0;key!=256;key++) {
			for (int i=0;i!=NUM_PHRASES;i++) {
				int xor = cipherChars[i] ^ key;
				if (((xor >= 65)&&(xor <= 122))||((xor == 33)||(xor == 63)||(xor == 46)) ) {
					count++;
					possibleDecryptedChars[i] = (char) xor;
				}
			}
			
			if (count == NUM_PHRASES) {
				System.out.println("key: " + key + " | " + Arrays.toString(possibleDecryptedChars));
			} 
			
			count = 0;
		}
	}

	private static char[] solveRemainingChars(int spaceIndex, char[] cipherChars) {
		char[] decryptedChars = new char[NUM_PHRASES];
		decryptedChars[spaceIndex] = SPACE;
		for (int i = ((spaceIndex+1) % NUM_PHRASES); i != spaceIndex; i=((i+1) % NUM_PHRASES)) {
			
			// gives us c0 + c1 = m0 + m1
			// but we know c0 + c1 = xor
			// and we know m0 = SPACE
			// so SPACE + xor = m2
			
			int xor = cipherChars[spaceIndex] ^ cipherChars[i]; 
			int decryptedCharInt = xor ^ SPACE;
			char decryptedChar = (char) decryptedCharInt;
			decryptedChars[i] = decryptedChar;
		}
		return decryptedChars;
	}

	private static int findIndexOfFirstSpace(char[] cipherChars) {
		int spaceIndex = 0;
		int[] c = new int[2];
		for (c[0]=0; c[0]!=cipherChars.length-1; c[0]++) {
			for (c[1]=c[0]+1; c[1]!=cipherChars.length; c[1]++) {
				
				// we're looking at two cipher chars at a time
				// let's save their cipher chars
				char[] cchars = new char[2];
				cchars[0] = cipherChars[c[0]];
				cchars[1] = cipherChars[c[1]];
				
				// we can't do anything if both chars are the same
				if (cchars[0] == cchars[1]) {
					continue;
				}
				
				// get the xor of the value
				int xor = cchars[0] ^ cchars[1];
				
				// if we have a space
				if ((xor >= 64)&&(xor < 128)) {
					// then we should figure out if the index is c[0] or c[1]
					for (int i = c[0]+2; i != c[0]; i=(i+1 % NUM_PHRASES)) {
						if ((cipherChars[i] != cchars[0]) && (cipherChars[i] != cchars[1])) {
							xor = cchars[0] ^ cipherChars[i];
							if ((xor >= 64)&&(xor < 128)) {
								// hurray, c[0] is a space
								spaceIndex = c[0];
								return spaceIndex;
							} else {
								// hurray, c[1] is a space
								spaceIndex = c[1];
								return spaceIndex;
							}
						}
					} 
					// if we got here, everything has to be a space?!?!
					
				} else {
					// continue because we don't care
					continue;
				}
			}
		}
		// if we got here, then we couldn't find the space
		return -1;
	}

	private static char[][] setMessagesArray() {
		char[][] messages = new char[NUM_PHRASES][];
		for (int i = 0; i != messages.length; i++) {
			messages[i] = new char[CIPHER_CHARS];
			for (int j=0; j!=messages[i].length;j++) {
				messages[i][j] = UNKNOWN;
			}
		}
		return messages;
	}

	private static Hex[] setCiphersArray() {
		Hex[] ciphers = new Hex[7];
		ciphers[0] = new Hex("BB3A65F6F0034FA957F6A767699CE7FABA855AFB4F2B520AEAD612944A801E");
		ciphers[1] = new Hex("BA7F24F2A35357A05CB8A16762C5A6AAAC924AE6447F0608A3D11388569A1E");
		ciphers[2] = new Hex("A67261BBB30651BA5CF6BA297ED0E7B4E9894AA95E300247F0C0028F409A1E");
		ciphers[3] = new Hex("A57261F5F0004BA74CF4AA2979D9A6B7AC854DA95E305203EC8515954C9D0F");
		ciphers[4] = new Hex("BB3A70F3B91D48E84DF0AB702ECFEEB5BC8C5DA94C301E0BECD241954C831E");
		ciphers[5] = new Hex("A6726DE8F01A50E849EDBC6C7C9CF2B2A88E19FD423E0647ECCB04DD4C9D1E");
		ciphers[6] = new Hex("BC7570BBBF1D46E85AF9AA6C7A9CEFA9E9825CFD5E3A0047F7CD009305A71E");
		return ciphers;
	}
	
}
