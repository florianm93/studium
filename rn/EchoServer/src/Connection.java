import genClasses.EchoMessage;
import genClasses.EchoMessageType;
import genClasses.ObjectFactory;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

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
				+ (messages_shortest==Integer.MAX_VALUE ? "-" : messages_shortest ) + "\n";
	}
	
	public void updateStatistics(String message) {
		messages_length = message.length();
		chars_recieved += messages_length;
		++messages_recieved;
		if (messages_length > messages_longest) {
			messages_longest = messages_length;
		}
		if (messages_length < messages_shortest) {
			messages_shortest = messages_length;
		}
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
		Unmarshaller us = null;
		ObjectFactory of = new ObjectFactory();
		EchoMessage message = of.createEchoMessage();
		EchoMessage messageToServer = of.createEchoMessage();
		try {
			JAXBContext jc = JAXBContext.newInstance(EchoMessage.class);
			us = jc.createUnmarshaller();
		} catch (JAXBException e) {
			;
		}
		
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
							
				ByteArrayInputStream bytestream = new ByteArrayInputStream(clientSentence.getBytes());
				try {
					message = (EchoMessage) us.unmarshal(bytestream);
				} catch(JAXBException e) {
					e.printStackTrace();
				}

				// switch-case:
				if (message.getContent().equals("showstat")) {
					messageToServer.setType(EchoMessageType.SHOWSTAT);
					messageToServer.setContent(this.printStatistic());
				} else if (message.getContent().equals("showallstat")) {
					messageToServer.setType(EchoMessageType.SHOWALLSTAT);
					messageToServer.setContent(TCPServer.printAllStatistics());
				} else if (message.getContent().startsWith("broadcast")) {
					messageToServer.setType(EchoMessageType.BROADCAST);
					if (message.getContent().length() < 10) {
						messageToServer.setContent("");
					} else {
						messageToServer.setContent(message.getContent().substring(10));
					}
					updateStatistics(messageToServer.getContent());
					System.out.println("SEND-BC: " + messageToServer.getContent());
				} else {
					messageToServer.setType(EchoMessageType.DEFAULT);
					messageToServer.setContent(message.getContent());
					updateStatistics(messageToServer.getContent());	
					System.out.println("SEND: " + messageToServer.getContent());
				}
				
				// send massage to client
				if(messageToServer.getContent() != "" && messageToServer.getType() == EchoMessageType.BROADCAST) {
					TCPServer.sendBroadcastMessage(messageToServer.getContent());
				}
				else if(messageToServer.getContent() != "") {
					sendMessage(messageToServer.getContent());
				}
				else {
					System.out.println("ERROR: message could not been sent.");
				}
			} catch (IOException e) {
				System.out.println("CLOSE: (" + this.socket.getInetAddress()
						+ ", " + this.socket.getLocalPort() + ")");
				break;
			}
		}
	}

}
