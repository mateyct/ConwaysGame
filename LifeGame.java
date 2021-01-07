import javax.swing.JFrame;

/**
 * Creates the window for the game of life simulation
 * @author Mason Tolley
 *
 */
public class LifeGame {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Conway's Game of Life");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new LifeGamePanel(600, 50));
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}

}
