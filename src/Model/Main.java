package Model;

import View.Play;


public class Main {

  	/**
	 * Illustrating Artificial Intelligence and Software Engineering Concepts through Othello
	 * Main Class
	 * @author jompon
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Othello othello = Othello.getInstance();
		Play play = new Play(othello);
		play.run();
	}

}
