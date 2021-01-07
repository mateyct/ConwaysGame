import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Contains the formatting for the simulation and all of the components
 * @author Mason Tolley
 *
 */
@SuppressWarnings("serial")
public class LifeGamePanel extends JPanel {
	
	// init variables and components
	private JButton start;
	private JButton reset;
	private LifeBoardPanel board;
	private PlaceType toPlace;
	private JPanel modeSelect;
	private ButtonGroup modeSelectGroup;
	private JRadioButton normalButton;
	private JRadioButton blinkerButton;
	private JRadioButton gosperlButton;
	
	public LifeGamePanel(int pixels, int gridSize) {
		// init LifeBoardPanel
		board = new LifeBoardPanel(pixels, gridSize, new CellListener());
		start = new JButton("Start/Pause");
		start.addActionListener(new StartListener());
		start.setHideActionText(true);
		reset = new JButton("Reset");
		reset.addActionListener(new ResetListener());
		add(board);
		add(start);
		add(reset);
		toPlace = PlaceType.normal;
		// configure panel and button group
		modeSelect = new JPanel();
		modeSelect.setPreferredSize(new Dimension(100, 300));
		modeSelectGroup = new ButtonGroup();
		// set radio buttons
		normalButton = new JRadioButton();
		normalButton.setActionCommand("normal");
		normalButton.setText("Normal");
		normalButton.addActionListener(new SelectPlacementType());
		normalButton.doClick();
		
		blinkerButton = new JRadioButton();
		blinkerButton.setActionCommand("blinker");
		blinkerButton.addActionListener(new SelectPlacementType());
		blinkerButton.setText("Blinker");
		
		gosperlButton = new JRadioButton();
		gosperlButton.setActionCommand("gospergun");
		gosperlButton.addActionListener(new SelectPlacementType());
		gosperlButton.setText("Gosper Gun");
		// add radio buttons
		modeSelectGroup.add(normalButton);
		modeSelectGroup.add(blinkerButton);
		modeSelectGroup.add(gosperlButton);
		// add selection panel
		modeSelect.add(normalButton);
		modeSelect.add(blinkerButton);
		modeSelect.add(gosperlButton);
		add(modeSelect);
	}
	/**
	 * The listener for the LifeBoardButton behavior
	 * @author Mason Tolley
	 *
	 */
	private class CellListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// selected
			LifeBoardButton selected = (LifeBoardButton) e.getSource();
			board.selectButtons(selected.getLoc(), toPlace);
		}
		
	}
	/**
	 * The listener for the start button
	 * @author Mason Tolley
	 *
	 */
	private class StartListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// call the animation changer
			board.animationCue();
		}
		
	}
	/**
	 * Changes the placement type
	 * @author Mason Tolley
	 *
	 */
	private class SelectPlacementType implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("normal")) {
				toPlace = PlaceType.normal;
			}
			else if (e.getActionCommand().equals("blinker")) {
				toPlace = PlaceType.blinker;
			}
			else if (e.getActionCommand().equals("gospergun")) {
				toPlace = PlaceType.gliderGun;
			}
		}
		
	}
	/**
	 * Resets the board
	 * @author Mason Tolley
	 *
	 */
	private class ResetListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			board.reset();
		}
		
	}
}
/**
 * Contains the options for the place option
 * @author Mason Tolley
 *
 */
enum PlaceType {
	normal,
	gliderGun,
	blinker
}
