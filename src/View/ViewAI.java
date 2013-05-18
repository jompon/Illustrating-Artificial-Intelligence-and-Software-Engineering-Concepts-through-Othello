package View;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import Model.Best;
import Model.Othello;
import Model.OthelloButton;
import Model.OthelloNode;
import Player.Computer;

public class ViewAI extends JFrame implements Runnable{

  private OthelloButton[][] button;
	private Othello othello;
	private Computer computer;
	private List<Best> listBest;
	private OthelloNode[] oNode;
	private JLabel[] label;
	private JLabel turn;
	private int lastX = -1;
	private int lastY = -1;
	private int size = 50;
	public ViewAI(Computer computer, OthelloButton[][] button, Othello othello)
	{
		this.computer = computer;
		this.button = button;
		this.othello = othello;
		setInitComponents( );
	}
	public void setInitComponents( )
	{
		this.setTitle("Show AI Step");
		this.setBounds(200, 200, 300, 150+size*computer.getLV()+10*(computer.getLV()-1));
		this.setResizable(false);
		
		Container contLabel = new Container();
		contLabel.setBounds(0, 0, getWidth(), getHeight());
		
		Container contNode = new Container();
		contNode.setBounds(100, 100, getWidth(), getHeight());

		JLabel intro = new JLabel("A.I. Process");
		intro.setBounds(10, 10, getWidth(), 30);
		intro.setFont(new Font("sansserif", Font.BOLD, 18));
		intro.setForeground(Color.BLUE);
		
		turn = new JLabel("Turn: "+othello.getNumTurn());
		turn.setBounds(10, intro.getY()+intro.getHeight()+10, getWidth(), 30);
		turn.setFont(new Font("sansserif", Font.BOLD, 18));
		
		contLabel.add(intro);
		contLabel.add(turn);
		
		oNode = new OthelloNode[computer.getLV()];
		label = new JLabel[computer.getLV()];
		for(int i=0;i<oNode.length;i++)
		{
			oNode[i] = new OthelloNode(size, 0);
			oNode[i].setBounds(200, turn.getY()+turn.getHeight()+size*i+0+10*(i+1), size, size);
			oNode[i].addMouseListener(new mouseAction(i));
			contNode.add(oNode[i]);
			
			label[i] = new JLabel( );
			label[i].setText("Move "+(i+1)+((i%2==0)?" (current player)":" (opponent player)"));
			label[i].setBounds(10, turn.getY()+turn.getHeight()+size*i+0+10*(i+1), 150, size);
			contLabel.add(label[i]);
			label[i].repaint();
		}
		this.add(contLabel);
		this.add(contNode);
	}
	
	public void update( )
	{
		turn.setText("Turn: "+othello.getNumTurn());
		if( othello.getCurrentPlayer().getType().equals("Computer") )
		{
			listBest = computer.getBest();
			for(int i=0;i<listBest.size();i++)
			{
				oNode[i].setValue(listBest.get(i).score);
				oNode[i].repaint();
			}
		}
	}
	
	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
		if( this.isVisible() )		this.setVisible(false);
		else						this.setVisible(true);
	}
	
	class mouseAction extends MouseAdapter implements MouseListener{
		
		private int index;
		public mouseAction(int index)
		{
			this.index = index;
		}
		public void mouseEntered(MouseEvent e)
		{
			if( listBest == null || index >= listBest.size() )		return;
			int x = listBest.get(index).point.x;
			int y = listBest.get(index).point.y;
			oNode[index].setToolTipText("<html>  Click me <br>Point["+x+","+y+"]"+"</html>");
		}
		public void mouseClicked(MouseEvent e)
		{
			if( listBest == null || index >= listBest.size() )		return;
			int x = listBest.get(index).point.x;
			int y = listBest.get(index).point.y;
			if( x == -1 && y == -1 )								return;
			if( button[x][y].isMarkView() )		button[x][y].setMarkView(false);
			else								button[x][y].setMarkView(true);
			button[x][y].repaint();
			if( lastX != -1 && lastY != -1 )
			{
				button[lastX][lastY].setMarkView(false);
				button[lastX][lastY].repaint();
			}
			lastX = x;
			lastY = y;
		}
	}
}
