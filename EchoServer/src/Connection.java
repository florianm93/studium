import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection extends Thread {
	Socket socket;
	int chars_recieved = 0;
	int messages_recieved = 0;
	int messages_longest = 0;
	int messages_shortest = Integer.MAX_VALUE;
	int messages_length = -1;
	DataInputStream inFromClient;
	DataOutputStream outToClient;

	public Connection(Socket s) {
		this.socket = s;
	}

	public String printStatistic() {
		return "<--- Statistics " + this.socket.getInetAddress() + ":"
				+ this.socket.getPort() + " -->\n" + "Chars Recieved: "
				+ chars_recieved + "\n" + "Messages Recieved: "
				+ messages_recieved + "\n" + "Longest Message: "
				+ messages_longest + "\n" + "Shortest Message: "
				+ messages_shortest + "\n";
	}

	public void sendMessage(String message) {
		try {

			outToClient.writeUTF(message);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		String clientSentence;
		String sentenceToServer;
		System.out.println("OPENED: (" + this.socket.getInetAddress() + ", "
				+ this.socket.getLocalPort() + ")");
		try {
			inFromClient = new DataInputStream(this.socket.getInputStream());
			outToClient = new DataOutputStream(this.socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			// for each message to one client
			try {

				clientSentence = inFromClient.readUTF();

				if (clientSentence.equals("showstat")) {
					sentenceToServer = this.printStatistic();
					sendMessage(sentenceToServer);
				} else if (clientSentence.equals("showallstat")) {
					sentenceToServer = TCPServer.printAllStatistics();
					sendMessage(sentenceToServer);
				} else if (clientSentence.startsWith("broadcast")) {
					// start stats
					messages_length = clientSentence.length();
					chars_recieved += messages_length;
					++messages_recieved;
					if (messages_length > messages_longest) {
						messages_longest = messages_length;
					}
					if (messages_length < messages_shortest) {
						messages_shortest = messages_length;
					}
					// end stats

					if (clientSentence.length() < 9) {
						sentenceToServer = "(" + this.socket.getInetAddress()
								+ ", " + this.socket.getLocalPort()
								+ ") BC-MESSAGE: ";
					} else {
						sentenceToServer = "(" + this.socket.getInetAddress()
								+ ", " + this.socket.getLocalPort()
								+ ") BC-MESSAGE: "
								+ clientSentence.substring(9);
					}
					TCPServer.sendBroadcastMessage(sentenceToServer);
				} else {
					// start stats
					messages_length = clientSentence.length();
					chars_recieved += messages_length;
					++messages_recieved;
					if (messages_length > messages_longest) {
						messages_longest = messages_length;
					}
					if (messages_length < messages_shortest) {
						messages_shortest = messages_length;
					}
					// end stats
					sentenceToServer = "(" + this.socket.getInetAddress()
							+ ", " + this.socket.getLocalPort() + ") MESSAGE: "
							+ clientSentence;
					System.out.println("SEND: " + sentenceToServer);
					sendMessage(sentenceToServer);
				}

				// outToClient.writeUTF(senenceToServer);
			} catch (IOException e) {
				System.out.println("CLOSE: (" + this.socket.getInetAddress()
						+ ", " + this.socket.getLocalPort() + ")");
				break;
			}
		}
	}

}
