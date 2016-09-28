package frames;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;

import blocks.Blank;
import blocks.Block;
import blocks.ElectricFence;
import util.GlobalReferences;

import java.awt.event.*;
import javax.swing.JLabel;

/**
 * This class is the blueprint for the frame that is displayed when a game is complete. 
 * This class extends JFrame.
 */
public class GameOverFrame extends JFrame {
	public final int NUM_COLUMNS = 12;
	public final int NUM_ROWS = 12;

	private Block[][] gameBlocks = new Block[NUM_COLUMNS][NUM_ROWS];
	private JPanel currentlyDisplayed = new JPanel();
	
	private CustomButton playBtn;
	private CustomButton exitBtn;
	
	public int gameStatus;
	
	/**
	 * Constructs an instance of GameOverFrame, and sets the gameStatus property
	 * based on the result of the previous game. 
	 * @param gameStatus
	 */
	public GameOverFrame(int gameStatus) {
		this.gameStatus = gameStatus;
		this.init();
	}
	
	/**
	 * Calls the methods inherited from the JFrame
	 * superclass.
	 */
	public void init() {
		this.setSize(WIDTH, HEIGHT);
		this.setBackground(Color.BLACK);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setTitle("Welcome");
		this.setResizable(false);
		
		this.currentlyDisplayed.setBackground(Color.BLACK);
		this.createFence();
		this.drawElements();	
		this.addMouseListener();
	}
	
	/**
	 * Creates a series of 48 fences along the border of the frame. 
	 */
	public void createFence() {
		for (int x = 0; x < gameBlocks.length; x++) {
			for (int y = 0; y < gameBlocks[0].length; y++) {
				this.gameBlocks[y][x] = new Blank();
			}
		}
		
		for (int i = 0; i < gameBlocks.length; i++) {
			// Draw on the left side
			this.gameBlocks[i][0] = new ElectricFence();
			// Draw on the right side
			this.gameBlocks[i][gameBlocks[0].length-1] = new ElectricFence();
		}
		
		for (int i = 0; i < gameBlocks[0].length; i++) {
			// Draw on the top side
			this.gameBlocks[0][i] = new ElectricFence();
			// Draw on the bottom side
			this.gameBlocks[gameBlocks.length-1][i] = new ElectricFence();
		}
	}
	
	/**
	 * Draws the blocks
	 */
	public void drawElements() {
		GridLayout grid = new GridLayout();
		grid.setColumns(NUM_COLUMNS);
		grid.setRows(NUM_ROWS);
		
		this.currentlyDisplayed.removeAll();
		this.currentlyDisplayed.revalidate();
		
		this.currentlyDisplayed.setLayout(grid);
		
		for (Block y[] : gameBlocks) {
			for (Block x : y) {
				this.currentlyDisplayed.add(x);
			}
		}
		
		this.add(this.currentlyDisplayed);
	}
	
	/**
	 * Draws the welcome label
	 * @param g: the graphics object
	 */
	public void drawWelcomeLabel(Graphics g) {
		JLabel welcomeLabel = new JLabel();
		
		if (this.gameStatus == 0) {
			if (GlobalReferences.NUM_MOVES!=1) {
				welcomeLabel = new JLabel("You Lost in " + GlobalReferences.NUM_MOVES + " Moves!", JLabel.CENTER);
			}
			else {
				welcomeLabel = new JLabel("You Lost in " + GlobalReferences.NUM_MOVES + " Move!", JLabel.CENTER);
			}
		} else {
			if (GlobalReferences.NUM_MOVES!=1) {
				welcomeLabel = new JLabel("You Won in " + GlobalReferences.NUM_MOVES + " Moves!", JLabel.CENTER);
			}
			else {
				welcomeLabel = new JLabel("You Won in " + GlobalReferences.NUM_MOVES + " Move!", JLabel.CENTER);
			}
		}
		
		// Reset the NUM_MOVES variable
		GlobalReferences.NUM_MOVES = 0;
		
		welcomeLabel.setLayout(null);
		welcomeLabel.setBounds(500, 200, 220, 30);
		welcomeLabel.setFont(new Font("GillSans", Font.PLAIN, 50)); 
		welcomeLabel.setForeground(Color.ORANGE);
				
		this.playBtn = new CustomButton("Play!", Color.ORANGE, 150, 50, 100, 500);
		this.exitBtn = new CustomButton("Exit", Color.ORANGE, 150, 50, 450, 500);
		
		this.add(this.playBtn);
		this.revalidate();
		this.add(this.exitBtn);
		this.revalidate();
		this.add(welcomeLabel);
		this.revalidate();
	}
	
	/**
	 * Draws the elements
	 */
	@Override
	public void paint(Graphics g) {
		this.drawWelcomeLabel(g);
		this.drawElements();
		
	}
	
	/**
	 * This method implements the Mouse Listener
	 */
	public void addMouseListener() {
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				int widthOfBoth = 150;
				int heightOfBoth = 50;
				// Play button
				if (e.getX() < widthOfBoth+100 && e.getX() > 100) {
					if (e.getY() < heightOfBoth + 525 && e.getY() > 525) {
						playOnClick();
					}
				}
				
				// Exit Button
				if (e.getX() < widthOfBoth+450 && e.getX() > 450) {
					if (e.getY() < heightOfBoth + 525 && e.getY() > 525) {
						exitOnClick();
					}
				}
			}
		});
	}
	
	/**
	 * Starts the game
	 */
	public void playOnClick() {
		HiVoltsScreen c = new HiVoltsScreen();
		c.setVisible(true);
		this.setVisible(false);
	}
	
	/**
	 * Exits the game
	 */
	public void exitOnClick() {
		System.out.println("F");
		this.setVisible(false);
		System.exit(1);
	}
}