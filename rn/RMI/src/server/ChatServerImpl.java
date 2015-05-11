package server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import client.IChatClientCallback;

public class ChatServerImpl extends UnicastRemoteObject implements IChatServer {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static final int registryPort = 1099;

	// Hier speichern wir die Callbacks!
	@SuppressWarnings("unused")
	private Map<String, IChatClientCallback> users;

	public ChatServerImpl() throws RemoteException {
		super();
		HashMap<String, IChatClientCallback> callbackHashMap = new HashMap<String, IChatClientCallback>();
		users = Collections.synchronizedMap(callbackHashMap);
	}

	public boolean login(String userID, IChatClientCallback receiver)
			throws RemoteException {
		users.put(userID, receiver);

		for (IChatClientCallback elem : users.values()) {
			elem.receiveUserLogin(userID, users.keySet().toArray());
		}
		return true;
	}

	public boolean logout(String userID) throws RemoteException {
    
		users.remove(userID);
		for (IChatClientCallback elem: users.values()) {
			elem.receiveUserLogout(userID, users.keySet().toArray());
		}
    return true;
  }

	public void chat(String userID, String message) throws RemoteException {
		for (IChatClientCallback elem: users.values()) {
			elem.receiveChat(userID, message);
		}
	}

	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(registryPort);
			Naming.bind("rmi://localhost/chat", new ChatServerImpl());
			System.out.println("ChatServer ready");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(0);
		}
	}
}
