import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PictureButton extends JButton{
	BufferedImage buttonIcon;
	BufferedImage buttonChange;
	BufferedImage present;
	PictureButton th = this;
	public PictureButton(String path){
			
		try{
			present = ImageIO.read(PictureButton.class.getClassLoader().getResourceAsStream(path));
				
		}catch(IOException io){
			io.printStackTrace();
		}
		Dimension size = new Dimension(present.getWidth(),present.getHeight());
		setSize(size);
		setPreferredSize(size);
		setBorder(null);
		setOpaque(false);
		setContentAreaFilled(false);
	}
	
	public PictureButton(String path,String pathClick) {
		this(path);
		try{
			buttonChange = ImageIO.read(PictureButton.class.getClassLoader().getResourceAsStream(pathClick));
			buttonIcon = ImageIO.read(PictureButton.class.getClassLoader().getResourceAsStream(path));
				
		}catch(IOException io){
			
		}
		
		this.getModel().addChangeListener(new ChangeListener() {
						
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				ButtonModel model = (ButtonModel) e.getSource(); 
				
				if(model.isPressed()){ // model.isRollover()
					if(present == th.buttonChange) present = th.buttonIcon;
					else present = th.buttonChange;
					repaint();
				}
			}
		});
		
		Dimension size = new Dimension(present.getWidth(),present.getHeight());
		setSize(size);
		setPreferredSize(size);
		setBorder(null);
		setOpaque(false);
		setContentAreaFilled(false);
	}

	
	public void paintComponent(Graphics g){
		super.paintComponents(g);
		g.drawImage(present,0,0,null);
	}
	
}