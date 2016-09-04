import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.BorderLayout;

public class GameOverFrame extends JFrame {
	
	public GameOverFrame() {
		this.init();
	}
	
	public void init() {
		setSize(WIDTH, HEIGHT);
		this.setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setTitle("Game Over");
		setResizable(false);
	}
	
	@Override
	public void paint(Graphics g) {
		this.addUIElements(g);
	}
	
	public void exit() {
		this.setVisible(false);
	}
	
	public void playAgain() {
		this.setVisible(false);
		Container container = new Container();
		container.setVisible(true);
	}
	
	public void addUIElements(Graphics g) {
		g.setColor(Color.RED);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
		g.drawString("GAME OVER", 200, 200);
		
		
		JButton exitButton = new JButton("Exit");
		exitButton.setLayout(null);
		exitButton.setBounds(60, 400, 220, 30);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit",
				        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {
					//Exit this JFrame
					exit();
				}
	         }
		});
		

		JButton playAgainButton = new JButton("Play Again");
		playAgainButton.setLayout(null);
		playAgainButton.setBounds(400, 400, 220, 30);
		playAgainButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Close this JFrame and open the Container Frame
				playAgain();
	         }
		});
		
		JLabel label = new JLabel("Game Over", JLabel.CENTER);
		//label.setLayout(null);
		label.setBounds(500, 200, 220, 30);
		label.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
		label.setForeground(Color.RED);
		
		JButton placeholder = new JButton();
		placeholder.setVisible(false);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(exitButton,BorderLayout.SOUTH);
		panel.add(playAgainButton,BorderLayout.SOUTH);
		panel.add(placeholder,BorderLayout.SOUTH);
		panel.add(label, BorderLayout.PAGE_START);
		panel.setBackground(Color.BLACK);
		this.add(panel);
		
		this.revalidate();
	}
}
