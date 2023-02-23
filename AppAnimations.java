import javax.swing.JFrame;
import java.awt.Color;

import java.awt.Graphics;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Image;

public class AppAnimations
{
	private int xpos;
	private int ypos;
	private Image image;
	
	public AppAnimations(String picFile, int xpos, int ypos)
	{
		this.xpos = xpos;
		this.ypos = ypos;
		try {
			this.image = ImageIO.read(new File(picFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.playerScreen();
	}
	
	private static void playerScreen() 
	{
		JFrame playerScreen = new JFrame("Spongebob Trivia");
		playerScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		playerScreen.setSize(600, 1000);
		playerScreen.setVisible(true);
	}
	
	public void draw(Graphics g)
	{
		g.drawImage(image, xpos, ypos, null);
	}
}
