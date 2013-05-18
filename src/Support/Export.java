package Support;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Model.OrdinalPlayer;
import Model.Othello;

/**
 * This class for collect data which of A.I. process and export its to csv file
 */
public class Export {

  private Othello othello;
	private List<String> title = new ArrayList<String>();
	private List<List<String>> point = new ArrayList<List<String>>();
	private OrdinalPlayer firstPlayer;
	private OrdinalPlayer secondPlayer;
	public static Export export;
	private Export(Othello othello)
	{
		this.othello = othello;
		title.add("Turn");
		title.add("Player");
		title.add("Piece");
		title.add("Minimax Level 1 (Move)");
		title.add("Minimax Level 2");
		title.add("Minimax Level 3");
		title.add("Minimax Level 4");
		title.add("Minimax Level 5");
	}
	public void setPlayer( )
	{
		firstPlayer = othello.getPlayer(1);
		secondPlayer = othello.getPlayer(2); 
	}
	public void reset( )
	{
		title.clear();
		for(int i=0;i<point.size();i++)
		{
			point.get(i).clear();
		}
		point.clear();
	}
	public static Export getInstance(Othello othello)
	{
		if( export == null )	export = new Export(othello);
		return export;
	}
	
	public void savePoint(String pointMove)
	{
		List<String> move = new ArrayList<String>();
		move.add(pointMove);
		point.add(move);
	}
	public void savePoint(List<String> pointLV)
	{
		point.add(pointLV);
	}
	public void writeFile( )
	{
		try {
			FileWriter writer = new FileWriter("Othello.csv");
			for(int i=0;i<title.size();i++)
			{
				writer.append(title.get(i));
				writer.append(",");
			}
			writer.append("\n");
			for(int i=0;i<point.size();i++)
			{
				writer.append(String.valueOf(i+1));
				writer.append(",");
				if( i%2 == 0 ){
					writer.append(firstPlayer.getType());
					writer.append(",");
					writer.append(firstPlayer.getPiece());
					writer.append(",");
				}
				else{
					writer.append(secondPlayer.getType());
					writer.append(",");
					writer.append(secondPlayer.getPiece());
					writer.append(",");
				}
				for(int j=0;j<point.get(i).size();j++)
				{
					writer.append(point.get(i).get(j));
					writer.append(",");
				}
				writer.append("\n");
			}
			
			writer.flush();
		    writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
