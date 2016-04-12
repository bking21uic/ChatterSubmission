

import java.net.*;
import java.io.*; 

public class ChatterServer extends Thread { 
	private static final char DELIM = (char)3;

	//Static ArrayList of all connections
	private static int connectionsNext = 0;
	private static ChatterServer[] connections = new ChatterServer[1];
	
	//Each connection
	private String name = null;
	private Socket clientSocket = null;
	private PrintWriter out = null; 
	private BufferedReader in = null; 
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) { 
		int port = 10008;
		
		ServerSocket serverSocket = null; 
		try { 
			serverSocket = new ServerSocket(port); 
			try { 
				while (true) {
					Socket newConnection = serverSocket.accept();
					new ChatterServer(newConnection);
				}
			} catch (IOException e) { 
				System.err.println("Accept failed."); 
				System.exit(1); 
			} 
		} catch (IOException e) { 
			System.err.println("Could not listen on port: 10008."); 
			System.exit(1); 
		} finally {
			try {
				serverSocket.close(); 
			} catch (IOException e) { 
				System.err.println("Could not close port: 10008."); 
				System.exit(1); 
			} 
		}
	}


	
	
	
	
	
	
	
	
	
	
	
	public static void sendMsg(ChatterServer sender, String msg) {
		cleanConnctions();
		for (int i=0; i<connectionsNext; i++)  {
			if (connections[i]==null || connections[i].name==null) ; else
			if ( sender==null || (myMessage( msg, connections[i].name) && !(connections[i]==sender)) ) {
				connections[i].out.println( msg );
				System.out.println("TO " + connections[i].name + "\t" + msg.replace(DELIM, '#'));
			}
		}
	}
	public static boolean myMessage( String msg, String name ) {
		return msg.indexOf(""+DELIM + name + DELIM)!=-1 || msg.indexOf(DELIM + "*" + DELIM)!=-1;
	}
	public static void cleanConnctions() {
		for (int i=0; i<connectionsNext; i++)  {
			if (!connections[i].clientSocket.isConnected()  ) {
				System.out.println("*** Reaping Old " + connections[i].name);
				connections[i] = null;
			}
		}
	}
	
	
	
	
	
	
	
	public void addConnection(ChatterServer cc) {
		//If we have the name, repalce if
		for (int i=0; i<connectionsNext; i++)
			if (connections[i].name.equals(cc.name))
				connections[i] = null;
		
		//Resize the array if we have to
		if (connections.length == connectionsNext ) {
			ChatterServer[] tmp = new ChatterServer[2*connections.length];
			for (int i=0; i<connections.length; i++) tmp[i] = connections[i];
			connections = tmp;
		}
		//Add this to connections
		connections[connectionsNext++] = cc;
		reportUsers();
	}
	public void removeConnection(ChatterServer cc) {
		for (int i=0; i<connectionsNext; i++) {
			if ( connections[i]==cc) {
				connections[i].closeMe();
				connections[i] = null;
			}
		}
		reportUsers();
	}
	public void reportUsers() {
		//Computer the new list of users
		String users = "" + DELIM;
		for (int i=0; i<connectionsNext; i++) 
			if (connections[i]!=null && connections[i].name!=null)
				users = users + connections[i].name + DELIM;
		
		if (users.length()>1)
			sendMsg ( null, "users" + users );
	}
	
	

	
	
	
	
	
	
	
	
	
	
	private ChatterServer (Socket clientSoc) {
		this.clientSocket = clientSoc;
		try {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader( new InputStreamReader( clientSocket.getInputStream()));
		} catch (IOException e1) {
			return;
		} 
		if (clientSocket==null || out ==null || in==null) return;
		addConnection(this);
		this.start();
	}

	
	
	
	
	
	
	
	public void closeMe() {
		out.close(); 
		try {
			in.close();
		} catch (IOException e) {	} 
		try {
			clientSocket.close();
		} catch (IOException e) {	} 
	}
	
	
	
	
	
	
	
	
	
	
	public void run() {
		try {
			String fromClient; 
			while ((fromClient = in.readLine()) != null)  { 
				if (fromClient.indexOf("nameme" + DELIM)==0) {
					String clientName = fromClient.substring(1+fromClient.lastIndexOf(DELIM));
					this.name = clientName;
				} else
					sendMsg( this, fromClient );
				
			} 
			closeMe();
		} catch (IOException e) { 
			System.err.println("Client " + this.name + " disconnected");
			closeMe();
		} 
	}
	
	
	
} 