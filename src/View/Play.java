package View;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import Model.Mark;
import Model.Othello;
import Model.OthelloBackGround;
import Model.OthelloButton;
import Player.Computer;
import Player.Human;
import Player.IPlayer;
import Player.Player;
import Support.Export;
import Support.Format;

/**
 * @author Jompon Kittisupjaroen
 * @version 1.0.0
 * 
 * Play is View for display the system controller and model of this game
 */
public class Play extends JFrame implements Runnable{

	private Othello othello;
	private OthelloButton currentTurn;					// show a small piece for to know now turn is who turn 
	private OthelloButton[][] button;					// table of the board
	private Player firstPlayer;
	private Player secondPlayer;
	private Mark Mark;									
	private Format format;
	private JLabel blackLabel;							// show the number of black piece
	private JLabel whiteLabel;							// show the number of white piece
	private JLabel P_1;									// 1P Player
	private JLabel P_2;									// 2P Player
	private OthelloBackGround imgBg;
	private JMenuBar bar;
	private JRadioButton black;
	private JRadioButton white;
	private JRadioButton[] difficulty;					
	private Container page;								// page
	private int level = 0;								// difficulty of computer
	private int size = 50;								// size of piece
	private Export export;
	public Play( Othello othello )
	{
		this.setName("Play");
		this.setTitle("Othello Game");
		this.othello = othello;
		export = Export.getInstance(othello);
		Mark = new Mark();
		format = new Format();
		currentTurn = new OthelloButton(Mark,format,size/2);
		button = new OthelloButton[8][8];
		imgBg = new OthelloBackGround("othello.png");
		initComponents();
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void initComponents()
	{	
		bar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenu help = new JMenu("Help");
		JMenuItem newGame = new JMenuItem("New");
		JMenuItem exit = new JMenuItem("Exit");
		JMenuItem about = new JMenuItem("About");
		JMenu mark = new JMenu("Mark");
		JCheckBoxMenuItem markPlace = new JCheckBoxMenuItem("Place");
		JCheckBoxMenuItem markFlip = new JCheckBoxMenuItem("Flip");
		mark.add(markPlace);
		mark.add(markFlip);
		file.add(newGame);
		file.add(mark);
		file.add(new JSeparator());
		file.add(exit);
		help.add(about);
		bar.add(file);
		bar.add(help);
		newGame.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.getKeyText(KeyEvent.VK_F2)) );
		markPlace.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK) );
		markFlip.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK) );
		about.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.ALT_MASK));
		exit.setAccelerator( KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK) );
		newGame.addActionListener(new newAction());
		markPlace.addActionListener(new menuAction(this));
		markFlip.addActionListener(new menuAction(this));
		exit.addActionListener(new menuAction(this));
		about.addActionListener(new menuAction(this));
		bar.setBounds(0, 0, size*12+1, size/2);
		this.setBounds(200, 150, size*10+7, size*10+29+bar.getHeight());
		page = new Container();
		this.add(page);
		cover();
	}
	
	//first page
	public void cover( )
	{
		page = getContentPane();
		this.add(bar);
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage( format.getIcon("reversi.jpg") ));
		
		imgBg.setBound(0, bar.getHeight(), getWidth()-6, getHeight()-bar.getHeight()-28);
		page.add(imgBg);
		
		P_1 = new JLabel("1P Player");
		P_1.setForeground(new Color(10,10,240));
		P_1.setFont(new Font("sansserif", Font.BOLD, 20));
		P_1.setBounds(this.getWidth()/2-50, this.getHeight()/2+90, 100, 30);
		P_1.addMouseListener(new MouseActionComponents());
		
		P_2 = new JLabel("2P Player");
		P_2.setForeground(new Color(10,10,240));
		P_2.setFont(new Font("sansserif", Font.BOLD, 20));
		P_2.setBounds(this.getWidth()/2-50, this.getHeight()/2+140, 100, 30);
		P_2.addMouseListener(new MouseActionComponents());
		
		page.add(P_1);
		page.add(P_2);
		page.add(imgBg);
	}
	
	//second page
	public void select( )
	{
		page = getContentPane();
		this.add(bar);
		
		Container contLabel = new Container();
		contLabel.setBounds(0, 0, getWidth(), getHeight());
		
		JLabel sideLabel = new JLabel("Select side color");
		sideLabel.setBounds(size/2, size/2+bar.getHeight(), getWidth(), size/2);
		sideLabel.setFont(new Font("sansserif", Font.BOLD, 18));
		
		OthelloButton blackButton = new OthelloButton(Mark, format, 100);
		blackButton.setPiece('X');
		blackButton.setBounds(100, size/4+sideLabel.getY()+sideLabel.getHeight(), 100, 100);
		
		black = new JRadioButton("BLACK");
		black.setBounds(blackButton.getX()+blackButton.getSquareSize(), blackButton.getY()+blackButton.getHeight()/2-bar.getHeight(), 100, size);
		black.setSelected(true);
		
		OthelloButton whiteButton = new OthelloButton(Mark, format, 100);
		whiteButton.setPiece('O');
		whiteButton.setBounds(100, blackButton.getY()+blackButton.getHeight(), 100, 100);
		
		white = new JRadioButton("WHITE");
		white.setBounds(whiteButton.getX()+whiteButton.getSquareSize(), whiteButton.getY()+whiteButton.getHeight()/2-bar.getHeight(), 100, size);
		
		ButtonGroup sidePlayer = new ButtonGroup();
		sidePlayer.add(black);
		sidePlayer.add(white);
		
		ButtonGroup levelPlayer = new ButtonGroup();
		
		JLabel levelLabel = new JLabel("Select difficulty");
		levelLabel.setBounds(size/2, size/4+whiteButton.getY()+whiteButton.getHeight(), getWidth(), size/2);
		levelLabel.setFont(new Font("sansserif", Font.BOLD, 18));
		
		difficulty = new JRadioButton[5];
		for(int i=0;i<difficulty.length;i++)
		{
			difficulty[i] = new JRadioButton();
			difficulty[i].setBounds(size, levelLabel.getY()+levelLabel.getHeight()+size/4+i*size/2, getWidth(), size/2);
			levelPlayer.add(difficulty[i]);
			
			switch(i){
				case 0:	difficulty[i].setText("Very Easy");		break;
				case 1:	difficulty[i].setText("Easy");			break;
				case 2:	difficulty[i].setText("Medium");
						difficulty[i].setSelected(true);		break;
				case 3:	difficulty[i].setText("Hard");			break;
				case 4:	difficulty[i].setText("Very Hard");		break;
			}
			page.add(difficulty[i]);
		}
		
		contLabel.add(sideLabel);
		contLabel.add(levelLabel);
		
		JButton ok = new JButton("OK");
		ok.setBounds(size*4, size*10-size/2, size*2, 3*size/5);
		ok.addActionListener(new ActionSelectSidePlayer());
		
		page.add(blackButton);
		page.add(whiteButton);
		page.add(black);
		page.add(white);
		page.add(ok);
		page.add(contLabel);
	}
	
	//third page
	public void play( )
	{
		page = getContentPane();
		this.add(bar);
		
		JButton restart = new JButton("Restart");
		restart.setBounds(size/4, size*9+size/4+bar.getHeight(), size*2, 3*size/5);
		restart.addActionListener(new restartAction());
		
		JButton aiProcess = new JButton("AI Process");
		aiProcess.setBounds(getWidth()-size/4-size%4-size*2, size/5+bar.getHeight(), size*2, 3*size/5);
		aiProcess.addActionListener(new menuAction(this));
		
		JButton btnExport = new JButton("Export");
		btnExport.setBounds(getWidth()-size/4-size%4-size*2, size*9+size/5+bar.getHeight(), size*2, 3*size/5);
		btnExport.addActionListener(new menuAction(this));
		
		Container contLabel = new Container();
		contLabel.setBounds(0, 0, getWidth(), getHeight());
		blackLabel = new JLabel("Black: "+othello.getNumBlackPiece());
		blackLabel.setBounds(size*3+25, 460+bar.getHeight(), 53, 30);
		whiteLabel = new JLabel("White: "+othello.getNumWhitePiece());
		whiteLabel.setBounds(size*4+25+54, 460+bar.getHeight(), 53, 30);
		
		contLabel.add(blackLabel);
		contLabel.add(whiteLabel);
		
		currentTurn.setPiece('X');
		currentTurn.setBounds(size/4, size/4+bar.getHeight(), size/2, size/2);
		page.add(currentTurn);
		
		for(int i=0;i<button.length;i++){
			for(int j=0;j<button[i].length;j++){
				button[i][j] = new OthelloButton(Mark, format, size);
				button[i][j].setBounds((j+1)*size, (i+1)*size+bar.getHeight(), size+1, size+1);
				page.add(button[i][j]);
			}
		}
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i=3;i<=4;i++){
					try {
						Thread.sleep(500);
						othello.setTable(i, i, 'O');
						button[i][i].setPiece('O');
						othello.setTable(i, i, 'O');
						repaint();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				for(int i=0;i<=1;i++){
					try {
						Thread.sleep(500);
						othello.setTable(3+(i+1)%2, 3+i%2, 'X');
						button[3+(i+1)%2][3+i%2].setPiece('X');
						othello.setTable(3+(i+1)%2, 3+i%2, 'X');
						repaint();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				for(int i=0;i<button.length;i++){
					for(int j=0;j<button[i].length;j++){
						button[i][j].addMouseListener(new mouseAction(i,j));
					}
				}
				button[3][2].setMarkPlace(true);
				button[5][4].setMarkPlace(true);
				button[2][3].setMarkPlace(true);
				button[4][5].setMarkPlace(true);
				othello.setTable(3, 2, 'M');
				othello.setTable(5, 4, 'M');
				othello.setTable(2, 3, 'M');
				othello.setTable(4, 5, 'M');
				othello.setEat(true);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if( firstPlayer.getID() == 0 ){
					firstPlayer.execute( );
					currentTurn.setPiece('O');
					currentTurn.repaint();
				}
			}
		}).start();
		if( level != 0 ){
			page.add(aiProcess);
			page.add(btnExport);
		}
		page.add(restart);
		page.add(contLabel);
	}
	
	public void setComponents(int componentsPage)
	{
		page.removeAll();
		if( componentsPage == 0 )			cover( );
		if( componentsPage == 1 )			select();
		if( componentsPage == 2 )			play( );
		page.repaint();
	}
	
	public void setPlayer(String P1,String P2, int level)
	{
		if( firstPlayer != null )	firstPlayer = null;
		if( secondPlayer != null )	secondPlayer = null;
		if( P1.equals("Human") ){
			othello.addPlayer("Human");
			firstPlayer = new Human(button, othello);
		}
		else if( P1.equals("Computer") ){
			othello.addPlayer("Computer");
			firstPlayer = new Computer(button, othello, level);
		}
		if( P2.equals("Human") ){
			othello.addPlayer("Human");
			secondPlayer = new Human(button, othello);
		}
		else if( P2.equals("Computer") ){
			othello.addPlayer("Computer");
			secondPlayer = new Computer(button, othello, level);
		}
		export.setPlayer();
	}
	
	public void resetMark()
	{
		for(int i=0;i<button.length;i++){
			for(int j=0;j<button[i].length;j++)
			{
				if( button[i][j].isMarkPlace() )		button[i][j].repaint();
			}
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.setVisible(true);
	}
	
	class MouseActionComponents extends MouseAdapter implements MouseListener{
		
		public void mouseClicked(MouseEvent e) {
			if( e.getSource() == P_1 )				setComponents(1);
			else if( e.getSource() == P_2 ){
				setPlayer("Human","Human", 0);
				setComponents(2);
			}
		}
	}
	
	class ActionSelectSidePlayer implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			for(int i=0;i<difficulty.length;i++)
			{
				if( difficulty[i].isSelected() )
				{
					level = i+1;
					break;
				}
			}
			if( black.isSelected() )			setPlayer("Human", "Computer", level);
			else if( white.isSelected() )		setPlayer("Computer", "Human", level);
			setComponents(2);
		}
	}
	
	class menuAction implements ActionListener{

		private JFrame play;
		private About about;
		public menuAction(JFrame play)
		{
			this.play = play;
			about = new About();
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if( e.getActionCommand().equals("About") )		about.run();
			else if( e.getActionCommand().equals("AI Process") )
			{
				if( firstPlayer instanceof Computer )
				{
					Computer com = (Computer)firstPlayer;
					com.showToggleViewAI();
				}
				else if( secondPlayer instanceof Computer )
				{
					Computer com = (Computer)secondPlayer;
					com.showToggleViewAI();
				}
			}
			else if( e.getActionCommand().equals("Export") )
			{
				export.writeFile();
			}
			else if( e.getActionCommand().equals("Place") )
			{
				if( Mark.isMarkPlace() )					Mark.setMarkPlace(false);
				else										Mark.setMarkPlace(true);
				resetMark();
			}
			else if( e.getActionCommand().equals("Flip") )
			{
				if( Mark.isMarkFlip() )						Mark.setMarkFlip(false);
				else										Mark.setMarkFlip(true);
			}
			else if( e.getActionCommand().equals("Exit") )	
			{
				if( JOptionPane.showConfirmDialog(null, "Are you sure?", "Exit", 0) == 0 )	
					ExitGame( );
			}
		}
		public void ExitGame()
		{
			play.setVisible(false);
			dispose();
			play.setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
	}
	// new game; back to first page
	class newAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			export.reset();
			othello.reset( );
			othello.newGame( );
			Player.newGame();
			if( firstPlayer instanceof Computer )
			{
				Computer com = (Computer)firstPlayer;
				com.releaseView();
			}
			else if( secondPlayer instanceof Computer )
			{
				Computer com = (Computer)secondPlayer;
				com.releaseView();
			}
			blackLabel.setText("Black: "+othello.getNumBlackPiece());
			whiteLabel.setText("White: "+othello.getNumWhitePiece());
			button = new OthelloButton[8][8];
			setComponents(0);
		}
	}
	// restart game
	class restartAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			export.reset();
			othello.reset( );
			if( firstPlayer instanceof Computer )
			{
				Computer com = (Computer)firstPlayer;
				com.resetView();
			}
			else if( secondPlayer instanceof Computer )
			{
				Computer com = (Computer)secondPlayer;
				com.resetView();
			}
			currentTurn.setPiece('X');
			currentTurn.repaint();
			blackLabel.setText("Black: "+othello.getNumBlackPiece());
			whiteLabel.setText("White: "+othello.getNumWhitePiece());
			for(int i=0;i<button.length;i++){
				for(int j=0;j<button[i].length;j++)
				{
					button[i][j].initComponents();
					button[i][j].repaint();
				}
			}
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					for(int i=3;i<=4;i++){
						try {
							Thread.sleep(500);
							othello.setTable(i, i, 'O');
							button[i][i].setPiece('O');
							othello.setTable(i, i, 'O');
							repaint();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					for(int i=0;i<=1;i++){
						try {
							Thread.sleep(500);
							othello.setTable(3+(i+1)%2, 3+i%2, 'X');
							button[3+(i+1)%2][3+i%2].setPiece('X');
							othello.setTable(3+(i+1)%2, 3+i%2, 'X');
							repaint();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					for(int i=0;i<button.length;i++){
						for(int j=0;j<button[i].length;j++){
							button[i][j].addMouseListener(new mouseAction(i,j));
						}
					}
					button[3][2].setMarkPlace(true);
					button[5][4].setMarkPlace(true);
					button[2][3].setMarkPlace(true);
					button[4][5].setMarkPlace(true);
					othello.setTable(3, 2, 'M');
					othello.setTable(5, 4, 'M');
					othello.setTable(2, 3, 'M');
					othello.setTable(4, 5, 'M');
					othello.setEat(true);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if( firstPlayer.getID() == 0 ){
						firstPlayer.execute( );
						currentTurn.setPiece('X');
					}
					currentTurn.repaint();
					resetMark();
				}
			}).start();
		}
	}
	
	class mouseAction extends MouseAdapter implements MouseListener{

		private int x;
		private int y;
		public mouseAction(int x,int y)
		{
			this.x = x;
			this.y = y;
		}
		public void mouseEntered(MouseEvent e)
		{
			if( othello.isTiming() )								return;
			if( othello.getIdPlayer() == 1 ){
				if( othello.getTurn() == 1 )						firstPlayer.Mark(x, y, "Flip");
				else if( othello.getTurn() == 2 )					secondPlayer.Mark(x, y, "Flip");
			}
		}
		public void mouseExited(MouseEvent e)
		{
			if( othello.isTiming() )								return;
			if( othello.getIdPlayer() == 1 ){
				if( othello.getTurn() == 1 )						firstPlayer.Mark(x, y, "Flip");
				else if( othello.getTurn() == 2 )					secondPlayer.Mark(x, y, "Flip");
			}
		}
		public void mouseClicked(MouseEvent e) { 
			
			//the first button (left)
			//the second button (center)
			//the third button (right)
			System.out.println("MOUSE BUTTON: "+e.getButton());
			if( e.getButton() == MouseEvent.BUTTON1 ){
				if( othello.isTiming() || !button[x][y].isMarkPlace() )	return;
				if( othello.isGameOver() )								return;
				if( othello.getTurn() == 1 )
				{
					
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							execute(firstPlayer);
							record();
							if( othello.getIdPlayer() == 0 )			execute(secondPlayer);
							record();
						}
					}).start();
				}
				else if( othello.getTurn() == 2 ){
					
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							execute(secondPlayer);
							record();
							if( othello.getIdPlayer() == 0 )			execute(firstPlayer);
							record();
						}
					}).start();
				}
			}
		}
		// execute by player
		public void execute(IPlayer Player)
		{
			Player.setPoint(x, y);
			Player.execute( );
			currentTurn.setPiece(othello.getPiece());
			currentTurn.repaint();
		}
		// update the number of piece to display
		public void record()
		{
			blackLabel.setText("Black: "+othello.getNumBlackPiece());
			whiteLabel.setText("White: "+othello.getNumWhitePiece());
		}
	}
}
