package Model;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import Support.Format;

/**
 * OthelloNode is model of node that show AI process from the system with value
 */
public class OthelloNode extends JComponent{

  private Format format;
	private int value;
	private int size;
	public OthelloNode(int size, int value)
	{
		this.size = size;
		this.value = value;
		this.setFont(new Font("sansserif", Font.BOLD, 12));
		format = new Format();
	}
	
	public void setValue(int value)
	{
		this.value = value;
	}
	public int getValue( )
	{
		return value;
	}

	public void paintNode(Graphics g)
	{
		ImageIcon img = new ImageIcon( format.getIcon("circle_grey.png") );
		Image img2 = img.getImage();
		g.drawImage(img2, 0, 0, size, size, this);
		g.drawString(value+"", (value<0)?4*size/10:9*size/20, 3*size/5);
	}
	public void paintComponent(Graphics g)
	{
	    super.paintComponent(g);
	    
	    paintNode(g);
	}
}
