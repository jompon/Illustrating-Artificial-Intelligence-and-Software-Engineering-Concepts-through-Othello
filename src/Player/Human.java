package Player;

import java.awt.Point;

import Model.Othello;
import Model.OthelloButton;

public class Human extends Player{

  public Human(OthelloButton[][] button,Othello othello) 
	{
		super(button,othello);
		this.noPlayer = ++numPlayer;
		this.ID = 1;
	}
	
	public void execute( )
	{
		if( !othello.isTiming() && button[x][y].isMarkPlace() ){
			System.out.println("EXECUTE: X: "+x+" Y: "+y);
			othello.setTiming(true);
			button[x][y].setPiece(othello.getPiece());
			button[x][y].setMarkPlace(false);
			button[x][y].setLastPlace(true);
			button[x][y].repaint();
			othello.setTable(x, y, othello.getPiece());
			othello.addPiece(othello.getPiece());
			viewUpdate();
			try {
				Thread.sleep(1000);
				super.Place("Eat");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			button[lastX][lastY].setLastPlace(false);
			button[lastX][lastY].repaint();
			lastX = x;
			lastY = y;
			export.savePoint("Point["+x+":"+y+"] = "+numEat);
			othello.setEat(false);
			othello.setTiming(false);
			nextTurn();
		}
	}
	
	public void setPoint(int x,int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public String toString()
	{
		return "Human Player";
	}

}
