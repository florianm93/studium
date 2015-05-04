import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PrimMain {
	public static boolean isPrimzahl(int zahl) {
		boolean values[] = new boolean[zahl + 2];

		for (int i = 2; i <= zahl; ++i) {
			for (int j = i + i; j <= zahl; j += i) {
				values[j] = true;
			}
		}
		return values[zahl];
	}

	public static void main(String[] args) {
		long eingabe = -1;
		Scanner sc = new Scanner(System.in);
		ExecutorService pool = Executors.newFixedThreadPool(5);

		// Testdaten, einkommentieren fuer viele Werte
		/*int anzahl = 10000000;
		for (int i = anzahl; i < anzahl * 10; ++i) {
			Callable<Boolean> pr = new Primzahl((long) (Math.random() * anzahl));
			Future<Boolean> future = pool.submit(pr);
		}*/

		do {

			System.out.println("Eingabe: ");
			eingabe = Long.parseLong(sc.nextLine());
			if (eingabe > 0) {
				Callable<Boolean> pr = new Primzahl(eingabe);
				Future<Boolean> ft = pool.submit(pr); 		// Berechnung gibt Ergebnis aus, boolean wird nur zurueck gegeben weil Java bloed ist und kein void erlaubt
			} else {
				System.out.println("Nur Werte groesser 0 sind erlaubt.");
			}
		} while (eingabe != -1);

		pool.shutdown();
		System.out.println("Programm wird beendet ...");
		sc.close();
	}

}
