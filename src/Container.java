import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
		
		// Add 20 random electric fences
		for (int i = 0; i < 20; i++) {
			int[] position = getUntakenPoint();
			this.gameBlocks[position[1]][position[0]] = new ElectricFence();
		}
		
		int[] playerPosition = getUntakenPoint();
		this.gameBlocks[playerPosition[1]][playerPosition[0]] = new Player();
		GlobalReferences.PLAYER_POSITION = new int[]{playerPosition[0], playerPosition[1]};
		for (int i = 0; i < 12; i++) {
			int[] mhoPos = getUntakenPoint();
			this.gameBlocks[mhoPos[1]][mhoPos[0]] = new Mho();
		}
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
	
	private int[] getRandomPoint() {
		int x_rand = 0;
		int y_rand = 0;
			
		Random r = new Random();
		x_rand = r.nextInt(NUM_ROWS);
		y_rand = r.nextInt(NUM_COLUMNS);
		
		return new int[]{x_rand,y_rand};
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case 81:
			//Q
			this.movePlayer(-1, -1);
			this.moveMhos();
			break;
		case 87:
			//W
			this.movePlayer(0, -1);
			this.moveMhos();
			break;
		case 69:
			//E
			this.movePlayer(1, -1);
			this.moveMhos();
			break;
		case 65:
			//A
			this.movePlayer(-1, 0);
			this.moveMhos();
			break;
		case 83:
			//S
			this.moveMhos();
			break;
		case 68:
			//D
			this.movePlayer(1, 0);
			this.moveMhos();
			break;
		case 90:
			//Z
			this.movePlayer(-1, 1);
			this.moveMhos();
			break;
		case 88:
			//X
			this.movePlayer(0, 1);
			this.moveMhos();
			break;
		case 67:
			//C
			this.movePlayer(1, 1);
			this.moveMhos();
			break;
		case 74:
			//J
			break;
		default:
			System.out.println("Unknown key");
			System.out.println(e.getKeyCode());
			break;
		}
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
					this.gameBlocks[i][j] = new Blank();
				}
			}
		}
		
		for (int i = 0; i < 12; i++) {
			//Used the getRandomPoint method, because the Mhos cannot just go into an empty space. They also have a chance of hitting a fence.
			int[] mhoPos = getRandomPoint();
			if (this.gameBlocks[mhoPos[1]][mhoPos[0]] instanceof ElectricFence) {
				//An Mho collided with a fence
				this.gameBlocks[mhoPos[1]][mhoPos[0]] = new ElectricFence();
			} else if (this.gameBlocks[mhoPos[1]][mhoPos[0]] instanceof Player) {
				//An Mho collided with the player
				this.gameOver();
			} else if (this.gameBlocks[mhoPos[1]][mhoPos[0]] instanceof Blank) {
				//An Mho went to blank spot
				this.gameBlocks[mhoPos[1]][mhoPos[0]] = new Mho();
			} else {
				//An Mho collided with another Mho
				this.gameBlocks[mhoPos[1]][mhoPos[0]] = new Blank();
			}
		}
		
		if (checkForMhos()) {
			this.won();
		} else {
			this.drawElements();
			this.repaint();
		}
	}
	
	public boolean checkForMhos() {
		int mhoCount = 0;
		for (int i = 0; i < this.gameBlocks.length; i++) {
			for (int j = 0; j < this.gameBlocks[i].length; j++) {
				if (this.gameBlocks[i][j] instanceof Mho == false) {
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
		System.out.println("Game Over");
		this.drawGameOverElements();
	}
	
	public void drawGameOverElements() {
		GameOverFrame frame = new GameOverFrame();
		frame.setSize(WIDTH, HEIGHT);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setVisible(true);
		this.setVisible(false);
		this.dispose();
	}
	
	public void won() {
		System.out.println("You've won!");
	}
	
	// Unused methods
	public void keyReleased(KeyEvent e) {}
	
	public void keyTyped(KeyEvent e) {}
	
}
