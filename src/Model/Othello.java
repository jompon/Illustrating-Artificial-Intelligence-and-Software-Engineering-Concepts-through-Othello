package Model;

import javax.swing.JOptionPane;

/**
 * Othello is algorithm controller for control the rule on this game
 * This is singleton class
 */
public class Othello {
	
	private char piece;						// current own piece
	private char pieceEn;					// current enemy piece
	private byte blackPiece;				// number of black piece
	private byte whitePiece;				// number of white piece
	private boolean isEat;					// indicate this turn can eat
	private boolean isGameOver;				// indicate that game over
	private int turn;						// turn of ordinal number of player
	private boolean wait;					// indicate to know that it is still turning
	private int numTurn;					// current turn
	private OrdinalPlayer firstPlayer;		// first player
	private OrdinalPlayer secondPlayer;		// second player
	private OrdinalPlayer currentPlayer;	// current player is indicating now turn is who turn player
	private char[][] table;					//table on the board
	private static Othello othello;			
	private Othello()
	{
		table = new char[8][8];
		reset( );
	}
	public static Othello getInstance()
	{
		if( othello == null )	othello = new Othello();
		return othello;
	}
	public void newGame()
	{
		OrdinalPlayer.newGame();
		firstPlayer = secondPlayer = currentPlayer = null;
	}
	public void reset( )
	{	
		isGameOver = false;
		wait = false;
		turn = 1;
		numTurn = 1;
		this.piece = 'X';
		this.pieceEn = 'O';
		blackPiece = 2;
		whitePiece = 2;
		for(int i=0;i<table.length;i++){
			for(int j=0;j<table[i].length;j++){
				table[i][j] = 0;
			}
		}
	}
	public void addPlayer(String typePlayer)
	{
		System.out.println("AddPlayer");
		if( this.firstPlayer == null )				this.firstPlayer = this.currentPlayer = new OrdinalPlayer('X', typePlayer);
		else if( this.secondPlayer == null )		this.secondPlayer = new OrdinalPlayer('O', typePlayer);
	}
	
	public OrdinalPlayer getPlayer(int ordinalNumber)
	{
		if( ordinalNumber == 1 )		return firstPlayer;
		if( ordinalNumber == 2 )		return secondPlayer;
		return null;
	}
	
	public OrdinalPlayer getCurrentPlayer( )
	{
		return currentPlayer;
	}
	
	public boolean isTiming()
	{
		return wait;
	}
	public void setTiming(boolean wait)
	{
		this.wait = wait;
	}
	
	public char[][] getTable( )
	{
		return table;
	}
	public void setTable(int x, int y, char place)
	{
		table[x][y] = place;
	}
	
	public byte getNumBlackPiece( )
	{
		return blackPiece;
	}
	public byte getNumWhitePiece( )
	{
		return whitePiece;
	}
	
	public void addPiece(char Piece)
	{
		if( Piece == 'X' )		blackPiece++;
		if( Piece == 'O' )		whitePiece++;
	}
	public void removePiece(char Piece)
	{
		if( Piece == 'X' )		blackPiece--;
		if( Piece == 'O' )		whitePiece--;
	}
	
	public boolean isEat( )
	{
		return isEat;
	}
	public void setEat( boolean isEat )
	{
		this.isEat = isEat;
	}

	public char getPiece()
	{
		return piece;
	}
	public char getOpponentPiece()
	{	
		return pieceEn;
	}
	public int getTurn( )
	{
		return turn;
	}

	public int getNumTurn()
	{
		return numTurn;
	}
	
	public void nextTurn()
	{
		numTurn++;
		swap( );
		if( numTurn%2 == 1 )						currentPlayer = firstPlayer;
		else										currentPlayer = secondPlayer;
		turn = currentPlayer.getOrdinalNumber();
	}
	// swap between own piece and enermy piece
	private void swap( )
	{
		char temp = piece;
		piece = pieceEn;
		pieceEn = temp;
	}
	
	public String getColorPiece( )
	{
		return currentPlayer.getColorPiece();
	}
	public int getIdPlayer( )
	{
		return currentPlayer.getIdPlayer();
	}
	
	public String showWin( )
	{
		if( blackPiece > whitePiece )		return "Black Winner";
		else if( blackPiece < whitePiece )	return "White Winner";
		return "Draw";
	}
	
	public boolean isGameOver( )
	{
		return isGameOver;
	}
	public void GameOver( )
	{
		JOptionPane.showMessageDialog(null, showWin());
		isGameOver = true;
	}
}
