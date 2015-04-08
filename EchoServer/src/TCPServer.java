import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.net.ServerSocket;
import java.net.Socket;

class TCPServer extends Thread {
  public static void main(String argv[]) throws Exception {
    String clientSentence;
    String capitalizedSentence;
    ServerSocket welcomeSocket = new ServerSocket(8888);
    System.out.println("Server started at Port: " + welcomeSocket.getLocalPort());
    
    while(true) {
    	// for each client connection
	    Socket connectionSocket = welcomeSocket.accept();
	    System.out.println("OPENED: (" + connectionSocket.getInetAddress() + ", " + connectionSocket.getLocalPort() + ")");

	    while(true) {
	    	// for each message to one client 
	    	try {
	    	    DataInputStream inFromClient = new DataInputStream(connectionSocket.getInputStream());
	    	    DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
	    		clientSentence = inFromClient.readUTF();
	      		capitalizedSentence = "("+connectionSocket.getInetAddress() + ", " + connectionSocket.getLocalPort()+") MESSAGE: " + clientSentence.toUpperCase();
	      		System.out.println("SEND: " + capitalizedSentence);
	      		outToClient.writeUTF(capitalizedSentence);
	    	} catch (EOFException e) {
	    		System.out.println("CLOSE: (" + connectionSocket.getInetAddress() + ", " + connectionSocket.getLocalPort() + ")");
	    		break;
	    	}
	    }
    }
  }
}