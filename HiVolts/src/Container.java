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

	@Override
	public void keyPressed(KeyEvent e) {
		// Q
		if (e.getKeyCode() == 81) {
			this.movePlayer(-1, -1);
		}
		// W
		else if (e.getKeyCode() == 87) {
			this.movePlayer(0, -1);
		}
		// E
		else if (e.getKeyCode() == 69) {
			this.movePlayer(1, -1);
		}
		// A
		else if (e.getKeyCode() == 65) {
			this.movePlayer(-1, 0);
		}
		// S: Player does not move
		//This else clause should not be included
		else if (e.getKeyCode() == 83) {
			
		}
		// D
		else if (e.getKeyCode() == 68) {
			this.movePlayer(1, 0);
		}
		// Z
		else if (e.getKeyCode() == 90) {
			this.movePlayer(-1, 1);
		}
		// X
		else if (e.getKeyCode() == 88) {
			this.movePlayer(0, 1);
		}
		// C
		else if (e.getKeyCode() == 67) {
			this.movePlayer(1, 1);
		}
		// J
		else if (e.getKeyCode() == 74) {
			
		}
		else {
			System.out.println("Unknown key");
			System.out.println(e.getKeyCode());
		}
 	}

	public void movePlayer(int x, int y) {
		gameBlocks[GlobalReferences.PLAYER_POSITION[1]][GlobalReferences.PLAYER_POSITION[0]] = new Blank();
		gameBlocks[GlobalReferences.PLAYER_POSITION[1] + y][GlobalReferences.PLAYER_POSITION[0] + x] = new Player();
		GlobalReferences.PLAYER_POSITION[1] += y;
		GlobalReferences.PLAYER_POSITION[0] += x;
		this.drawElements();
		this.repaint();
	}
	
	// Unused methods
	public void keyReleased(KeyEvent e) {}
	
	public void keyTyped(KeyEvent e) {}
	
}
