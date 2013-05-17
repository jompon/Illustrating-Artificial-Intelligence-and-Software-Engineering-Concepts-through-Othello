package Model;

import java.awt.Point;

/**
 * Best is a set data of node of A.I. process 
 */
public class Best {

  public Point point;
	public Integer score = 0;
	public Integer level = 0;
	public Integer sum = 0;
	public String toString()
	{
		return "Point["+point.x+","+point.y+"] = "+score+" | sum = "+sum+" | level = "+level;
	}
}
