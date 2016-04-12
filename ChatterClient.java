

import java.io.*;
import java.net.*;




public class ChatterClient extends Thread {
	
	public static final char DELIM = (char)3;

	//Each connection has all these strings
	//From the login window
	private String ip = "127.0.0.1";
	private int port = 10008;
	private String name = "CHATTER";
	
	//These are the network connection components
	private Socket echoSocket = null;
	private PrintWriter out = null;
	private BufferedReader in = null; 
	
	//These are the UI components
	private ChatterClientRemote remote = null;
	private int windowNext = 0;
	private ChatterClientWindow[] window = new ChatterClientWindow[1];
	
	private boolean isGui = false;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		System.out.println(args.length);
		for (int i=0; i<args.length; i++)
			System.out.println(i + "\t" + args[i]);
		
		
		if (args.length>0 ) {
			System.out.println("Creating in CMD Mode");
			new ChatterClient(false);
		} else {
			System.out.println("Creating in GUI Mode");
			new ChatterClient(true);
		}
	}
	
	
	
	
	
	
	
	
	public ChatterClient(boolean isGui) {
		this.isGui = isGui;
		if (!this.isGui) System.out.println("CMD LINE TIME!");
		//Each chatterClient logon is a new Thread (like fork)
		ChatterClientLogin  logon = null;
		//Keep asking the user to logon:
		while (logon==null) {
			//We create a ChatterClientLogin to
			//try to connect to the server
			if (this.isGui)
				logon = new LoginWindow(true);
			else
				logon = new ChatterClientLogin(false);
			///If the user clicks Quit, we leave
			if (logon.quitNow()==true) return;
			//Get this ChatterClientLogin's data
			this.ip = logon.getIP();
			this.port = getInt(logon.getPort());
			this.name = logon.getName();
			logon.terminate();
			//Try opening the socket, in and out
			try {
				//We need these three objects
				echoSocket = null;
				out = null;
				in = null; 
				//Try to get them
	    		echoSocket = new Socket(this.ip, this.port);
	    		out = new PrintWriter(echoSocket.getOutputStream(), true);
	    		in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
	    		if (echoSocket==null || in==null || out==null) 
	    			logon=null;
		    	else {
		    		//Request a name from the server
		    		out.println("nameme" + DELIM + name);
		    		//Create a remote
		    		this.remote = new ChatterClientRemote();
		    		//Create the first logon
		    		deliver(ChatterClient.DELIM + "*" + ChatterClient.DELIM);
		    		//Start the server
		    		this.start();
		    		
		    		
		    		//
		    		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		        	String userInput;
		    		while ((userInput = stdIn.readLine()) != null) {
		        		sendChat("*", userInput);
		        	}
		    	}
	    		
	    		
	    		
	    	} catch (Exception e) {
	    		System.err.println("Failed to connect to " + this.ip + ":" + this.port);
	    		logon=null;
	    	}
	    	
	    		
	    		
	    	
	    	
		}
    	
	}
	private static int getInt(String port) {
		int i;
		try {
			i = Integer.parseInt(port);
		} catch (Exception e) {return -1;}
		return i;
	}
	
	
	protected void sendChat(String session, String msg) {
		out.println("chat" + DELIM + session + DELIM + this.name + ": " + msg);
	}
	
	
	
	
	
	
	//BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
	//while (true) {
	//	String i = stdIn.readLine();
	//	if (i==null) return;
	//	out.println("chat" + DELIM + "*" + DELIM + this.name + ": " + i);
	//	try {Thread.sleep(100);} catch (InterruptedException e1) {}
	//}
	
	
	
	
	
	
	
	
	
	
	public void closeMe() {
		try {
    		out.close();
			in.close();
			echoSocket.close();
		} catch (IOException e) {
		}
	}
	
	
	
	private void deliver(String msg) {
		int i = msg.indexOf(DELIM);
		int j = msg.lastIndexOf(DELIM);
		String session = msg.substring(i+1, j);
		for (i=0; i<windowNext; i++)
			if (window[i].getSession().equals(session)) {
				window[i].update(msg);
				return;
			}
		if (window.length == windowNext) {
			ChatterClientWindow[] temp = new ChatterClientWindow[2*window.length];
			for (i=0; i<window.length; i++) temp[i] = window[i];
			window = temp;
		}
		ChatterClientWindow nc;
		if (this.isGui)
			nc = new ChatWindow(this, session);
		else
			nc = new ChatterClientWindow(this, session);
		 
		 nc.update(msg);
		 window[windowNext++] = nc;
	}
	
	
	
	
	
	public void run() {
    	while (true) {
			String msg = null;
			try {
				msg = in.readLine();
			} catch (IOException e) {}
			if (msg==null || msg.indexOf( DELIM )==-1) {
				System.err.println("The server disconnected");
				System.exit(1);
			} else {
				int i =msg.indexOf(DELIM);
				int j = msg.lastIndexOf(DELIM);
				String cmd = msg.substring(0, i);
				String session = msg.substring(i+1, j);
				if (cmd.equals("chat"))
					deliver(msg);
				if (cmd.equals("users")) {
					window[0].update("Chatting are: " + session.replace(DELIM, ','));
					if (this.remote!=null) this.remote.update(msg);
				}
			}
    	}
    }
	
	
	
}