package blocks;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Mho extends Block {

	@Override
	public void fillInBlock(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void paint(Graphics g) {
		Random r = new Random();
		int rr = r.nextInt(255);
		int gg = r.nextInt(255);
		int bb = r.nextInt(255);
		Color c = new Color(rr, gg, bb);
		
		g.setColor(c);
		g.fillRect(0, 0, 100, 100);
	}
	
}
