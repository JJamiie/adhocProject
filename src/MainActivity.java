import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class MainActivity extends JFrame implements KeyListener {
	private JLayeredPane layeredPane;
	private String key;
	private JTextField ssidField;
	private JPasswordField keyField;
	private JTextField usernameField;
	
	public static void main(String[] arg) {
		new MainActivity();
	}

	public MainActivity() {
		this.setSize(400, 350);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		layeredPane = new JLayeredPane();
		addKeyListener(this);

		WindowListener exitListener = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int confirm = JOptionPane.showOptionDialog(null,
						"Are You Sure to Close Application?",
						"Exit Confirmation", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (confirm == 0) {
					String[] command1 = {"/bin/bash","-c",
							"echo " + key + "| sudo -S service network-manager start" };
					try {
						Process process = Runtime.getRuntime().exec(command1);
						int returnCode = process.waitFor();
						System.out.println(command1);
						System.out.println("Service start:= " + returnCode);
					} catch (IOException e1) {
						e1.printStackTrace();
					}catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					System.exit(0);
				}
			}
		};
		this.addWindowListener(exitListener);
		frameJoinChannel();
		this.add(layeredPane);
		this.setVisible(true);

	}

	public void frameJoinChannel() {
		layeredPane.removeAll();
		JPanel join = new JPanel();
		join.setSize(400, 350);

		JLabel username = new JLabel();
		username.setText("Name");
		username.setBounds(70, 70, 100, 30);
		usernameField = new JTextField();
		usernameField.setBounds(120, 70, 200, 30);

		JLabel ssid = new JLabel();
		ssid.setText("SSID");
		ssid.setBounds(70, 130, 100, 30);
		ssidField = new JTextField("jamie");
		ssidField.setBounds(120, 130, 200, 30);

		JLabel key = new JLabel();
		key.setText("Key");
		key.setBounds(70, 190, 100, 30);
		keyField = new JPasswordField("jamie16130");
		keyField.setBounds(120, 190, 200, 30);

		JButton joinButton = new JButton("Join");
		joinButton.setText("Join");
		joinButton.setBounds(150, 250, 100, 30);
		joinButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (ssidField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Please fill in your SSID", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (keyField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Please fill in your key.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					configAdhoc();
				}
			}
		});

		layeredPane.add(username);
		layeredPane.add(usernameField);
		layeredPane.add(ssidField);
		layeredPane.add(ssid);
		layeredPane.add(key);
		layeredPane.add(keyField);
		layeredPane.add(joinButton);
	}

	public void configAdhoc() {

		Process process;
		try {
			key = keyField.getText();
			String[] command1 = { "/bin/bash", "-c",
					"echo " + key + "| sudo -S service network-manager stop" };
			process = Runtime.getRuntime().exec(command1);
			int returnCode = process.waitFor();
			System.out.println(command1);
			System.out.println("Service stop:= " + returnCode);

			String[] command2 = { "/bin/bash", "-c",
					"echo " + key + "| sudo -S ip link set wlan0 down" };
			process = Runtime.getRuntime().exec(command2);
			returnCode = process.waitFor();
			System.out.println(command2);
			System.out.println("Down:= " + returnCode);

			String[] command3 = { "/bin/bash", "-c",
					"echo " + key + "| sudo -S iwconfig wlan0 mode ad-hoc" };
			process = Runtime.getRuntime().exec(command3);
			returnCode = process.waitFor();
			System.out.println(command3);
			System.out.println("Mode:= " + returnCode);

			String[] command4 = { "/bin/bash", "-c",
					"echo " + key + "| sudo -S iwconfig wlan0 channel 4" };
			process = Runtime.getRuntime().exec(command4);
			returnCode = process.waitFor();
			System.out.println(command4);
			System.out.println("Channel:= " + returnCode);

			String[] command5 = {
					"/bin/bash",
					"-c",
					"echo " + key + "| sudo -S iwconfig wlan0 essid \""
							+ ssidField.getText() + "\"" };
			process = Runtime.getRuntime().exec(command5);
			returnCode = process.waitFor();
			System.out.println(command5);
			System.out.println("SSID:= " + returnCode);

			String[] command6 = { "/bin/bash", "-c",
					"echo " + key + "| sudo -S iwconfig wlan0 key 1234567890" };
			process = Runtime.getRuntime().exec(command6);
			returnCode = process.waitFor();
			System.out.println(command6);
			System.out.println("Key:= " + returnCode);

			String[] command7 = { "/bin/bash", "-c",
					"echo " + key + "| sudo -S ip link set wlan0 up" };
			process = Runtime.getRuntime().exec(command7);
			returnCode = process.waitFor();
			System.out.println(command7);
			System.out.println("Down:= " + returnCode);

			int ip = (int) Math.floor((Math.random() * 255) + 1);
			String[] command8 = { "/bin/bash", "-c",
					"echo " + key + "| sudo -S ifconfig wlan0 192.168.1."+ip };
			process = Runtime.getRuntime().exec(command8);
			returnCode = process.waitFor();
			System.out.println(command8);
			System.out.println("IP 192.168."+ip+":= " + returnCode);

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
