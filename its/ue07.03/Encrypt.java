import java.security.MessageDigest;
import java.util.ArrayList;

public class Encrypt {
	public static String phrase = "0241739d4c2596e4f12a2fd531de6163";
	public static String characters[] = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	public static ArrayList<String> results = new ArrayList<String>();
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
		if (sb.toString().equalsIgnoreCase(phrase)) {
			System.out.print("found:\t\t" + word + "\t\t" + sb.toString());
			results.add(word + "\t\t" + sb.toString());
		}
		System.out.println(word + "\t\t" + sb.toString());
	}
	
	public static void tryWords(int lengthWord) throws Throwable {
		int lengthPossibleChars = characters.length;
		results.clear();
		
		for (int i=0; i<Math.pow(lengthPossibleChars, lengthWord); ++i) {
			StringBuilder word = new StringBuilder();
			
			for (int j=0; j<lengthWord; ++j) {
				word.append(characters[i/((int)Math.pow(lengthWord, j)) % lengthWord]);
			}
			encrypt(word.toString());
		}
		
		// Ausgabe der Ergebnisliste:
		for (String r : results) {
			System.out.println(r);
		}
	}
	
	

	public static void main(String args[]) throws Throwable {
		// Woerterbuch Angriff
		book.add("Matse");
		book.add("MATSE");
		book.add("matse");
		for (String word: book) {
			Encrypt.encrypt(word);
		}
		
		// BruteForce aller Loesungen
		tryWords(5);
		
		
		
	}

}
