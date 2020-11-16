import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Font;
import java.util.Random;

/**
 * CS 121 Project 1: Traffic Animation
 *
 * Animates a UFO on a conveyer belt, testing its tractor beam
 *
 * @author BSU CS 121 Instructors
 * @author Mason Tolley
 */
@SuppressWarnings("serial")
public class GameOfLife extends JPanel
{
	// This is where you declare constants and variables that need to keep their
	// values between calls	to paintComponent(). Any other variables should be
	// declared locally, in the method where they are used.

	/**
	 * A constant to regulate the frequency of Timer events.
	 * Note: 100ms is 10 frames per second - you should not need
	 * a faster refresh rate than this
	 */
	private final int DELAY = 100; //milliseconds

	/**
	 * The anchor coordinate for drawing / animating. All of your vehicle's
	 * coordinates should be relative to this offset value.
	 */
	private int xOffset = -30;
	private int useXOffset = 0;
	static boolean[][] grid;
	/**
	 * The number of pixels added to xOffset each time paintComponent() is called.
	 */
	private int stepSize = 10;

	private final Color BACKGROUND_COLOR = new Color(100, 100, 100);

	/* This method draws on the panel's Graphics context.
	 * This is where the majority of your work will be.
	 *
	 * (non-Javadoc)
	 * @see java.awt.Container#paint(java.awt.Graphics)
	 */
	boolean[][] cloneGrid(boolean[][] old) {
		boolean[][] newArray = new boolean[old.length][];
		for (int i = 0; i < old.length; i++) {
			newArray[i] = old[i].clone();
		}
		return newArray;
	}
	public void paintComponent(Graphics g)
	{
		//account for changes to window size
		int width = getWidth(); // panel width
		int height = getHeight(); // panel height
		// calc cell width
		int cellWidth = (int)((double)width / grid[0].length);
		int cellHeight = (int)((double)height / grid.length);
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.black);
		// paint cells
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				g.drawRect(j * (cellWidth), i * cellHeight, cellWidth, cellHeight);
				if(grid[i][j]) {
					g.fillRect(j * (cellWidth), i * cellHeight, cellWidth, cellHeight);
				}
			}
		}
		boolean[][] newGrid = cloneGrid(grid);
		// check spots
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				int numBy = 0;
				// down
				if(i != grid.length - 1 && grid[i + 1][j]) {
					numBy++;
				}
				// up
				if(i != 0 && grid[i - 1][j]) {
					numBy++;
				}
				// left
				if(j != 0 && grid[i][j - 1]) {
					numBy++;
				}
				// right
				if(j != grid[i].length - 1 && grid[i][j + 1]) {
					numBy++;
				}
				// down right
				if(i != grid.length - 1 && j != grid[i].length - 1 && grid[i + 1][j + 1]) {
					numBy++;
				}
				// down left
				if(i != grid.length - 1 && j != 0 && grid[i + 1][j - 1]) {
					numBy++;
				}
				// up right
				if(i != 0 && j != grid[i].length - 1 && grid[i - 1][j + 1]) {
					numBy++;
				}
				// up left
				if(i != 0 && j != 0 && grid[i - 1][j - 1]) {
					numBy++;
				}
				if(newGrid[i][j]) {
					if(numBy == 2 || numBy == 3) {
						newGrid[i][j] = true;
					}
					if(numBy < 2) {
						newGrid[i][j] = false;
					}
					if (numBy > 3) {
						newGrid[i][j] = false;
					}
				}
				else if(!newGrid[i][j]) {
					if(numBy == 3) {
						newGrid[i][j] = true;
					}
				}
			}
		}
		grid = cloneGrid(newGrid);
		
		// Put your code above this line. This makes the drawing smoother.
		Toolkit.getDefaultToolkit().sync();
	}


	//==============================================================
	// You don't need to modify anything beyond this point.
	//==============================================================


	/**
	 * Starting point for this program. Your code will not go in the
	 * main method for this program. It will go in the paintComponent
	 * method above.
	 *
	 * DO NOT MODIFY this method!
	 *
	 * @param args unused
	 */
	public static void main (String[] args)
	{
		Random rand = new Random();
		// initialize
		grid = new boolean[50][50];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = false;
			}
		}
		grid[1][3] = true;
		grid[2][1] = true;
		grid[2][3] = true;
		grid[3][2] = true;
		grid[3][3] = true;
		// DO NOT MODIFY THIS CODE.
		JFrame frame = new JFrame ("Game of Life");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new GameOfLife());
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Constructor for the display panel initializes necessary variables.
	 * Only called once, when the program first begins. This method also
	 * sets up a Timer that will call paint() with frequency specified by
	 * the DELAY constant.
	 */
	public GameOfLife()
	{
		// Do not initialize larger than 800x600. I won't be able to
		// grade your project if you do.
		int initWidth = 500;
		int initHeight = 500;
		setPreferredSize(new Dimension(initWidth, initHeight));
		this.setDoubleBuffered(true);

		//Start the animation - DO NOT REMOVE
		startAnimation();
	}

	/**
	 * Create an animation thread that runs periodically.
	 * DO NOT MODIFY this method!
	 */
	private void startAnimation()
	{
		ActionListener timerListener = new TimerListener();
		Timer timer = new Timer(DELAY, timerListener);
		timer.start();
	}

	/**
	 * Repaints the graphics panel every time the timer fires.
	 * DO NOT MODIFY this class!
	 */
	private class TimerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			repaint();
		}
	}
}
