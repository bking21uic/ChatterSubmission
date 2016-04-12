import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;

public class LoginWindow extends ChatterClientLogin implements ActionListener {

	
	//private boolean ready = false;
	//private String ip;
	//private String port;
	//private String name;
	
	
	private JFrame Login;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	
	private JButton btnNewButton;

	
	/**
	 * Create the application.
	 */
	public LoginWindow(boolean isGui) {
		super(isGui);
		if (!isGui) return;
		initialize();
		this.Login.setVisible(true);
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Login = new JFrame();
		Login.setTitle("Login");
		Login.setBounds(100, 100, 283, 180);
		Login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Login.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Server IP");
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblNewLabel.setBounds(10, 0, 91, 32);
		Login.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Port");
		lblNewLabel_1.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblNewLabel_1.setBounds(10, 35, 83, 32);
		Login.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Username");
		lblNewLabel_2.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblNewLabel_2.setBounds(10, 75, 105, 24);
		Login.getContentPane().add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setBounds(96, 7, 163, 20);
		Login.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(96, 43, 163, 20);
		Login.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(96, 78, 163, 20);
		Login.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		btnNewButton = new JButton("Connect");
		btnNewButton.setBounds(35, 110, 190, 23);
		//btnNewButton.setBounds(35, 110, 89, 23);
		Login.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		this.ip = this.textField.getText();
		if (this.ip.length()==0) this.ip = "127.0.0.1";
		this.port = this.textField_1.getText();
		if (this.port.length()==0) this.port = "10008";
		this.name = this.textField_2.getText();
		this.ready=true;
	}
	
	public void terminate() {
		this.Login.setVisible(false);
		
	}
}