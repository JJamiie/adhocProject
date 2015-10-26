import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class MainActivity extends JFrame implements KeyListener {
	private String key;
	private JTextField ssidField;
	private JPasswordField keyField;
	private JTextField usernameField;
	ImagePanel join;
	ImagePanel runframe;
	MainActivity th = this;

	public static void main(String[] arg) {
		new MainActivity();
	}

	public MainActivity() {

		this.setSize(400, 400);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2 - 50);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(this);
		exitListener();
		frameJoinChannel();
		this.setResizable(false);
		this.setVisible(true);
	}

	public void broadcast(AudioChunk audio) throws InterruptedException,
			IOException {
		ServerSocketChannel ssChannel = ServerSocketChannel.open();
		ssChannel.configureBlocking(true);
		int port = 8000;
		ssChannel.socket().bind(new InetSocketAddress(port));

		while (true) {
			SocketChannel sChannel = ssChannel.accept();

			ObjectOutputStream oos = new ObjectOutputStream(sChannel.socket()
					.getOutputStream());
			oos.writeObject(audio);
			oos.close();
			System.out.println("Connection ended");
		}
	}

	
	public void frameJoinChannel() {
		join = new ImagePanel("picture/login.png");
		join.setSize(400, 400);
		join.setLayout(null);

		ssidField = new JTextField();
		ssidField.setBounds(120, 170, 200, 30);
		setStandartTextField(ssidField, "SSID");

		usernameField = new JTextField();
		usernameField.setBounds(120, 215, 200, 30);
		setStandartTextField(usernameField, "Username");

		keyField = new JPasswordField();
		keyField.setBounds(120, 260, 200, 30);
		setStandartTextField(keyField, "Password");

		PictureButton joinButton = new PictureButton("picture/joinButton.png",
				"picture/joinButtonClick.png");
		joinButton.setBounds(64, 316, 261, 35);
		joinButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (ssidField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Please fill in your SSID", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (usernameField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Please fill in your username.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else if (keyField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Please fill in your password.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					// configAdhoc();
					frameRun();
				}
			}
		});

		join.add(usernameField);
		join.add(ssidField);
		join.add(keyField);
		join.add(joinButton);
		this.add(join);
		this.revalidate();
		this.repaint();
	}

	public void frameRun() {
		System.out.println("----Run-----");
		runframe = new ImagePanel("picture/runframe.png");
		runframe.setSize(400, 400);
		runframe.setLayout(null);
		PictureButton back = new PictureButton("picture/back.png",
				"picture/backClick.png");
		back.setBounds(370, 10, 21, 20);
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				th.remove(runframe);
				frameJoinChannel();
			}
		});
		runframe.add(back);
		this.remove(join);
		this.add(runframe);
		this.revalidate();
		this.repaint();
	}

	public void setStandartTextField(JTextField textField, String text) {
		textField.setBackground(new Color(58, 65, 73));
		textField.setForeground(Color.white);
		textField.setBorder(null);
		textField.setCaretColor(Color.white);
		TextPrompt tp = new TextPrompt(text, textField);
		tp.setForeground(new Color(106, 116, 127));
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
					"echo " + key + "| sudo -S iwconfig wlan0 channel 1" };
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
			String[] command8 = {
					"/bin/bash",
					"-c",
					"echo " + key + "| sudo -S ifconfig wlan0 192.168.1." + ip
							+ " 255.255.255.0" };
			process = Runtime.getRuntime().exec(command8);
			returnCode = process.waitFor();
			System.out.println(command8);
			System.out.println("IP 192.168.1." + ip + ":= " + returnCode);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void exitListener() {
		WindowListener exitListener = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int confirm = JOptionPane.showOptionDialog(null,
						"Are You Sure to Close Application?",
						"Exit Confirmation", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (confirm == 0) {
					System.exit(0);
				}
			}
		};
		this.addWindowListener(exitListener);
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
