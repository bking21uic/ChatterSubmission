

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChatterClientLogin {
	
	protected boolean ready = false;
	protected String ip;
	protected String port;
	protected String name;
	
	public ChatterClientLogin(boolean isGui) {
		if (isGui) return;
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("IP: ");
			this.ip = stdIn.readLine();
			if (ip.length()==0) this.ip = "127.0.0.1";
			System.out.print("Port: ");
			this.port = stdIn.readLine();
			if (port.length()==0) this.port = "10008";
			System.out.print("Name: ");
			this.name = stdIn.readLine();
			this.ready=true;
		} catch (IOException e) {}
		
	}
	
	public String getIP() {
		while (ready==false)
			try {Thread.sleep(100);} catch (InterruptedException e) {}
		return ip;
	}
	
	public String getPort() {
		while (ready==false)
			try {Thread.sleep(100);} catch (InterruptedException e) {}
		return port;
	}
	
	public String getName() {
		while (ready==false)
			try {Thread.sleep(100);} catch (InterruptedException e) {}
		return name;
	}

	public boolean quitNow() {
		while (ready==false)
			try {Thread.sleep(100);} catch (InterruptedException e) {}
		return false;
	}
	
	public void terminate() {
		return;
	}

	
}