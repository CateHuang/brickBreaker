package brickBreaker;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame obj = new JFrame();
		GamePlay gamePlay = new GamePlay();
		obj.setBounds(10, 10, 700, 600);
		obj.setResizable(false);
		obj.setTitle("Breakout Ball");
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//EXIT_ON_CLOSE, DISPOSE_ON_CLOSE, HIDE_ON_CLOSE
		obj.setVisible(true);
		obj.add(gamePlay);

		
		
	}

}

/*	Create a window for game
 *	Create a class named GamepPlay extends JFrame, ActionListener, KeyListener
 *		set the colors, positions for regions - background, border for brick, paddle, ball
 *		deal with the motion for ball moving and timer
 *		set KeyListener
 *		define intersects for reflection
 *	Create a map to generate brick - draw
 *	Add scores, game start and over
 */
