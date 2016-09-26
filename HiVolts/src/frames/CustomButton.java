package frames;
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class CustomButton extends JComponent {
	
	private String btnText;
	private Color color;
	private int width;
	private int height;
	private int xVal;
	private int yVal;
	
	/**
	 * Constructs the custom button class.
	 * @param btnText: the text for the button
	 * @param color: the button color
	 * @param width: the button width
	 * @param height: the button height
	 * @param xVal: the x position
	 * @param yVal: the y position
	 */
	public CustomButton(String btnText, Color color, int width, int height, int xVal, int yVal) {
		this.btnText = btnText;
		this.color = color;
		this.width = width;
		this.height = height;
		this.xVal = xVal;
		this.yVal = yVal;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(this.color);
		g.fillRoundRect(this.xVal, this.yVal, this.width, this.height, 10, 10);
		g.setColor(Color.WHITE);
		g.setFont(new Font("GillSans", Font.PLAIN, 30)); 
		g.drawString(this.btnText, this.xVal + 50, this.yVal + 35);
	}
	
	/**
	 * Gets the x position
	 * @return the x position
	 */
	public int getXVal() {
		return this.getXVal();
	}
	
	/**
	 * Gets the y position
	 * @return the y position
	 */
	public int getYVal() {
		return this.getYVal();
	}
	
	/**
	 * Gets the button width
	 * @return the button width
	 */
	public int getBtnWidth() {
		return this.width;
	}
}