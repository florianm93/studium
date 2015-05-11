package client;
 
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import server.IChatServer;
 
public class ChatClient extends JFrame implements IChatClientCallback {
 
  private static final long serialVersionUID = 1L;
  @SuppressWarnings("unused")
  private static final int callbackPort = 0;
  private IChatServer serverObject;
  private String userName;
  // GUI Variablen
  private JMenuBar jmenu;
  private JMenu fileMenu;
  private JMenuItem exitMenuItem;
  private JMenu commandsMenu;
  private JMenuItem loginMenuItem;
  private JMenuItem logoutMenuItem;
  private JMenuItem clearMenuItem;
  private JMenu viewMenu;
  private JPanel eastPanel;
  private JTextArea userList;
  private JPanel southPanel;
  private JTextField inputField;
  private JPanel centerPanel;
  private JScrollPane jScrollPane;
  private JTextArea textArea;
 
  public ChatClient(String serverAddress) throws RemoteException {
    // Setup GUI
    this.setBounds(25, 18, 500, 400);
    jmenu = new JMenuBar();
    this.setJMenuBar(jmenu);
    fileMenu = new JMenu("File");
    jmenu.add(fileMenu);
    exitMenuItem = new JMenuItem("Exit");
    fileMenu.add(exitMenuItem);
    exitMenuItem.addActionListener(new ExitMenuItemListener());
    commandsMenu = new JMenu("Commands");
    jmenu.add(commandsMenu);
    loginMenuItem = new JMenuItem("Login");
    commandsMenu.add(loginMenuItem);
    loginMenuItem.addActionListener(new LoginMenuItemListener());
    logoutMenuItem = new JMenuItem("Logout");
    commandsMenu.add(logoutMenuItem);
    logoutMenuItem.addActionListener(new LogoutMenuItemListener());
    logoutMenuItem.setEnabled(false);
    clearMenuItem = new JMenuItem("Clear");
    commandsMenu.add(clearMenuItem);
    clearMenuItem.addActionListener(new ClearMenuItemListener());
    viewMenu = new JMenu("Look & Feel");
    ButtonGroup buttonGroup = new ButtonGroup();
    final UIManager.LookAndFeelInfo[] info = UIManager
    .getInstalledLookAndFeels();
    for (int i = 0; i < info.length; i++) {
      JRadioButtonMenuItem item = new JRadioButtonMenuItem(
      info[i].getName(), i == 0);
      final String className = info[i].getClassName();
      item.addActionListener(new ViewMenuItemListener(className));
      buttonGroup.add(item);
      viewMenu.add(item);
    }
    jmenu.add(viewMenu);
    Container thisContent = this.getContentPane();
    this.setFont(new java.awt.Font("dialog", 0, 12));
    this.setTitle("RMI-Chat");
    eastPanel = new JPanel();
    eastPanel.setPreferredSize(new Dimension(100, 10));
    Border eastPanelBorder0 = new EtchedBorder();
    eastPanel.setBorder(eastPanelBorder0);
    eastPanel.setMinimumSize(new Dimension(8, 25));
    userList = new JTextArea();
    Border userListBorder0 = new EmptyBorder(2, 2, 2, 2);
    userList.setBorder(userListBorder0);
    userList.setEditable(false);
    userList.setFont(new java.awt.Font("dialog", Font.BOLD, 12));
    BorderLayout eastPanelLayout = new BorderLayout();
    eastPanel.setLayout(eastPanelLayout);
    eastPanel.add(userList, BorderLayout.CENTER);
    southPanel = new JPanel();
    southPanel.setPreferredSize(new Dimension(10, 30));
    Border southPanelBorder0 = new EtchedBorder();
    southPanel.setBorder(southPanelBorder0);
    inputField = new JTextField();
    Border inputFieldBorder0 = new EmptyBorder(2, 2, 2, 2);
    inputField.setBorder(inputFieldBorder0);
    inputField.setLayout(null);
    inputField.addActionListener(new InputFieldListener());
    BorderLayout southPanelLayout = new BorderLayout();
    southPanel.setLayout(southPanelLayout);
    southPanel.add(inputField, BorderLayout.CENTER);
    centerPanel = new JPanel();
    Border centerPanelBorder0 = new EtchedBorder();
    centerPanel.setBorder(centerPanelBorder0);
    jScrollPane = new JScrollPane();
    jScrollPane
    .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    textArea = new JTextArea();
    Border textAreaBorder0 = new EmptyBorder(2, 2, 2, 2);
    textArea.setBorder(textAreaBorder0);
    textArea.setEditable(false);
    textArea.setFont(new java.awt.Font("dialog", Font.BOLD, 12));
    jScrollPane.getViewport().setView(textArea);
    BorderLayout centerPanelLayout = new BorderLayout();
    centerPanel.setLayout(centerPanelLayout);
    centerPanel.add(jScrollPane, BorderLayout.CENTER);
    BorderLayout thisContentLayout = new BorderLayout();
    thisContent.setLayout(thisContentLayout);
    thisContent.add(eastPanel, BorderLayout.EAST);
    thisContent.add(southPanel, BorderLayout.SOUTH);
    thisContent.add(centerPanel, BorderLayout.CENTER);
    this.addWindowListener(new MyWindowListener());
    try {
    	serverObject = (IChatServer) Naming.lookup("rmi://localhost/chat");
    } catch (Exception e) {
      System.out.println("Exception" + e);
      e.printStackTrace();
      return;
    }
    UnicastRemoteObject.exportObject(this, 0);
  }
 
  /**
  * Callback-Methode: wird vom Server aufgerufen, wenn jemand eine Nachricht
  * verschickt
  */
  public void receiveChat(String userID, String message)
  throws RemoteException {
    String value = userID + " >>> " + message + "\n";
    textArea.append(value);
	
  }
 
  /**
  * Callback-Methode: wird vom Server aufgerufen, wenn es einen neuen
  * Benutzer gibt
  */
  public void receiveUserLogin(String userID, Object[] users)
  throws RemoteException {
	  userList.setText("");
	  for(int i=0; i<users.length; ++i)  {
		  String user = (String)users[i] + "\n";
		  userList.append(user);
	  }
	  String value = userID + " logged in\n";
	  textArea.append(value);
	  
  }
 
  /**
  * Callback-Methode: wird vom Server aufgerufen, wenn ein Benutzer das
  * System verlaesst
  */
  public void receiveUserLogout(String userID, Object[] users)
  throws RemoteException {
	  String value = userID + " logged off\n";
	  textArea.append(value);
	  
	  userList.setText("");
	  for(int i=0; i<users.length; ++i)  {
		  if(users[i] == userID) {
			  continue;
			  
		  }
		  String user = (String)users[i] + "\n";
		  userList.append(user);
	  }
  }
 
  public static void main(String[] args) throws Throwable {
    ChatClient chat = new ChatClient("localhost");
    chat.setVisible(true);
  }
 
  /**
  * Wenn wir das Fenster schliessen, dann erfolgt ein Logout
  */
  class MyWindowListener extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
      try {
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      System.exit(0);
    }
  }
 
  class ViewMenuItemListener implements ActionListener {
    String className;
 
    public ViewMenuItemListener(String className) {
      this.className = className;
    }
 
    public void actionPerformed(ActionEvent e) {
      try {
        UIManager.setLookAndFeel(className);
      } catch (Exception ex) {
        System.out.println(ex);
      }
      SwingUtilities.updateComponentTreeUI(ChatClient.this);
    }
  }
 
  class ExitMenuItemListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      try {
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      System.exit(0);
    }
  }
 
  class LoginMenuItemListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String name = JOptionPane.showInputDialog(ChatClient.this,
      "Please enter a nickname.", "LOGIN",
      JOptionPane.PLAIN_MESSAGE);
      try {
        // fuehre Login durch
        if (serverObject.login(name, ChatClient.this)) {
          // falls erfolgreich
          userName = name;
          textArea.setText("");
          loginMenuItem.setEnabled(false);
          logoutMenuItem.setEnabled(true);
        } else {
          textArea.setText("This name is already in use." + "\n"
          + "Please choose another name.");
          loginMenuItem.setEnabled(true);
        }
      } catch (Exception ex) {
    	  ex.printStackTrace();
        System.err.println("Exception " + ex);
      }
      inputField.setText("");
    }
  }
 
  class LogoutMenuItemListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      try {
        // Logout
        if (serverObject.logout(userName)) {
          textArea.append(userName + " logged out" + "\n");
          userName = "";
          userList.setText("");
          loginMenuItem.setEnabled(true);
          logoutMenuItem.setEnabled(false);
        } else {
          textArea.append("Logout-Error" + "\n");
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }
 
  class ClearMenuItemListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      textArea.setText("");
    }
  }
 
  class InputFieldListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      String message = inputField.getText();
      if (message.length() > 0) {
        try {
          serverObject.chat(userName, message);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
        inputField.setText("");
      }
    }
  }
}
