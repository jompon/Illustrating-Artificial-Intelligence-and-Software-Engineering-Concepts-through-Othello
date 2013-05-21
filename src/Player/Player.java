package Player;

import javax.swing.JOptionPane;

import Model.Othello;
import Model.OthelloButton;
import Support.Export;
import View.ViewAI;

public abstract class Player implements IPlayer{

	protected int x = 0;
	protected int y = 0;
	protected int lastX = 0;
	protected int lastY = 0;
	private int DirectX = 0;
	private int DirectY = 0;
	protected int numEat = 0;
	protected int noPlayer;
	protected int ID;
	protected Othello othello;
	protected OthelloButton[][] button;
	protected static int numPlayer = 0;
	protected static ViewAI view;
	protected Export export;
	public Player(OthelloButton[][] button, Othello othello)
	{
		this.button = button;
		this.othello = othello;
		export = Export.getInstance(othello);
	}
	
	protected void setView(Computer computer)
	{
		if( view == null )
		{
			view = new ViewAI(computer, button, othello);
		}
	}
	
	public void nextTurn( )
	{
		othello.nextTurn();
		for(int i=0;i<button.length;i++){
			for(int j=0;j<button[i].length;j++){
				if( button[i][j].isMarkPlace() ){
					othello.setTable(i, j, (char)0);
					button[i][j].setMarkPlace(false);
					button[i][j].repaint();
				}
				Mark(i,j,"Put");
			}
		}
		if( othello.getNumBlackPiece() + othello.getNumWhitePiece() == Math.pow(button.length, 2) )
			othello.GameOver();
		else if( !othello.isEat() )											passTurn();
	}
	
	private void passTurn()
	{
		JOptionPane.showMessageDialog(null, othello.getColorPiece()+" cannot eat this turn!!");
		nextTurn();
		if( othello.getIdPlayer() == 0 )									execute( );
	}
	
	private void Eat( int x , int y , int a , int b )
	{
		int i = 0,j = 0;
		for(int p=0;p<numEat;p++){
			i += a;
			j += b;
			
			try {
				button[x+i][y+j].setPiece(othello.getPiece());
				othello.setTable(x+i, y+j, othello.getPiece());
				button[x+i][y+j].setMarkFlip(false);
				button[x+i][y+j].repaint();
				othello.addPiece(othello.getPiece());
				othello.removePiece(othello.getOpponentPiece());
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void setTake( int x , int y , int a , int b )
	{
		int i = 0,j = 0;
		for(int p=0;p<numEat;p++){
			i += a;
			j += b;
			if( button[x+i][y+j].isMarkFlip() )		button[x+i][y+j].setMarkFlip(false);
			else									button[x+i][y+j].setMarkFlip(true);
			button[x+i][y+j].repaint();
		}
	}
	private void setMark( int x, int y )
	{
		if( !othello.isEat() )						othello.setEat(true);// Show that this turn can eat
		button[x][y].setMarkPlace(true);
		button[x][y].repaint();
		othello.setTable(x, y, 'M');
	}
	private boolean isTakePiece( int x , int y , int a , int b )
	{
		int i = a*(numEat+1),j = b*(numEat+1);
		if( x+i >=0 && x+i < button.length && y+j >= 0 && y+j < button[x+i].length )
		{
			if( button[x+i][y+j].getPiece() == othello.getPiece() )			return true;
		}
		return false;
	}
	private int numTakePiece( int x , int y , int a , int b )
	{
		if( x+a >= 0 && x+a < button.length && y+b >= 0 && y+b < button[x+a].length )
		{
			if( button[x+a][y+b].getPiece() == othello.getOpponentPiece() )		return numTakePiece( x , y , DirectX+a , DirectY+b )+1;
		}
		return 0;
	}
	private void checkAroundPiece( int x , int y , String command )
	{
		for(int i=-1;i<=1;i++){
			for(int j=-1;j<=1;j++){
				if( x+i >= 0 && x+i < button.length && !( i == 0 && j == 0 ) &&
				    y+j >= 0 && y+j < button[0].length )
				{
					if( button[x+i][y+j].getPiece() == othello.getOpponentPiece() ){
						DirectX = i;
						DirectY = j;
						numEat = numTakePiece( x , y , i , j );
						if( isTakePiece( x , y , i , j ) )
						{
							if( command.equalsIgnoreCase("Eat") )			Eat( x , y , i, j );
							else if( command.equalsIgnoreCase("Take") )		setTake(x, y, i, j);
							else if( command.equalsIgnoreCase("Put") )		setMark(x, y);
						}
					}
				}
			}
		}
	}
	public void Place( final String command )
	{
		checkAroundPiece( x , y , command );
	}
	public void Mark(int X, int Y, String command) 
	{
		if( !button[X][Y].isPlaced() )				checkAroundPiece( X , Y , command );
	}
	
	public void setPoint(int x,int y)
	{
		this.x = x;
		this.y = y;
	}
	
	protected void viewUpdate( )
	{
		if( view == null )		return;
		view.update();
	}
	
	public int getID()
	{
		return ID;
	}
	public String getNoPlayer()
	{
		return noPlayer + "P Player";
	}
}
