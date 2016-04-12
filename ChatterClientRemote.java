

public class ChatterClientRemote {

	private String users = null;
	
	public static String[] token(String msg) {
		String[] tmp = msg.split(""+ChatterClient.DELIM);
		return tmp;
	}
	
	public void update(String msg) {
		this.users = users;
		
		System.out.println("CHATTING WITH US ARE ");
		String[] tmp = token(msg);
		for (int i=0; i<tmp.length; i++)
			System.out.println("\t" + tmp[i]);
		System.out.println("---------------------");
		
	}
	
	
	
}