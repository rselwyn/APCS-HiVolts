package blocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.QuadCurve2D;

public class Mho extends Block {

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
