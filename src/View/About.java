package View;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import Support.Format;

public class About extends JFrame implements Runnable{
  
	private Format format;
	public About( )
	{
		super("About Dialog");
		format = new Format();
		initComponents();
	}

	public void initComponents()
	{
		//Image im = Toolkit.getDefaultToolkit().getImage(format.getIcon("minesweeper.jpg"));
		//this.setIconImage(im);
		this.setTitle("About Dialog");
		this.setBounds(300, 300, 600, 150);
		this.setResizable(false);
		
		Container contain = new Container();
		//JButton button = new JButton( format.getFixIcon("Bang.png", 100, 100) );
		//button.setBounds(this.getWidth()/2-40, 0, 100, 100);
		//button.setBorder(null);
		//button.setContentAreaFilled(false);
		//contain.add(button,BorderLayout.NORTH);
		
		JTextArea area = new JTextArea();
		area.setBounds(0, 0, this.getWidth(), this.getHeight());
		area.setFont( new Font(Font.MONOSPACED,Font.BOLD,22) );
		area.setText( format.SpaceBar(2)+"Illustrating Artificial Intelligence and\n" +
										"Software Engineering Concepts through Othello\n" +
										format.SpaceBar(19)+"V.1.0.0\n" +
										format.SpaceBar(15)+"Update 17/May/56");
		area.setLineWrap(true);
		area.setFocusable(false);
		area.setBackground(null);
		
		this.add(contain);
		this.add(area);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.setVisible(true);
	}
	
	public String toString()
	{
		return "About Class";
	}
}
