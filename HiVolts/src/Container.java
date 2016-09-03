import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;

import blocks.Blank;
import blocks.Block;
import blocks.ElectricFence;
import blocks.Mho;
import blocks.Player;

public class Container extends JFrame {
	
	public final int WIDTH = 700;
	public final int HEIGHT = 700;
	
	public final int NUM_COLUMNS = 12;
	public final int NUM_ROWS = 12;
	
	private Block[][] gameBlocks = new Block[12][12];
	
	public Container() {
		this.initJFrameMethods();
		this.fillInBoard();
		
		for (Block y[] : gameBlocks) {
			for (Block x : y) {
				this.add(x);
			}
		}
	}
	
	public void initJFrameMethods() {
		setSize(WIDTH, HEIGHT);
		this.getContentPane().setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setTitle("HiVolts");
		setResizable(false);
		
		GridLayout grid = new GridLayout();
		grid.setColumns(NUM_COLUMNS);
		grid.setRows(NUM_ROWS);
		
		this.setLayout(grid);
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

		this.gameBlocks[3][1] = new Player();
		
	}
	
	
}
