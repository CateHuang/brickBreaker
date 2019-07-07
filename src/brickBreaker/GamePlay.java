package brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
	private Timer timer;
	private boolean play = false;
	private int delay = 8;
	
	private int playerX;
	private int ballPosX;
	private int ballPosY;
	private int ballXDir;
	private int ballYDir;
	private int score;
	private int totalBricks;
	
	private int randomNumber; 
	
	private MapGenerator map;
	
	/* setFocusable mainly used for enable/disable view's focus event 
	 * on both touch mode and keypad mode( using up/down/next key).
	 * 
	 * setFocusTraversalKeysEnabled() decides whether or not focus traversal keys (TAB key, SHIFT+TAB, etc.)
	*/
	public GamePlay() {
		initGame();
		addKeyListener(this);
		setFocusable(true);			
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void generateNumber( int n ) {
		Random r = new Random();
		randomNumber = r.nextInt((n)+1);
	}
	
	public void initGame() {
		play = true;
		generateNumber(692);
		ballPosX = randomNumber;
		ballPosY = 300;
		generateNumber(5);
		ballXDir = randomNumber;
		ballYDir = 2;
		generateNumber(592);
		playerX = randomNumber;
		score = 0;
		totalBricks = 21;
		map = new MapGenerator(5, 7);
		
		repaint();
	}
	
	public void paint(Graphics g) {
		//background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		//borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		// drawing map
		map.draw((Graphics2D)g);
		
		// score
		g.setColor(Color.white);
		g.setFont(new Font("Serif", Font.BOLD, 25));
		g.drawString("" + score, 590, 30);
		
		//the paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		//the ball
		g.setColor(Color.yellow);
		g.fillOval(ballPosX, ballPosY, 20, 20);
		
		if ( totalBricks <= 0 ) {
			play = false;
			ballXDir = 0;
			ballYDir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("Serif", Font.BOLD, 30));
			g.drawString("You Won, Scores: " + score, 190, 300);
			
			g.setFont(new Font("Serif", Font.BOLD, 20));
			g.drawString("Press Enter to restart", 250, 350);			
			
		}
		
		if ( ballPosY > 550 ) {
			play = false;
			ballXDir = 0;
			ballYDir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("Serif", Font.BOLD, 30));
			g.drawString("Game Over, Scores: " + score, 190, 300);
			
			g.setFont(new Font("Serif", Font.BOLD, 20));
			g.drawString("Press Enter to restart", 250, 350);
		}
		
		g.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		
		if ( play ) {
			ballPosX += ballXDir;
			ballPosY += ballYDir;
		
			if ( new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8)) ) {
				ballYDir = -ballYDir;
			}
			
			// (MpGenerator map).map = new int[i][j]
			A: for ( int i = 0; i < map.map.length; i++ ) {
				for ( int j = 0; j < map.map[0].length; j++ ) {
					if ( map.map[i][j] > 0 ) {
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
						Rectangle brickRect = rect;
						
						if ( ballRect.intersects(brickRect) ) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;
						
							if ( ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width ) {
								ballXDir = -ballXDir;
							} else {
								ballYDir = -ballYDir;
							}
						
							break A;
						}	
					}
				}
				
			}
			
			
			
			if ( ballPosX < 0 ) {
				ballXDir = -ballXDir;
			}
			if ( ballPosY < 0 ) {
				ballYDir = -ballYDir;
			}
			if( ballPosX > 670) {
				ballXDir = -ballXDir;
			}
		}
		
		repaint();
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if ( e.getKeyCode() == KeyEvent.VK_RIGHT ) {
				if ( playerX >= 600 ) {
					playerX = 600;
				} else {
					moveRight();
				}
		}		
		if ( e.getKeyCode() == KeyEvent.VK_LEFT ) {
			if ( playerX < 10 ) {
				playerX = 10;
			} else {
				moveLeft();
			}			
		}
		if ( e.getKeyCode() == KeyEvent.VK_ENTER ) {
			if ( !play ) {
				initGame();
			}	
		}	
	}
	
	public void moveRight() {
		play = true;
		playerX += 20;
	}
	public void moveLeft() {
		play = true;
		playerX -= 20;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

}
