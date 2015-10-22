import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;



public class MainActivity extends JFrame {
	public static void main(String[] arg){
		MainActivity mainActivity = new MainActivity();
		mainActivity.setVisible(true);
	}
	public MainActivity(){
		super("Adhoc");
		setSize(130,80);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JTextField ssidField = new JTextField("SSID");
		JLabel ssid = new JLabel();
		ssid.setText("SSID");
		
		JPasswordField keyField = new JPasswordField("Key");
		
		JButton joinButton = new JButton("Join");
		setLayout(new BorderLayout());
		add(ssid,BorderLayout.NORTH);
		add(ssidField, BorderLayout.NORTH);
		add(keyField, BorderLayout.CENTER);
		add(joinButton, BorderLayout.SOUTH);
		
	}
	
	
	
	
}
