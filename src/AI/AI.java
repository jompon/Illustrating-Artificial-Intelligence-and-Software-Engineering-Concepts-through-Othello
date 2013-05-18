package AI;

import java.awt.Point;

public interface AI {
  
	public void Think();
	public Point Place( );
	public void resetListNode( );
	public void setCapacity(int capacity);
	public void setTable(char[][] button);
}
