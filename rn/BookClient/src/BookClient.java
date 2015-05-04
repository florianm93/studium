import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

/*
 * Aufgabe 01.2: Bei der Interoperabilitaet muss drauf geachtet werden, dass die Attribute der 
 * Book-Klasse die selbe Reihenfolge haben, ausserdem muessen die Variablenname gleich sein, 
 * andernfall wird fuer diese Werte beim lesen "null" eingetragen. 
 * 
 * Das Protokoll wird durch die Book Klasse festegelgt, da abhaehngig von ihr die Daten anders
 * darfgestellt werden.  
 * 
 * Aufgabe 01.3: Der Vorteil von der JSON Verison ist, das die Daten in einem einheitlichen Format 
 * sind und Menschenlesbar gespeichert sind. Es ist dadruch moeglich die Dateien anderweitig zu verwenden
 * bzw. in anderen Clients die auch einen JSON Parser besitzen zu lesen und zu veraendern.
 */
 
public class BookClient {
 
  protected static ArrayList<Book> books = new ArrayList<>();
  protected static Scanner sc = new Scanner(System.in);
  protected static File bookFile = new File("books.json");
 
  public static void main(String[] args) {
 
    int eingabe=-1;
    while(eingabe !=0) {
      System.out.println("Was wollen Sie?\n"
      +" (0) Programm beenden\n"
      +" (1) Buecher aus Datei laden\n"
      +" (2) Buecher anzeigen\n"
      +" (3) Buch hinzufuegen\n"
      +" (4) Buch loeschen\n"
      +" (5) Buecher in Datei speichern");
      eingabe=new Scanner(System.in).nextInt();
      switch(eingabe) {
        case 1:
        buecher_laden( bookFile );
        break;
        case 2:
        buecher_anzeigen();
        break;
        case 3:
        buch_hinzufuegen();
        break;
        case 4:
        buch_loeschen();
        break;
        case 5:
        buecher_speichern(bookFile);
        break;
      }
    }
  }
 
  public static void buecher_laden(File server) {
    FileInputStream ifs;
    ObjectInputStream ois = null;
    JSONDeserializer<ArrayList<Book>> deserializer = new JSONDeserializer<>();
    
    try {
    	ifs = new FileInputStream(server);
    	ois = new ObjectInputStream(ifs);
	    
	    try {
	    	while(true) {
	    		String tmp = (String)ois.readObject();
	    		books = deserializer.deserialize(tmp);

	    	}
	    } catch (EOFException e) {
	    	System.out.println("Alle Buecher geladen");
	    } catch (ClassNotFoundException | IOException e) {
	    	e.printStackTrace();
	    }	    
    } catch (IOException e) {
    	System.out.println("Wat?!");
    	e.printStackTrace();
    }
  }
 
  public static void buecher_anzeigen() {
	for(Book e: books) {
    	System.out.print(e);
    }
	System.out.println("------------------------------------------------------\n\n");
  }
 
 
  public static void buch_hinzufuegen() {
    Book tmp = new Book();
    
    System.out.println("Vorname: ");
    tmp.setVorname(sc.nextLine());
    System.out.println("Nachname: ");
    tmp.setNachname(sc.nextLine());
    System.out.println("ISBN: ");
    tmp.setIsbn(sc.nextLine());
    System.out.println("Titel:");
    tmp.setTitel(sc.nextLine());
    
    books.add(tmp);
  }
 
  public static void buch_loeschen() {
	  buecher_anzeigen();
	  System.out.println("ISBN des zu loeschenden Buches:");
	  String isbn = sc.nextLine();
	  for(int i=0; i<books.size(); ++i) {
		  if(books.get(i).getIsbn().equals(isbn)) {
			  books.remove(i);
			  System.out.println("Erfolgreich geloescht!");
		  }
	  }
  }
 
  public static void buecher_speichern(File server) {
	    FileOutputStream ofs;
	    ObjectOutputStream oos = null;
	    JSONSerializer serializer = new JSONSerializer();
	    
	    try {
	    	ofs = new FileOutputStream(server);
	    	oos = new ObjectOutputStream(ofs);
		    try {
		    	String tmp = serializer.serialize(books);
		    	oos.writeObject(tmp);

		    	System.out.println("Buecher erfolgreich gespeichert!");
		    } catch (EOFException e) {
		    	//pass
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
		    
	    } catch (IOException e) {
	    	System.out.println("Wat?!");
	    	e.printStackTrace();
	    }
  }
 
}