package Player;

public interface IPlayer {
  
	public void setPoint(int x,int y);
	public void execute( );
	public void nextTurn( );
	public void Mark(int x,int y,String command);
	public int getID();
	public String getNoPlayer( );
	public String toString( );
}
