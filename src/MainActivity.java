
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class MainActivity extends JFrame implements KeyListener{
	JLayeredPane layeredPane;
	public static void main(String[] arg){
		new MainActivity();	
	}
	
	public MainActivity(){
		this.setSize(400,350);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		layeredPane = new JLayeredPane();
		addKeyListener(this);
		
		frameJoinChannel();
		this.add(layeredPane);
		this.setVisible(true);
	}
	JTextField ssidField;
	public void frameJoinChannel(){
		layeredPane.removeAll();
		JPanel join = new JPanel();
		join.setSize(400,350);
		
		JLabel name = new JLabel();
		name.setText("Name");
		name.setBounds(70,70, 100, 30);
		JTextField nameField = new JTextField();
		nameField.setBounds(120,70, 200, 30);
		
		JLabel ssid = new JLabel();
		ssid.setText("SSID");
		ssid.setBounds(70,130, 100, 30);
		ssidField = new JTextField();
		ssidField.setBounds(120,130, 200, 30);
		
//		JLabel key = new JLabel();
//		key.setText("Key");
//		key.setBounds(70,190, 100, 30);
//		JPasswordField keyField = new JPasswordField();
//		keyField.setBounds(120,190, 200, 30);
		
		JButton joinButton =  new JButton("Join");
		joinButton.setText("Join");
		joinButton.setBounds(150,250,100, 30);
		joinButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				configAdhoc();
				
			}
		});
		
		layeredPane.add(name);
		layeredPane.add(nameField);
		layeredPane.add(ssidField);
		layeredPane.add(ssid);
		//layeredPane.add(key);
		//layeredPane.add(keyField);
		layeredPane.add(joinButton);
	}

	public void configAdhoc(){
		String command = "sudo ifconfig wlan0 down";
        Process process;
		try {
			process = Runtime.getRuntime().exec(command);
			int returnCode = process.waitFor();
			System.out.println(command);
	        System.out.println("Down:= " + returnCode);
	        
	        command = "iwconfig wlan0 mode ad-hoc"; 
	        process = Runtime.getRuntime().exec(command);
	        returnCode = process.waitFor();
	        System.out.println(command);
	        System.out.println("Mode:= " + returnCode);
	        
	        command = "iwconfig wlan0 essid \"" + ssidField.getText() + "\"";
	        process = Runtime.getRuntime().exec(command);
	        returnCode = process.waitFor();
	        System.out.println(command);
	        System.out.println("SSID:= " + returnCode);

	        command = "iwconfig wlan0 channel 4";
	        process = Runtime.getRuntime().exec(command);
	        returnCode = process.waitFor();
	        System.out.println(command);
	        System.out.println("Channel:= " + returnCode);

	        command = "ifconfig wlan0 192.168.1.1";
	        process = Runtime.getRuntime().exec(command);
	        returnCode = process.waitFor();
	        System.out.println(command);
	        System.out.println("IP:= " + returnCode);
	        
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
}
