import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

public class ServerListener extends Thread {
	private Socket socket;
	private String sentenceFromServer;

	public ServerListener(Socket s) {
		this.socket = s;
	}

	public void run() {
		try {
			DataInputStream inFromServer = new DataInputStream(
					this.socket.getInputStream());
			ReentrantLock mutex = new ReentrantLock();

			while (true) {
				sentenceFromServer = inFromServer.readUTF();

				mutex.lock();
				System.out.print("\b\b\b\b<<< " + sentenceFromServer + "\n>>> ");
				mutex.unlock();
				if (sentenceFromServer.endsWith("exit")) {
					this.socket.close();
					System.out
							.println("\nExiting server connection ... good bye");
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
