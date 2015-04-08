import java.net.Socket;

public class Connection extends Thread{
	Socket socket;
	
	public Connection(Socket s) {
		this.socket = s;
	}

}
