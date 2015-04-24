import java.util.concurrent.Callable;

public class Primzahl implements Callable<Boolean> {
	private long zahl;
	
	public Primzahl(long zahl) {
		this.zahl = zahl;
	}

	@Override
	public Boolean call() throws Exception {

		for (long i = 2; i <= Math.sqrt((long)zahl); ++i) {
			if(zahl%i == 0) {
				System.out.println("Zahl " + this.zahl + " ist keine Primzahl");
				return Boolean.TRUE;		
			}
		}
		System.out.println("Zahl " + this.zahl + " ist eine Primzahl");
		return Boolean.FALSE;
	}

}
