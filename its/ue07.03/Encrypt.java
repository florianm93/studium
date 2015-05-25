import java.security.MessageDigest;
import java.util.ArrayList;

public class Encrypt {
	public static String phrase = "0241739d4c2596e4f12a2fd531de6163";
	public static String characters[] = { "0", "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
			"k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
			"x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
			"X", "Y", "Z" };
	/*public static String characters[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
		"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
		"X", "Y", "Z" };*/
	public static ArrayList<String> book = new ArrayList<String>();

	public static void encrypt(String word) throws Throwable {
		MessageDigest md5 = MessageDigest.getInstance("md5");
		byte[] inBuffer = word.getBytes();
		byte[] outBuffer = md5.digest(inBuffer);

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < outBuffer.length; ++i) {
			sb.append(Integer.toString((outBuffer[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		if (sb.toString().equals(phrase)) {
			System.out.print("found:\t\t" + word + "\t\t" + sb.toString() + "\n");
			System.exit(0);
		}
		//System.out.println(word + "\t\t" + sb.toString());
	}

	public static void tryWords(int lengthWord) throws Throwable {
		int lengthPossibleChars = characters.length;
 
		for (int k = 1; k <= lengthWord; ++k) {
			for (int i = 0; i < Math.pow(lengthPossibleChars, k); ++i) {
				StringBuilder word = new StringBuilder();
				word.append(characters[i % lengthPossibleChars]);
				for (int j = 1; j < k; ++j) {
					word.append(characters[i
							/ (1 + (int) Math.pow(k, j))
							% lengthPossibleChars]);
				}
				encrypt(word.toString());
			}
		}
	}

	public static void main(String args[]) throws Throwable {
		// Woerterbuch Angriff
		book.add("Matse");
		book.add("MATSE");
		book.add("matse");
		book.add("12345");
		book.add("pwd");
		book.add("1234");
		for (String word : book) {
			Encrypt.encrypt(word);
		}

		// sonst:
		// BruteForce aller Loesungen
		tryWords(5);
		
		
		/*
		 * stats (real mit /usr/bin/time):
		 * 3m12.320sec: Nur Grossbuchstaben, Ausgabe jeden Hashwertes, ohne Woertbuch
		 * 0m23.371sec: Nur Grossbuchstaben, keine Hashwerte ausgeben, ohne Woertbuch
		 * 0m00.144sec: Alle Zeichen, keine Hashwerte ausgeben, mit Woerterbuch
		 * 
		 */

	}

}
