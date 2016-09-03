package blocks;

import java.awt.Color;
import java.awt.Graphics;

public class ElectricFence extends Block {

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
