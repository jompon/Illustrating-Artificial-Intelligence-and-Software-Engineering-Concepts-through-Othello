package AI;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Model.Best;
import Model.Othello;

/**
 * MiniMax is the one way of A.I. process to play a game
 */
public class MiniMax{

  private Othello othello;
	private char[][] button;
	private List<List<Best>> listNodeBest;
	private List<Best> listBest;
	private Best best;
	private int difficulty = 3;
	public MiniMax(Othello othello, int difficulty) 
	{
		this.othello = othello;
		listNodeBest = new ArrayList<List<Best>>();
		listBest = new ArrayList<Best>();
		this.difficulty = difficulty;
	}

	public void setCapacity(int difficulty)
	{
		this.difficulty = difficulty;
	}
	public int getCapacity( )
	{
		return difficulty;
	}
	
	public void resetListNode( )
	{
		for(int i=0;i<listNodeBest.size();i++)
		{
			listNodeBest.get(i).clear();
		}
		listNodeBest.clear();
		listBest.clear();
	}
	
	public List<Best> getListBest()
	{
		return listBest;
	}
	
	public Best miniMax(boolean computer, char[][] button, int count, char my, char opponent, int sc)
	{
		Best best = new Best();
		Best reply = new Best();
		
		List<Best> listBest = new ArrayList<Best>();
		
		if( listNodeBest.size() == 0 ){
			listNodeBest.add(listBest);
		}else{
			for(int i=listNodeBest.size()-1;i>=0;i--)
			{
				if( count >= listNodeBest.get(i).get(0).level ){
					listNodeBest.add(i+1, listBest);
					break;
				}
			}
		}
		int num = 0;
		for(int i=0;i<button.length;i++){
			for(int j=0;j<button[i].length;j++){
				if( button[i][j] == 'M' ){
					num++;
					button[i][j] = my;
					int getScore = getScore(i, j, button, opponent) * ((computer)?1:-1);
					Best b = new Best();
					b.score = getScore;
					b.sum = sc+getScore;
					b.point = new Point(i,j);
					b.level = count;
					listBest.add(b);
					/*System.out.print("Point = "+b.point);
					System.out.print(" Score = "+b.score);
					System.out.print(" Sum = "+b.sum);
					System.out.println(" Level = "+b.level);*/
					char[][] buttons = copyTable(button);
					checkAround(i, j, buttons, opponent, 'E', my);
					mark(buttons, my, opponent);
					if( count < difficulty )
					{
						reply = miniMax( !computer, buttons, count+1, opponent, my, sc+getScore );
						if( num == 1 )
						{
							best.score = getScore;
							best.sum = reply.sum;;
							best.point = new Point(i,j);
							best.level = count;
						}
						else if( (computer && reply.sum > best.sum) || (!computer && reply.sum < best.sum) )
						{
							best.score = getScore;
							best.sum = reply.sum;
							best.point = new Point(i,j);
							best.level = count;
						}
						b.sum = reply.sum;
					}
					else
					{
						if( num == 1 )
						{
							best.score = getScore;
							best.sum = sc+getScore;
							best.point = new Point(i,j);
							best.level = count;
						}
						else if( (computer && sc+getScore > best.sum) || (!computer && sc+getScore < best.sum) )
						{
							best.score = getScore;
							best.sum = sc+getScore;
							best.point = new Point(i,j);
							best.level = count;
						}
					}
					button[i][j] = 'M';
				}
			}
		}
		if( num == 0 )
		{
			Best b = new Best();
			b.score = 0;
			b.sum = sc;
			b.point = new Point(-1,-1);
			b.level = count;
			listBest.add(b);
			char[][] buttons = copyTable(button);
			mark(buttons, my, opponent);
			if( count < difficulty )
			{
				reply = miniMax( !computer, buttons, count+1, opponent, my, sc );
				best.score = 0;
				best.sum = reply.sum;
				best.point = new Point(-1,-1);
				best.level = count;
				b.sum = reply.sum;
			}
			else
			{
				best.score = 0;
				best.sum = sc;
				best.point = new Point(-1,-1);
				best.level = count;
			}
		}
		/*System.out.println("---------------Select--------------");
		
		System.out.print("Point = "+best.point);
		System.out.print(" Score = "+best.score);
		System.out.print(" Sum = "+best.sum);
		System.out.println(" Level = "+best.level);
		System.out.println();*/
		return best;
	}
	
	public void findListBest(Best root, int count, int init, int p)
	{
		if( count > difficulty )		return;
		int sum = 0;
		for(int i=init;i<=p;i++)
		{
			sum += listNodeBest.get(i).size();
		}
		for(int j=0;j<listNodeBest.get(p).size();j++){
			if( listNodeBest.get(p).get(j).sum.equals(root.sum) )
			{
				Best best = new Best();
				best.score = listNodeBest.get(p).get(j).score;
				best.sum = listNodeBest.get(p).get(j).sum;
				best.point = new Point(listNodeBest.get(p).get(j).point.x, listNodeBest.get(p).get(j).point.y);
				best.level = listNodeBest.get(p).get(j).level;
				listBest.add(best);
				//System.out.println("Select ListBest["+p+","+j+"]: Score = "+best.score+" Sum = "+best.sum+" Point = "+best.point+" Level  = "+best.level);
				sum = sum - (listNodeBest.get(p).size()-1-j);
				break;
			}
		}
		init = p;
		while( init<listNodeBest.size() && count == listNodeBest.get(init).get(0).level )		init++;
		findListBest(root, count+1, init, sum+init-1);
	}
	
	private void mark(char[][] button, char en, char place)
	{
		for(int i=0;i<button.length;i++){
			for(int j=0;j<button[i].length;j++){
				if( button[i][j] != 'X' && button[i][j] != 'O' )
				{
					button[i][j] = 0;
				}
			}
		}
		for(int i=0;i<button.length;i++){
			for(int j=0;j<button[i].length;j++)
			{
				if( button[i][j] == 0 )
				{
					checkAround(i, j, button, en, 'M', place);
				}
			}
		}
	}
	
	private void checkAround(int x, int y, char[][] button, char en, char command, char place)
	{
		for(int i=-1;i<=1;i++){
			for(int j=-1;j<=1;j++){
				if( x+i >= 0 && x+i < button.length && !( i == 0 && j == 0 ) &&
				    y+j >= 0 && y+j < button[x+i].length )
				{
					int num = numTakePiece( x , y , i , j , button, en );
					if( isTakePiece(x, y, i, j, button, num, place) ){
						if( command == 'E' )
						{
							eat( x , y , i , j, button, num, en );
						}
						if( command == 'M' ){
							button[x][y] = 'M';
							return;
						}
					}
				}
			}
		}
	}
	
	private int getScore(int x, int y, char[][] button, char en)
	{
		int score = 0;
		//System.out.println("TABLE["+x+","+y+"]");
		for(int i=-1;i<=1;i++){
			for(int j=-1;j<=1;j++){
				if( x+i >= 0 && x+i < button.length && !( i == 0 && j == 0 ) &&
				    y+j >= 0 && y+j < button[x+i].length )
				{
					int num = numTakePiece( x , y , i , j , button, en);
					if( isTakePiece(x, y, i, j, button, num, button[x][y]) ){
						score += num;
						//System.out.println("DIRECTION = ("+i+","+j+") "+"Num = "+num);
					}
				}
			}
		}
		return score;
	}
	
	private boolean isTakePiece( int x , int y , int a , int b , char[][] button, int num , char place)
	{
		if( num == 0 )		return false;
		int i = a*(num+1), j = b*(num+1);
		if( x+i >=0 && x+i < button.length && y+j >= 0 && y+j < button[x+i].length )
		{
			//System.out.println(" Button["+(x+i)+","+(y+j)+"]: "+button[x+i][y+j]+" ["+x+","+y+"] "+a+","+b);
			if( button[x+i][y+j] == place )		return true;
		}
		return false;
	}
	private int numTakePiece( int x , int y , int a , int b , char[][] button, char en)
	{
		if( x+a >= 0 && x+a < button.length && y+b >= 0 && y+b < button[x+a].length )
		{
			if( button[x+a][y+b] == en )		return numTakePiece( x , y , a+a/Math.abs(((a==0)?1:a)) , b+b/Math.abs(((b==0)?1:b)), button, en )+1;
		}
		return 0;
	}
	
	private void eat( int x , int y , int a , int b , char[][] button, int num, char en)
	{
		if( num <= 0 )		return;
		button[x+a][y+b] = button[x][y];
		eat( x , y , a+a/Math.abs(((a==0)?1:a)) , b+b/Math.abs(((b==0)?1:b)), button, num-1, en );
	}
	private char[][] copyTable(char[][] button)
	{
		char[][] newButton = new char[button.length][button.length];
		for(int i=0;i<button.length;i++){
			for(int j=0;j<button[i].length;j++){
				newButton[i][j] = button[i][j];
			}
		}
		return newButton;
	}
	private void printTable(String title, char[][] table)
	{
		System.out.println("Title: "+title);
		for(int i=0;i<table.length;i++){
			for(int j=0;j<table[i].length;j++){
				System.out.print(table[i][j]);
			}
			System.out.println( );
		}
	}
	
	public void Think( ) 
	{
		best = miniMax(true, this.button, 1, othello.getPiece(), othello.getOpponentPiece(), 0);
		//System.out.println("Find Best: Score = "+best.score+" Sum = "+best.sum+ " Point = "+best.point+" Level = "+best.level);
		findListBest(best, 1, 0, 0);
	}

	public Point Place() {
		// TODO Auto-generated method stub
		/*for(int i=0;i<listNodeBest.size();i++){
			for(int j=0;j<listNodeBest.get(i).size();j++){
				System.out.println("LIST["+i+","+j+"]: Score = "+listNodeBest.get(i).get(j).score+" Sum = "+listNodeBest.get(i).get(j).sum+" Point = "+listNodeBest.get(i).get(j).point+" Level = "+listNodeBest.get(i).get(j).level);
			}
		}
		for(int i=0;i<listBest.size();i++)
		{
			System.out.println("LIST["+i+"]: Score = "+listBest.get(i).score+" Sum = "+listBest.get(i).sum+" Point = "+listBest.get(i).point+" Level = "+listBest.get(i).level);
		}*/
		return best.point;
	}

	public void setTable(char[][] button) 
	{
		this.button = button;
	}
}
