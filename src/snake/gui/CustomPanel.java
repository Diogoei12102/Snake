package snake.gui;

import java.awt.Graphics;
import java.awt.Image;


import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CustomPanel extends JPanel {
	String imgName;
	
	/**
	 * Create the panel.
	 */
	public CustomPanel(String imgpath) {
		this.imgName = imgpath;
		

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		ImageIcon button = null;
		Image img;
		button = new ImageIcon(this.getClass().getResource(imgName));
		img = button.getImage();
		g.drawImage(img, 0 , 0 , getWidth() , getHeight() , null);
	}

	public void setImage(String string) {
		imgName = string;
		
	}
	
	
}
