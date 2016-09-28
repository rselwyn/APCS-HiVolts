package blocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.QuadCurve2D;

/**
 * Class that represents a Mho.  This extends block
 * so that it can be drawn onto the grid.
 */
public class Mho extends Block {

	/**
	 * Draws a mho.  Also uses QuadCurve2D
	 * to be able to draw the curved mho face.
	 */
	@Override
	public void fillInBlock(Graphics g) {
		// Draw the mho
		g.setColor(Color.ORANGE);
		g.fillOval(7, 7, 36, 36);
		
		// Draw the eyes
		g.setColor(Color.BLACK);
		g.fillOval(13, 15, 11, 11);
		
		g.setColor(Color.BLACK);
		g.fillOval(26, 15, 11, 11);
		
		// Only g2d allows for line drawing
		Graphics2D lineDrawer = (Graphics2D) g;
		QuadCurve2D.Double curve = new QuadCurve2D.Double(15,35,25,25,35,35);
		lineDrawer.draw(curve);
	}
	
}
