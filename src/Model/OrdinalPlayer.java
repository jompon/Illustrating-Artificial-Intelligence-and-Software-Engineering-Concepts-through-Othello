package Model;

/**
 * OrdinalPlayer is model on one player
 */
public class OrdinalPlayer {

	private static int number = 0;		// number of player
	private int ordinalNumber;			// ordinal of player
	private char piece;					// color of player
	private String typePlayer = "";		// type of player
	private int IdPlayer;				// id number is indicating for player type
	public OrdinalPlayer(char piece, String typePlayer)
	{
		this.piece = piece;
		this.typePlayer = typePlayer;
		ordinalNumber = ++number;
		setIdPlayer();
	}
	public static void newGame( )
	{
		number = 0;
	}
	public String getType( )
	{
		return typePlayer;
	}
	
	public char getPiece()
	{
		return piece;
	}
	
	public String getColorPiece()
	{
		if( piece == 'O' )			return "White";
		else if( piece == 'X' )		return "Black";
		return "It has no color";
	}
	
	public int getIdPlayer( )
	{
		return IdPlayer;
	}
	private void setIdPlayer( )
	{
		if( typePlayer.equals("Computer") )		IdPlayer = 0;
		else if( typePlayer.equals("Human") )	IdPlayer = 1;
		else									IdPlayer = 9;
	}
	
	public int getOrdinalNumber()
	{
		return ordinalNumber;
	}

	public String toString( )
	{
		return ordinalNumber + "P Player";
	}
}
