import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;

import blocks.Block;
import blocks.Mho;

public class Container extends JFrame {
	
	private Block[][] gameBlocks = new Block[12][12];
	
	public Container() {
		setSize(700, 700);
		setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setTitle("A Flag!");
		setResizable(false);
		
		GridLayout grid = new GridLayout();
		grid.setColumns(12);
		grid.setRows(12);
		
		this.setLayout(grid);
		
		for (int i = 0; i<140; i++) {
			this.add(new Mho());
		}
	}
	
	
	
}
