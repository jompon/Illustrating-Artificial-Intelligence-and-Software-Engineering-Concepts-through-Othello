package AI;

import Model.Othello;


public abstract class AIAbstract implements AI{

	protected Othello othello;
	protected int difficulty = 3;
	public AIAbstract(Othello othello, int difficulty)
	{
		this.othello = othello;
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
}
