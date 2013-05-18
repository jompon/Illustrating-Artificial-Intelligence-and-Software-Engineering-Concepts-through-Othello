package Support;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Format class is other function of this program or support class
 */
public class Format {

  //set limit to JTextField
	public JTextFieldLimit getJTextFieldLimits(long limit)
	{
		return new JTextFieldLimit(limit);
	}
	
	//How many space bar
	public String SpaceBar( long space )
	{
		if( space < 1 )			return "";
		return 	" " + SpaceBar( space-1 );
	}
	
	public String FixEdge( String first, String second, long allLength )
	{
		return first + SpaceBar( allLength - (second.length()+first.length())*47/20 ) + second;
	}
	
	//fix a number to three character
	public String Fix_Three_Length(long object)
	{
		String Obj = String.valueOf(object); 
		for(int i=Obj.length();i<3;i++)
		{
			Obj = "0"+Obj;
		}
		return Obj;
	}
	
	public String getEnCode(String code,char ascii)
	{
		String encode = "";
		for(int j=0;j<code.length();j++)
		{
			char temp = (char) (code.charAt(j)-ascii);
			encode += (temp+"");
		}
		return encode;
	}
	
	public URL getIcon(String image)
	{
		return getClass().getResource(image);
	}
	
	//Resize icon
	public Icon getFixIcon(String image, int Width, int Height) throws IOException
	{
		ImageIcon img = new ImageIcon( getIcon(image) );
		Image img2 = img.getImage();
		Image newImg = img2.getScaledInstance(Width, Height,  java.awt.Image.SCALE_SMOOTH);   
		Icon newIcon = new ImageIcon(newImg);
		return newIcon;
	}
	
	public String toString()
	{
		return "Format Class";
	}
	
	class JTextFieldLimit extends PlainDocument {
		
		private long limit;
		public JTextFieldLimit(long limit)
		{
			super();
			this.limit = limit;
		}
		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException
		{
			if (str == null)								return;
		    if ((getLength() + str.length()) <= limit) 		super.insertString(offset, str, attr);
		}
	}
}
