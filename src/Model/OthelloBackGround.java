package Model;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import Support.Format;

/**
 * OthelloBackGround is paint background on cover page
 */
public class OthelloBackGround extends JComponent{

  private String imageName;
	private Format format;
	private int x;
	private int y;
	private int width;
	private int height;
	public OthelloBackGround(String imageName)
	{
		this.imageName = imageName;
		format = new Format();
	}
	public void setBound(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void paintImage( Graphics g )
	{
		ImageIcon img = new ImageIcon( format.getIcon(imageName) );
		Image img2 = img.getImage();
		if( width == 0 || height == 0 )		g.drawImage(img2, x, y, this);
		else								g.drawImage(img2, x, y, width, height, this);
	}
	public void paintComponent( Graphics g )
	{
	    super.paintComponent(g);
	    
	    paintImage(g);
	}
}
