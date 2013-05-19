package Player;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import AI.MiniMax;
import Model.Best;
import Model.Othello;
import Model.OthelloButton;

public class Computer extends Player {

	private MiniMax computer;
	private Point p;
	public Computer(OthelloButton[][] button, Othello othello, int level) 
	{
		super(button, othello);
		computer = new MiniMax(othello, level);
		resetView( );
		this.noPlayer = ++numPlayer;
		this.ID = 0;
	}
	
	public void execute( )
	{
		if( !othello.isTiming() && !othello.isGameOver() ){
			othello.setTiming(true);
			computer.resetListNode();
			computer.setTable(othello.getTable());
			computer.Think( );
			p = computer.Place();
			setPoint(p.x,p.y);
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
			List<Best> best = getBest();
			List<String> point = new ArrayList<String>();
			for(int i=0;i<best.size();i++)
			{
				point.add("Point["+best.get(i).point.x+":"+best.get(i).point.y+"] = "+best.get(i).score);
			}
			export.savePoint(point);
			othello.setEat(false);
			othello.setTiming(false);
			System.out.println("Com["+x+","+y+"]");
			nextTurn();
		}
	}
	
	public void setPoint(int x,int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public List<Best> getBest()
	{
		return computer.getListBest();
	}
	
	public void setLV(int lv)
	{
		computer.setCapacity(lv);
	}
	public int getLV( )
	{
		return computer.getCapacity();
	}
	
	public void showToggleViewAI( )
	{
		view.run();
	}

	public void resetView( )
	{
		if( view != null )		view.setVisible(false);
		view = null;
		setView(this);
	}
	
	public void releaseView( )
	{
		if( view == null )		return;
		view.setVisible(false);
		view = null;
	}
	
	public String toString()
	{
		return "Computer Player";
	}
}
