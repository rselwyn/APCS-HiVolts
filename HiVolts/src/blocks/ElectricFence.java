package blocks;

import java.awt.Color;
import java.awt.Graphics;

/**
 * ElectricFence block on the screen.  This
 * extends Block so that it can be drawn onto the
 * main screen.
 */
public class ElectricFence extends Block {

	/**
	 * Fills in the block with a fence shape
	 */
	@Override
	public void fillInBlock(Graphics g) {
		// Draw the fence side posts
		g.setColor(Color.ORANGE);
		
		g.drawLine(10, 10, 10, 40);
		g.drawLine(40, 10, 40, 40);
		
		// Draw the main links
		g.drawLine(10, 15, 40, 15);
		g.drawLine(10, 25, 40, 25);
		g.drawLine(10, 35, 40, 35);
	}

}
