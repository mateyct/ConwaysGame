import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * The panel containing the grid of buttons
 * @author Mason Tolley
 *
 */
@SuppressWarnings("serial")
public class LifeBoardPanel extends JPanel {
	
	// init buttons array
	private LifeBoardButton[][] buttons;
	private Timer timer;
	
	public LifeBoardPanel(int pixels, int gridSize, ActionListener listener) {
		buttons = new LifeBoardButton[gridSize][gridSize];
		setLayout(new GridLayout(buttons.length, buttons[0].length, 0, 0));
		// loop through buttons array
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				// set buttons
				buttons[i][j] = new LifeBoardButton();
				buttons[i][j].addActionListener(listener);
				buttons[i][j].setPreferredSize(new Dimension(pixels / gridSize, pixels / gridSize));
				buttons[i][j].setLoc(new Point(i,j));
				add(buttons[i][j]);
			}
		}
		// define timer
		TimerActionListener taskPerformer = new TimerActionListener();
	    timer = new Timer(100, taskPerformer);
	}
	/**
	 * Creates the next phase of the process
	 */
	public void nextPhase() {
		for(int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				int numAdj = 0;
				// down
				if(i != buttons.length - 1 && buttons[i + 1][j].getAlive()) {
					numAdj++;
				}
				// up
				if(i != 0 && buttons[i - 1][j].getAlive()) {
					numAdj++;
				}
				// left
				if(j != 0 && buttons[i][j - 1].getAlive()) {
					numAdj++;
				}
				// right
				if(j != buttons[i].length - 1 && buttons[i][j + 1].getAlive()) {
					numAdj++;
				}
				// down right
				if(i != buttons.length - 1 && j != buttons[i].length - 1 && buttons[i + 1][j + 1].getAlive()) {
					numAdj++;
				}
				// down left
				if(i != buttons.length - 1 && j != 0 && buttons[i + 1][j - 1].getAlive()) {
					numAdj++;
				}
				// up right
				if(i != 0 && j != buttons[i].length - 1 && buttons[i - 1][j + 1].getAlive()) {
					numAdj++;
				}
				// up left
				if(i != 0 && j != 0 && buttons[i - 1][j - 1].getAlive()) {
					numAdj++;
				}
				buttons[i][j].setAdj(numAdj);
			}
		}
		// loop through buttons a second time to check for number adjacent and set accordingly
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				if(buttons[i][j].getAlive()) {
					if(buttons[i][j].getAdj() == 2 || buttons[i][j].getAdj()  == 3) {
						buttons[i][j].setAlive(true);
					}
					if(buttons[i][j].getAdj()  < 2) {
						buttons[i][j].setAlive(false);
					}
					if (buttons[i][j].getAdj()  > 3) {
						buttons[i][j].setAlive(false);
					}
				}
				else if(!buttons[i][j].getAlive()) {
					if(buttons[i][j].getAdj()  == 3) {
						buttons[i][j].setAlive(true);
					}
				}
			}
		}
		repaint();
	}
	/**
	 * Clones the two dimensional LifeBoardButton array
	 * @return 
	 */
	LifeBoardButton[][] cloneArray(LifeBoardButton[][] array) {
		LifeBoardButton[][] temp = new LifeBoardButton[buttons.length][buttons[0].length];
		// loop through and clone array so you don't break it with aliasing
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[i].length; j++) {
				temp[i][j] = array[i][j].clone();
				temp[i][j].setEnabled(false);
			}
		}
		return temp;
	}
	/**
     * Action event for the player animation
     */
	private class TimerActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
            nextPhase();
		}
	}
	/**
    * Calls the animation for the player
    */
    public void animationCue() {
    	// if not running, play animation and disable buttons
    	if (!timer.isRunning()) {
	    	for (int i = 0; i < buttons.length; i++) {
				for (int j = 0; j < buttons[i].length; j++) {
					buttons[i][j].setEnabled(false);
				}
			}
	    	// start animation
	    	timer.start();
    	}
    	else {
    		for (int i = 0; i < buttons.length; i++) {
				for (int j = 0; j < buttons[i].length; j++) {
					buttons[i][j].setEnabled(true);
				}
			}
	    	// start animation
	    	timer.stop();
    	}
    }
    /**
     * Selects cells to be alive 
     * @param startPoint
     * @param typePlace
     */
    public void selectButtons(Point startPoint, PlaceType typePlace) {
    	if(typePlace == PlaceType.normal) {
    		if(!buttons[startPoint.x][startPoint.y].getAlive())
    			buttons[startPoint.x][startPoint.y].setAlive(true);
    		else {
    			buttons[startPoint.x][startPoint.y].setAlive(false);
    		}
    	}
    	else if(typePlace == PlaceType.gliderGun) {
    		placeGliderGun(startPoint);
    	}
    	else if(typePlace == PlaceType.blinker) {
    		buttons[startPoint.x][startPoint.y].setAlive(true);
    		try {
    			buttons[startPoint.x - 1][startPoint.y].setAlive(true);
    		}
    		catch(ArrayIndexOutOfBoundsException e) {
    			
    		}
    		try {
    			buttons[startPoint.x + 1][startPoint.y].setAlive(true);
    		}
    		catch(ArrayIndexOutOfBoundsException e) {
    			
    		}
    	}
    }
    /**
     * Places a gosper glider gun
     * @param startPoint
     */
    void placeGliderGun(Point startPoint) {
    	// create massive array of points
    	Point[] points = {startPoint, new Point(startPoint.x + 1,startPoint.y), 
    			new Point(startPoint.x,startPoint.y + 1),
    			new Point(startPoint.x + 1,startPoint.y + 1),
    			new Point(startPoint.x,startPoint.y + 10),
    			new Point(startPoint.x + 1,startPoint.y + 10),
    			new Point(startPoint.x + 2,startPoint.y + 10),
    			new Point(startPoint.x - 1,startPoint.y + 11),
    			new Point(startPoint.x + 3,startPoint.y + 11),
    			new Point(startPoint.x + 4,startPoint.y + 12),
    			new Point(startPoint.x - 2,startPoint.y + 12),
    			new Point(startPoint.x + 4,startPoint.y + 13),
    			new Point(startPoint.x - 2,startPoint.y + 13),
    			new Point(startPoint.x + 1,startPoint.y + 14),
    			new Point(startPoint.x - 1,startPoint.y + 15),
    			new Point(startPoint.x + 3,startPoint.y + 15),
    			new Point(startPoint.x,startPoint.y + 16),
    			new Point(startPoint.x + 1,startPoint.y + 16),
    			new Point(startPoint.x + 2,startPoint.y + 16),
    			new Point(startPoint.x + 1,startPoint.y + 17),
    			new Point(startPoint.x,startPoint.y + 20),
    			new Point(startPoint.x - 1,startPoint.y + 20),
    			new Point(startPoint.x - 2,startPoint.y + 20),
    			new Point(startPoint.x,startPoint.y + 21),
    			new Point(startPoint.x - 1,startPoint.y + 21),
    			new Point(startPoint.x - 2,startPoint.y + 21),
    			new Point(startPoint.x - 3,startPoint.y + 22),
    			new Point(startPoint.x + 1,startPoint.y + 22),
    			new Point(startPoint.x - 3,startPoint.y + 24),
    			new Point(startPoint.x + 1,startPoint.y + 24),
    			new Point(startPoint.x - 4,startPoint.y + 24),
    			new Point(startPoint.x + 2,startPoint.y + 24),
    			new Point(startPoint.x - 2,startPoint.y + 34),
    			new Point(startPoint.x - 1,startPoint.y + 34),
    			new Point(startPoint.x - 2,startPoint.y + 35),
    			new Point(startPoint.x - 1,startPoint.y + 35)};
    	// loop through and set buttons
    	for(Point p : points) {
    		try {
    			buttons[p.x][p.y].setAlive(true);
    		}
    		catch(ArrayIndexOutOfBoundsException e) {
    			
    		}
    	}
		
    }
    public void reset() {
    	for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons[i].length; j++) {
				buttons[i][j].setAlive(false);
			}
		}
    }
}
