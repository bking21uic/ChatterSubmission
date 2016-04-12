import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;

public class ChatWindow extends ChatterClientWindow implements ActionListener {

	private JFrame chat;
	private JTextField textField;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatWindow window = new ChatWindow(null, "");
					window.chat.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChatWindow(ChatterClient cc, String session) {
		super(cc, session);
		initialize();
		this.chat.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {		
		String newline = "\n";
		
		chat = new JFrame();
		chat.setResizable(false);
		chat.setTitle("Chat Room");
		chat.setBounds(100, 100, 438, 222);
		chat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chat.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 434, 139);
		chat.getContentPane().add(scrollPane);
		
		//input
		textField = new JTextField();
		textField.setBounds(0, 150, 325, 37);
		chat.getContentPane().add(textField);
		textField.setColumns(10);	
		
		//output
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		scrollPane.setViewportView(textArea);
		
		//putting the input to the output
		String text = textField.getText();
		textArea.append(text + newline);
		textField.selectAll();
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.setBounds(335, 150, 89, 37);
		chat.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(this);
			
		
	}
	
	
	
	
	
	public void update(String msg) {
		this.textArea.setText( this.textArea.getText() + "\n" + extractMsg(msg)    );
	}

	public void actionPerformed(ActionEvent e) {
		String s = this.textField.getText();
		this.textArea.setText( this.textArea.getText() + "\nYOU:  " + s   );
		this.parent.sendChat(session, s);
		this.textField.setText("");
	}
}