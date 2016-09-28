package blocks;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.QuadCurve2D;

/**
 * This is a class that represents the Player.
 * It extends block so that it can be painted onto the
 * screen.
 */
public class Player extends Block {

	/**
	 * Very similar to the mho, but the colors
	 * are changed to represent the player.
	 */
	@Override
	public void fillInBlock(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.ORANGE);
		g.drawOval(7, 7, 36, 36);
		
		// Draw the eyes
		g.drawOval(13, 15, 11, 11);
		
		g.drawOval(26, 15, 11, 11);
		
		// Only g2d allows for line drawing
		Graphics2D lineDrawer = (Graphics2D) g;
		QuadCurve2D.Double curve = new QuadCurve2D.Double(15,30,25,40,35,30);
		lineDrawer.draw(curve);
	}

}
