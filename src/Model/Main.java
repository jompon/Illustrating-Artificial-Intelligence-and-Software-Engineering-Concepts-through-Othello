package Model;

import View.Play;


public class Main {

  	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Othello othello = Othello.getInstance();
		Play play = new Play(othello);
		play.run();
	}

}
