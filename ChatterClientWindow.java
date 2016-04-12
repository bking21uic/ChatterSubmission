
public class ChatterClientWindow {

	protected ChatterClient parent;
	protected String session;
	
	public ChatterClientWindow(ChatterClient cc, String session) {

		this.parent = cc;
		System.out.println("Created Session " + session.replace(ChatterClient.DELIM, '#'));
		this.session = session;
	}
	
	public String getSession() {
		return this.session;
	}
	
	public String extractMsg(String msg) {
		int i = msg.lastIndexOf(ChatterClient.DELIM);
		if (i==-1) return "";
		return msg.substring(i+1);
	}
	
	public void update(String msg) {
		System.out.println(extractMsg(msg));
	}
}
