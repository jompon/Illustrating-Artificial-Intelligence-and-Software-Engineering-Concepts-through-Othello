package Model;

/**
 * 'Mark' is indicating that user want make system to show color mark on UI
 */
public class Mark {

  private boolean markPlace;	// show that position can place
	private boolean markFlip;	// show that position can be eaten
	public Mark()
	{
		markPlace = false;
		markFlip = false;
	}
	
	public boolean isMarkPlace()
	{
		return markPlace;
	}
	public void setMarkPlace(boolean markPlace)
	{
		this.markPlace = markPlace;
	}
	
	public boolean isMarkFlip()
	{
		return markFlip;
	}
	public void setMarkFlip(boolean markFlip)
	{
		this.markFlip = markFlip;
	}
	
	public String toString()
	{
		return "Mark Class";
	}
}
