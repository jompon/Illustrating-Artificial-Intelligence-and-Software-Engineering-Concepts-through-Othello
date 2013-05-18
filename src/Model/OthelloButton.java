package Model;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import Support.Format;

/**
 * OthelloButton is model of piece that paint on UI
 */
public class OthelloButton extends JComponent{

  private Mark mark;				// Mark from setting of user
	private Format format;			
	private boolean lastPlace;		// the last piece that put on the board
	private boolean markPlace;		// mark from the system that this position can place; indicate with yellow color
	private boolean markFlip;		// mark from the system that this position can be eaten; indicate with red color
	private boolean markView;		// mark from ViewAI for view; indicate with blue color
	private char piece;				// indicate of button; 'X' is black color, 'O' is white color
	private int size;				// size of button
	public OthelloButton(Mark mark, Format format, int size)
	{
		this.size = size;
		this.mark = mark;
		this.format = format;
		initComponents( );
	}
	public void initComponents( )
	{
		lastPlace = false;
		markPlace = false;
		markFlip = false;
		markView = false;
		piece = 0;
	}
	
	public int getSquareSize()
	{
		return size;
	}
	
	public boolean isPlaced( )
	{
		return piece == 'O' || piece == 'X';
	}
	
	public boolean isLastPlace( )
	{
		return lastPlace;
	}
	public void setLastPlace( boolean lastPlace )
	{
		this.lastPlace = lastPlace;
	}
	
	public boolean isMarkPlace( )
	{
		return markPlace;
	}
	public void setMarkPlace( boolean markPlace )
	{
		this.markPlace = markPlace;
	}
	
	public boolean isMarkFlip( )
	{
		return markFlip;
	}
	public void setMarkFlip( boolean markFlip )
	{
		this.markFlip = markFlip;
	}
	
	public boolean isMarkView( )
	{
		return markView;
	}
	public void setMarkView( boolean markView )
	{
		this.markView = markView;
	}
	
	public char getPiece( )
	{
		return piece;
	}
	public void setPiece(char piece)
	{
		this.piece = piece;
	}
	
	public void paintLastPlace(Graphics g, int x, int y, int size)
	{
	    g.setColor(Color.BLUE);
	    g.drawLine(size/4, size/2, size*3/4, size/2);
	    g.drawLine(size/2, size/4, size/2, size*3/4);
	}
	public void paintMark(Graphics g, int x, int y, int size,String typeMark)
	{
		if( typeMark.equals("View") )		g.setColor(Color.BLUE);
		if( typeMark.equals("Put") )		g.setColor(Color.YELLOW);
		if( typeMark.equals("Take") )		g.setColor(Color.RED);
	    //g.fillRect(x, y, size, size);
	    g.fill3DRect(x, y, size, size, true);
	    g.setColor(Color.BLACK);
	    g.drawRect(x, y, size, size);
	}
	public void paintOval(Graphics g, double x, double y, int ovalSize)
	{
		int boardX = (int)(ovalSize * (x + 0.05) + 0.5);
		int boardY = (int)(ovalSize * (y + 0.05) + 0.5);
		int size = (int) (ovalSize * 0.9 + 0.5);
	    
		ImageIcon img;
		Image img2;
		
		switch (piece)
	    {
	    	case 'O':
	    		/*g.setColor(Color.WHITE);
	    		g.fillOval(boardX, boardY, size, size);
	    		g.drawOval(boardX, boardY, size, size);*/
	    		img = new ImageIcon( format.getIcon("white_thumb.png") );
	    		img2 = img.getImage();
	    		g.drawImage(img2, boardX, boardY, size, size, this);
	    		break;

	    	case 'X':
	    		/*g.setColor(Color.BLACK);
	    		g.fillOval(boardX, boardY, size, size);
	    		g.drawOval(boardX, boardY, size, size);*/
	    		//g.setColor(Color.black);
	    		//g.fillOval(boardX - 1, boardY - 1, size + 3, size + 3);		//Edge
	    		img = new ImageIcon( format.getIcon("black_thumb.png") );
	    		img2 = img.getImage();
	    		g.drawImage(img2, boardX, boardY, size, size, this);
	    		break;
	    	default:	break;
	    }
	}
	public void paintSquare(Graphics g, int x, int y, int size)
	{
	    g.setColor(Color.GREEN);
	    g.fill3DRect(x, y, size, size, true);
	    g.setColor(Color.BLACK);
	    g.draw3DRect(x, y, size, size, true);
	}
	public void paintComponent(Graphics g)
	{
	    super.paintComponent(g);
	    
	    Rectangle clipRect = new Rectangle();
	    g.getClipBounds(clipRect);
	    if( markView )									paintMark(g, 0, 0, size, "View");
	    else if( mark.isMarkPlace() && markPlace )			paintMark(g, 0, 0, size, "Put");
	    else if( mark.isMarkFlip() && markFlip )		paintMark(g, 0, 0, size, "Take");
	    else											paintSquare(g, 0, 0, size);
	    paintOval(g, 0, 0, size);
	    if( lastPlace )									paintLastPlace(g, 0, 0, size);
	}
}
