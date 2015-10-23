import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{
	String path;
	public ImagePanel(String path){
		this.path = path;
	}
	
	public void paintComponent(Graphics g){
		this.setSize(400,400);
		super.paintComponents(g);
		Graphics2D g2 = (Graphics2D) g;
		Dimension dim = getSize();
		g2.clearRect(0, 0, (int)dim.getWidth(), (int)dim.getHeight());

		try {
			BufferedImage head = ImageIO.read(ImagePanel.class.getClassLoader().getResourceAsStream(path));
			g2.drawImage(head,0,0,null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}