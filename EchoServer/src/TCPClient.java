import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

class TCPClient extends Thread {
	public static void main(String argv[]) throws RuntimeException {
		String sentence;
		String modifiedSentence;
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
				System.in));
		Socket clientSocket;

		if (argv.length == 2) {
			try {
				clientSocket = new Socket((String) argv[0], Integer.parseInt(argv[1]));
				DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
				//DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());
				
				System.out.println("Ohai, connected to Server: (" + argv[0] + ", " + argv[1] + ")");
				ServerListener sl = new ServerListener(clientSocket);
				sl.start();
				
				
				while(true) {
					System.out.print(">>> ");
					sentence = inFromUser.readLine();
					outToServer.writeUTF(sentence);
					
					
					//modifiedSentence = inFromServer.readUTF();
					/*System.out.println("FROM SERVER: " + modifiedSentence);
					if(modifiedSentence.endsWith("exit")) {
						clientSocket.close();
						System.out.println("\nExiting server connection ... good bye");
						break;
					}*/
				}
			} catch (IOException e) {
				throw new RuntimeException("Irgendwas schief gelaufen");
			}
		} else {
			throw new RuntimeException("Falsche Eingabe");
		}

	}
}
