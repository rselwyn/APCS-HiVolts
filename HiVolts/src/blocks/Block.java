package blocks;

import java.awt.Graphics;

import javax.swing.JComponent;

public abstract class Block extends JComponent {
	
	public int WIDTH = 50;
	public int HEIGHT = 50;
	
	public abstract void fillInBlock(Graphics g);
	
	public Block() {
		this.repaint();
	}
	
}
