import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class TCPServer {
	private static ArrayList<Connection> connections = new ArrayList<>();

	public static String printAllStatistics() {
		String stats = "";
		for(Connection c: connections) {
			stats += c.printStatistic() + "\n";
		}
		return stats;
	}
	public static void sendBroadcastMessage(String message) {
		for(Connection c: connections) {
			c.sendMessage(message);
		}
	}
	
	public static void main(String argv[]) throws Exception {
		ServerSocket welcomeSocket = new ServerSocket(8888);
		System.out.println("Server started at Port: "
				+ welcomeSocket.getLocalPort());

		while (true) {
			// for each client connection
			Connection conn = new Connection(welcomeSocket.accept());
			connections.add(conn);
			conn.start();
			
		}
	}
}