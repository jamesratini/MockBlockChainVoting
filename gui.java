import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class gui extends JFrame {

	JTextField publicKeyBox;
	boolean buttonPressed = false;
	public gui()
	{
		super("BlockChain Voting");
		this.setLocation(600,300 );
		this.setSize(360, 300);
	    this.setLayout(new BorderLayout());
	    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    this.setResizable(false);
	    createGui();
	}
	private void createGui()
	{
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		JLabel publicKeyBoxLabel = new JLabel("Enter public key to send message to");
		mainPanel.add(publicKeyBoxLabel, BorderLayout.CENTER);
		publicKeyBox = new JTextField();
		mainPanel.add(publicKeyBox, BorderLayout.CENTER );
		
		//adding button
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				publicKeyBox.setText("button Pressed");
				buttonPressed = true;
			}
			
		});
		this.add(mainPanel);
		this.setVisible(true);
	}
	public String getPublicKeyText()
	{
		return publicKeyBox.getText();
	}
	public boolean isButtonPressed()
	{
		return buttonPressed;
	}
}
