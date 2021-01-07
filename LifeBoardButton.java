import java.awt.Color;
import java.awt.Point;

import javax.swing.JButton;

/**
 * The individual buttons in the buttons grid
 * @author Mason Tolley
 *
 */
@SuppressWarnings("serial")
public class LifeBoardButton extends JButton {
	
	// init variables
	private boolean alive;
	private int numAdj;
	private Point location;
	
	/**
	 * Constructor
	 */
	public LifeBoardButton() {
		alive = false;
		setBackground(Color.white);
	}
	
	/**
	 * sets to the opposite of alive
	 */
	public void setAlive() {
		alive = !alive;
		if(alive) {
			setBackground(Color.black);
		}
		else {
			setBackground(Color.white);
		}
	}
	/**
	 * Sets alive to true if set is true
	 * @param set
	 */
	public void setAlive(boolean set) {
		alive = set;
		if(set) {
			setBackground(Color.black);
		}
		else {
			setBackground(Color.white);
		}
	}
	
	/**
	 * Returns true if alive
	 * @return alive
	 */
	public boolean getAlive() {
		return alive;
	}
	
	@Override
	public LifeBoardButton clone() {
		LifeBoardButton temp = new LifeBoardButton();
		temp.setAlive(alive);
		temp.setBackground(getBackground());
		return temp;
	}
	/**
	 * Sets the number of adjacent living cells to adj
	 * @param adj
	 */
	public void setAdj(int adj) {
		numAdj = adj;
	}
	/**
	 * Returns the number of adjacent living cells
	 * @return numAdj
	 */
	public int getAdj() {
		return numAdj;
	}
	/**
	 * Returns the stored location of the button
	 * @return location
	 */
	public Point getLoc() {
		return location;
	}
	/**
	 * Sets the location of the button
	 * @param loc
	 */
	public void setLoc(Point loc) {
		location = loc;
	}
}
