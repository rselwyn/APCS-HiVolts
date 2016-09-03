import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;

import blocks.Block;
import blocks.ElectricFence;
import blocks.Mho;

public class Container extends JFrame {
	
	public final int WIDTH = 700;
	public final int HEIGHT = 700;
	
	public final int NUM_COLUMNS = 12;
	public final int NUM_ROWS = 12;
	
	private Block[][] gameBlocks = new Block[12][12];
	
	public Container() {
		this.initJFrameMethods();
		
		for (int i = 0; i<144; i++) {
			if (i%2==0) this.add(new Mho());
			else this.add(new ElectricFence());
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
	
	
	
}
