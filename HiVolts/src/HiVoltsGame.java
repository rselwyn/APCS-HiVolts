import frames.WelcomeFrame;

/**
 * Main Class.  See documentation for the main method.
 */
public class HiVoltsGame {
	
	/**
	 * Main link to the program.  Instantiates the 
	 * welcome frame, sets its size, and makes it visible.
	 * @param args
	 */
	public static void main(String[] args) {
		WelcomeFrame frame = new WelcomeFrame();
		frame.setSize(700, 700);
		frame.setVisible(true);
	}
}