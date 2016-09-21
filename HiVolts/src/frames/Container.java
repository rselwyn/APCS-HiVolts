package frames;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
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

public class Container extends JFrame implements KeyListener {
	
	public final int WIDTH = 700;
	public final int HEIGHT = 700;
	
	public final int NUM_COLUMNS = 12;
	public final int NUM_ROWS = 12;
	
	private Block[][] gameBlocks = new Block[NUM_COLUMNS][NUM_ROWS];
	private JPanel currentlyDisplayed = new JPanel();
	
	public Container() {
		this.initJFrameMethods();
		this.fillInBoard();
		this.drawElements();
	}
	
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
	
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println(e.getKeyCode());
		switch (e.getKeyCode()) {
		case 81:
			//Q
			this.movePlayer(-1, -1);		
			break;
		case 87:
			//W
			this.movePlayer(0, -1);	
			break;
		case 37:
			// Left arrow
			this.movePlayer(-1,0);
			break;
		case 38:
			// up
			this.movePlayer(0, -1);
			break;
		case 39:
			// Right arrow
			this.movePlayer(1, 0);
			break;
		case 40:
			// Down
			this.movePlayer(0, 1);
			break;
		case 69:
			//E
			this.movePlayer(1, -1);
			break;
		case 65:
			//A
			this.movePlayer(-1, 0);		
			break;
		case 83:
			//S		
			this.movePlayer(0, 0);
			break;
		case 68:
			//D
			this.movePlayer(1, 0);
			break;
		case 90:
			//Z
			this.movePlayer(-1, 1);
			break;
		case 88:
			//X
			this.movePlayer(0, 1);
			break;
		case 67:
			//C
			this.movePlayer(1, 1);
			break;
		case 74:
			this.jump();
			return;
		default:
			System.out.println(e.getKeyCode());
			return;
		}

		this.moveMhos();
 	}
	
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

		this.drawElements();
		this.repaint();
	}
	
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
	
	public void gameOver() {
		this.drawGameOverElements();
	}
	
	public void drawGameOverElements() {
		GameOverFrame frame = new GameOverFrame(0);
		frame.setSize(WIDTH, HEIGHT);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setVisible(true);
		this.setVisible(false);
		this.dispose();
	}
	
	public void won() {
		this.drawWinElements();
	}
	
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
