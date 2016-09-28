package blocks;

import java.awt.Graphics;

/**
 * Blank block to be painted into the board.  This extends
 * Block so that it can be drawn into the grid.
 */
public class Blank extends Block {

	/**
	 * Because this is a Blank block, it
	 * does not fill in the block, and leaves
	 * it blank.
	 */
	@Override
	public void fillInBlock(Graphics g) {
		// TODO Auto-generated method stub

	}

}
