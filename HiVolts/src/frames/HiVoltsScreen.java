package frames;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.GlobalReferences;
import blocks.Blank;
import blocks.Block;
import blocks.ElectricFence;
import blocks.Mho;
import blocks.Player;

/**
 * This class is the main container of the game, and extends JFrame.
 * In addition, this class implements the KeyListener interface, 
 * which allows us to access various methods involved in the user's keystroke.
 */
public class HiVoltsScreen extends JFrame implements KeyListener {
	
	public final int WIDTH = 700;
	public final int HEIGHT = 700;
	
	public final int NUM_COLUMNS = 12;
	public final int NUM_ROWS = 12;
	
	private Block[][] gameBlocks = new Block[NUM_COLUMNS][NUM_ROWS];
	private JPanel currentlyDisplayed = new JPanel();
	
	/**
	 * Constructs an instance of HiVoltsScreen and calls three various methods 
	 * that are essential for displaying the initial game board.
	 */
	public HiVoltsScreen() {
		this.initJFrameMethods();
		this.fillInBoard();
		this.drawElements();
	}
	
	/**
	 * Sets and calls the various JFrame methods, and is called in the constructor.
	 */
	public void initJFrameMethods() {
		setSize(WIDTH, HEIGHT);
		this.getContentPane().setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setTitle("HiVolts");
		setResizable(false);
		addKeyListener(this);
		this.currentlyDisplayed.setBackground(Color.BLACK);
	}
	
	/**
	 * Interacts with the gameBlocks 2d array.
	 */
	private void fillInBoard() {
		// Initialize everything with blank blocks
		for (int x = 0; x < gameBlocks.length; x++) {
			for (int y = 0; y < gameBlocks[0].length; y++) {
				this.gameBlocks[y][x] = new Blank();
			}
		}
		
		// Draw the outside fences
		for (int i = 0; i < gameBlocks.length; i++) {
			// Draw on the left side
			this.gameBlocks[i][0] = new ElectricFence();
			// Draw on the right side
			this.gameBlocks[i][gameBlocks[0].length-1] = new ElectricFence();
		}
		
		for (int i = 0; i < gameBlocks[0].length; i++) {
			// Draw on the top side
			this.gameBlocks[0][i] = new ElectricFence();
			// Draw on the bottom side
			this.gameBlocks[gameBlocks.length-1][i] = new ElectricFence();
		}
		
		Point[] setPoints = getOpenPoints(20);
		for (Point p : setPoints) {
			this.gameBlocks[p.x][p.y] = new ElectricFence();
		}
		
		int[] playerPosition = getUntakenPoint();
		this.gameBlocks[playerPosition[1]][playerPosition[0]] = new Player();
		GlobalReferences.PLAYER_POSITION = new int[]{playerPosition[0], playerPosition[1]};
		for (int i = 0; i < 12; i++) {
			int[] mhoPos = getUntakenPoint();
			this.gameBlocks[mhoPos[1]][mhoPos[0]] = new Mho();
		}
	}
	

	/**
	 * Efficient algorithm explanation:
	 * 
	 * This algorithm works as follows.  First, iterate over the grid
	 * and select all empty squares.  This step is O(n).  After that, perform a fisher-yates shuffle 
	 * over the array in O(n), and return the first "numberOf" positions.
	 * 
	 * @param numberOf:	number of points to return
	 * @return	randomly chosen points
	 */
	public Point[] getOpenPoints(int numberOf) {
		ArrayList<Point> pointsAvailable = new ArrayList<Point>();
		
		
		for (int i = 0; i<this.NUM_COLUMNS; i++) {
			for (int j = 0; j<this.NUM_ROWS; j++) {
				if(!(this.gameBlocks[i][j] instanceof ElectricFence)) {
					pointsAvailable.add(new Point(i,j));
				}
			}
		}
		
		// Fisher-Yates shuffle
		Collections.shuffle(pointsAvailable);
		return Arrays.copyOfRange(pointsAvailable.toArray(new Point[pointsAvailable.size()]),0,numberOf);
	}
	
	/**
	 * Draws the blocks that are in the array onto the screen.
	 */
	public void drawElements() {
		GridLayout grid = new GridLayout();
		grid.setColumns(NUM_COLUMNS);
		grid.setRows(NUM_ROWS);
		
		this.currentlyDisplayed.removeAll();
		this.currentlyDisplayed.revalidate();
		
		this.currentlyDisplayed.setLayout(grid);
		
		for (Block y[] : gameBlocks) {
			for (Block x : y) {
				currentlyDisplayed.add(x);
			}
		}
		
		this.add(currentlyDisplayed);
	}
	
	/**
	 * NOTE: RETURNS X,Y.  This needs to be changed to 
	 * Y,X when indexing.
	 */
	private int[] getUntakenPoint() {
		int x_rand = 0;
		int y_rand = 0;
		// Run until a blank square is found
		while (!(gameBlocks[y_rand][x_rand] instanceof Blank)) {
			Random r = new Random();
			x_rand = r.nextInt(NUM_ROWS);
			y_rand = r.nextInt(NUM_COLUMNS);
		}
		
		return new int[]{x_rand,y_rand};
	}

	/**
	 * NOTE: RETURNS X,Y.  This needs to be changed to 
	 * Y,X when indexing.
	 */
	private int[] getJumpablePoint() {
		int x_rand = 0;
		int y_rand = 0;
		// Run until a blank square is found
		while (!(gameBlocks[y_rand][x_rand] instanceof Blank) && !(gameBlocks[y_rand][x_rand] instanceof Mho)) {
			Random r = new Random();
			x_rand = r.nextInt(NUM_ROWS);
			y_rand = r.nextInt(NUM_COLUMNS);
		}
		
		return new int[]{x_rand,y_rand};
	}
	
	/**
	 * Handles key presses 
	 * @param e: the key press event
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e.getKeyCode());
		switch (e.getKeyCode()) {
		case 81:
			//Q
			GlobalReferences.NUM_MOVES++;
			this.movePlayer(-1, -1);		
			break;
		case 87:
			//W
			GlobalReferences.NUM_MOVES++;
			this.movePlayer(0, -1);	
			break;
		case 37:
			// Left arrow
			GlobalReferences.NUM_MOVES++;
			this.movePlayer(-1,0);
			break;
		case 38:
			// up
			GlobalReferences.NUM_MOVES++;
			this.movePlayer(0, -1);
			break;
		case 39:
			// Right arrow
			GlobalReferences.NUM_MOVES++;
			this.movePlayer(1, 0);
			break;
		case 40:
			// Down
			GlobalReferences.NUM_MOVES++;
			this.movePlayer(0, 1);
			break;
		case 69:
			//E
			GlobalReferences.NUM_MOVES++;
			this.movePlayer(1, -1);
			break;
		case 65:
			//A
			GlobalReferences.NUM_MOVES++;
			this.movePlayer(-1, 0);		
			break;
		case 83:
			//S
			this.movePlayer(0, 0);
			break;
		case 68:
			//D
			GlobalReferences.NUM_MOVES++;
			this.movePlayer(1, 0);
			break;
		case 90:
			//Z
			GlobalReferences.NUM_MOVES++;
			this.movePlayer(-1, 1);
			break;
		case 88:
			//X
			GlobalReferences.NUM_MOVES++;
			this.movePlayer(0, 1);
			break;
		case 67:
			//C
			GlobalReferences.NUM_MOVES++;
			this.movePlayer(1, 1);
			break;
		case 74:
			this.jump();
			return;
		default:
			System.out.println(e.getKeyCode());
			return;
		}
		// move the mhos at the end
		this.moveMhos();
 	}
	
	/**
	 * Chooses a random point and jumps to it.
	 */
	public void jump() {
		this.gameBlocks[GlobalReferences.PLAYER_POSITION[1]][GlobalReferences.PLAYER_POSITION[0]] = new Blank();
		int[] newPosition = this.getJumpablePoint();
		if (this.gameBlocks[newPosition[1]][newPosition[0]] instanceof Mho) {
			this.gameOver();
		}
		this.gameBlocks[newPosition[1]][newPosition[0]] = new Player();
		GlobalReferences.PLAYER_POSITION[1] = newPosition[1];
		GlobalReferences.PLAYER_POSITION[0] = newPosition[0];
		this.drawElements();
		this.repaint();
	}

	/**
	 * Moves the player in a given x and y direction
	 * @param x: how far in the x direction
	 * @param y: how far in the y direction
	 */
	public void movePlayer(int x, int y) {
		if (this.isOverlapping(x, y)) {
			this.gameOver();
		} else {
			gameBlocks[GlobalReferences.PLAYER_POSITION[1]][GlobalReferences.PLAYER_POSITION[0]] = new Blank();
			gameBlocks[GlobalReferences.PLAYER_POSITION[1] + y][GlobalReferences.PLAYER_POSITION[0] + x] = new Player();
			GlobalReferences.PLAYER_POSITION[1] += y;
			GlobalReferences.PLAYER_POSITION[0] += x;
			this.drawElements();
			this.repaint();
		}
	}
	
	/**
	 * Handles all of the mho movement.  While it might seem complicated at first,
	 * the algorithm is very simple:
	 * 
	 * First, move the mhos horizontally or vertically if they are in line with the player.
	 * Second, try to move the mho diagonally towards the player.
	 * Third, if that isn't possible, try and move horizontally or vertically towards
	 * the player.
	 */
	public void moveMhos() {
		for (int i = 0; i < this.gameBlocks.length; i++) {
			for (int j = 0; j < this.gameBlocks[i].length; j++) {
				if (this.gameBlocks[i][j] instanceof Mho) {
					// If they are vertically aligned
					if (j == GlobalReferences.PLAYER_POSITION[0]) {
						if (i > GlobalReferences.PLAYER_POSITION[1]) {
							// mho is lower on screen
							this.gameBlocks[i][j] = new Blank();
							if (this.gameBlocks[i-1][j] instanceof ElectricFence) {
								break;
							}
							this.gameBlocks[--i][j] = new Mho();
							i++;
						}
						else {
							System.out.println("Alternate");
							this.gameBlocks[i][j] = new Blank();
							if (this.gameBlocks[i+1][j] instanceof ElectricFence) {
								break;
							}
							this.gameBlocks[++i][j] = new Mho();
						}
						break; //we have made the move for the mho, now exit
					}
					// Horizontal alignment implemented here
					else if (i == GlobalReferences.PLAYER_POSITION[1]) {
						if (j > GlobalReferences.PLAYER_POSITION[0]) {
							// mho is lower on screen
							this.gameBlocks[i][j] = new Blank();
							if (this.gameBlocks[i][j-1] instanceof ElectricFence) {
								break;
							}
							this.gameBlocks[i][--j] = new Mho();
							i++;
						}
						else {
							System.out.println("Alternate");
							this.gameBlocks[i][j] = new Blank();
							if (this.gameBlocks[i][j+1] instanceof ElectricFence) {
								break;
							}
							this.gameBlocks[i][++j] = new Mho();
						}
						break; //we have made the move for the mho, now exit
					}
					// If not horizontal or vertical
					else {
						// Test for diagonal
						boolean can_go_a = !(this.gameBlocks[i-1][j-1] instanceof ElectricFence);
						boolean can_go_b = !(this.gameBlocks[i-1][j+1] instanceof ElectricFence);
						boolean can_go_c = !(this.gameBlocks[i+1][j-1] instanceof ElectricFence);
						boolean can_go_d = !(this.gameBlocks[i+1][j+1] instanceof ElectricFence);
						
						if (i > GlobalReferences.PLAYER_POSITION[1]) {
							if (j > GlobalReferences.PLAYER_POSITION[0]) {
								if (!can_go_a) {
									this.needsToMoveNotDiagonal(i, j);
									break;
								}
								// Move up left and up one
								this.gameBlocks[i][j] = new Blank();
								this.gameBlocks[--i][--j] = new Mho();
								System.out.println("A");
							}
							else {
								if (!can_go_b) {
									this.needsToMoveNotDiagonal(i, j);
									break;
								}
								this.gameBlocks[i][j] = new Blank();
								this.gameBlocks[--i][++j] = new Mho();
								System.out.println("B");
							}
						} 
						else {
							if (j > GlobalReferences.PLAYER_POSITION[0]) {
								if (!can_go_c) {
									this.needsToMoveNotDiagonal(i, j);
									break;
								}
								this.gameBlocks[i][j] = new Blank();
								this.gameBlocks[++i][--j] = new Mho();
								System.out.println("C");
							}
							else {
								if (!can_go_d) {
									this.needsToMoveNotDiagonal(i, j);
									break;
								}
								this.gameBlocks[i][j] = new Blank();
								this.gameBlocks[++i][++j] = new Mho();
								System.out.println("D");
							}
						}
					}
				}
			}
		}
		
		if (this.gameBlocks[GlobalReferences.PLAYER_POSITION[1]][GlobalReferences.PLAYER_POSITION[0]] instanceof Mho) {
			this.gameOver();
		}
		else if (this.checkForMhos()) {
			this.drawWinElements();
		}

		this.gameBlocks[GlobalReferences.PLAYER_POSITION[1]][GlobalReferences.PLAYER_POSITION[0]] = new Player();
		
		this.drawElements();
		this.repaint();
	}
	
	/**
	 * This function is called when the mho is 
	 * not in the same x or y and the mho 
	 * cannot move diagonally.
	 * @param i: the mho position x
	 * @param j: the mho position y
	 */
	public void needsToMoveNotDiagonal(int i, int j) {
		// Horizontal distance is greater than vertial distance
		int dHeight = Math.abs(GlobalReferences.PLAYER_POSITION[1] - i);
		int dWidth = Math.abs(GlobalReferences.PLAYER_POSITION[0] - j);
		
		// If width is greater than height
		if (dWidth > dHeight) {
			if (GlobalReferences.PLAYER_POSITION[1] >= i) {
				i--;
				if (!(this.gameBlocks[i][j] instanceof ElectricFence)) {
					this.gameBlocks[i][j] = new Mho();
				}
				this.gameBlocks[++i][j] = new Blank();
				System.out.println("A'");
			}
			else {
				i++;
				if (!(this.gameBlocks[i][j] instanceof ElectricFence)) {
					this.gameBlocks[i][j] = new Mho();
				}
				this.gameBlocks[--i][j] = new Blank();
				System.out.println("B'");
			}
		}
		else {
			// Height is greater than width
			if (GlobalReferences.PLAYER_POSITION[0] > j) {
				j++;
				if (!(this.gameBlocks[i][j] instanceof ElectricFence)) {
					this.gameBlocks[i][j] = new Mho();
				}
				this.gameBlocks[i][--j] = new Blank();
				System.out.println("C'");
			}
			else {
				j--;
				if (!(this.gameBlocks[i][j] instanceof ElectricFence)) {
					this.gameBlocks[i][j] = new Mho();
				}
				this.gameBlocks[i][++j] = new Blank();
				System.out.println("D'");
			}
		}
		this.repaint();
		this.drawElements();
	}
	
	/**
	 * Counts the number of mhos on the screen
	 * @return true if there are 0 mhos, else false
	 */
	public boolean checkForMhos() {
		int mhoCount = 0;
		for (int i = 0; i < this.gameBlocks.length; i++) {
			for (int j = 0; j < this.gameBlocks[i].length; j++) {
				if (this.gameBlocks[i][j] instanceof Mho == true) {
					mhoCount++;
				}
			}
		}
		if (mhoCount == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public boolean isOverlapping(int x, int y) {
		//Checks if there is an Mho in the current position of the player
		if (this.gameBlocks[GlobalReferences.PLAYER_POSITION[1] + y][GlobalReferences.PLAYER_POSITION[0] + x] instanceof Mho) {
			return true;
		} else if (this.gameBlocks[GlobalReferences.PLAYER_POSITION[1] + y][GlobalReferences.PLAYER_POSITION[0] + x] instanceof ElectricFence) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Called on game over
	 */
	public void gameOver() {
		this.drawGameOverElements();
	}
	
	/**
	 * Draws the game over elements
	 */
	public void drawGameOverElements() {
		GameOverFrame frame = new GameOverFrame(0);
		frame.setSize(WIDTH, HEIGHT);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setVisible(true);
		this.setVisible(false);
		this.dispose();
	}
	
	/**
	 * Draws the winning elements
	 */
	public void won() {
		this.drawWinElements();
	}
	
	/**
	 * Draws the winning elements
	 */
	public void drawWinElements() {
		GameOverFrame frame = new GameOverFrame(1);
		frame.setSize(WIDTH, HEIGHT);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setVisible(true);
		this.setVisible(false);
		this.dispose();
	}
	
	// Unused methods
	public void keyReleased(KeyEvent e) {}
	
	public void keyTyped(KeyEvent e) {}
}
