import genClasses.EchoMessage;
import genClasses.EchoMessageType;
import genClasses.ObjectFactory;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

class TCPClient {
	public static void main(String argv[]) throws RuntimeException, JAXBException {
		String sentence;
		String xmlSentence;
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
				System.in));
		Socket clientSocket;
		ObjectFactory of = new ObjectFactory();
		EchoMessage message = of.createEchoMessage();
		JAXBContext jc = JAXBContext.newInstance(EchoMessage.class);
		Marshaller ms = jc.createMarshaller();
		ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
				
		if (argv.length == 2) {
			try {
				clientSocket = new Socket((String) argv[0], Integer.parseInt(argv[1]));
				DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
				
				System.out.println("Ohai, connected to Server: (" + argv[0] + ", " + argv[1] + ")");
				ServerListener sl = new ServerListener(clientSocket);
				sl.start();
				
				
				while(true) {
					System.out.print(">>> ");
					sentence = inFromUser.readLine();
					bytestream.reset();
					
					message.setContent(sentence);
					message.setType(EchoMessageType.DEFAULT);
					message.setSender("127.0.0.1 8888");
					ms.marshal(message, bytestream);
					xmlSentence = new String(bytestream.toByteArray());
					outToServer.writeUTF(xmlSentence);
				}
			} catch (IOException e) {
				throw new RuntimeException("Irgendwas schief gelaufen");
			}
		} else {
			throw new RuntimeException("Falsche Eingabe");
		}
	}
}
