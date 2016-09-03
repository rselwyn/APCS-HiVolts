package blocks;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public abstract class Block extends JComponent {
	
	public int WIDTH = 50;
	public int HEIGHT = 50;
	
	public abstract void fillInBlock(Graphics g);
	
	public Block() {
		this.repaint();
	}
	
	/**
	 * Paint method that calls the method fillInBlock.  This is 
	 * final because it should not be overridden.  YOu should implement
	 * fillInBlock().
	 */
	@Override
	public final void paint(Graphics g) {
		// Draw the background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		this.fillInBlock(g);
	}
	
}
