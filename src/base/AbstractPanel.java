package base;

import java.awt.Graphics2D;

import javax.swing.JPanel;

public abstract class AbstractPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * paints main objects
	 * 
	 * @author Hossein
	 * @param g
	 * 
	 * 
	 */
	public abstract void paint(Graphics2D g);

	/**
	 * Read from files if necessary. set fields and ...
	 * 
	 * @author Hossein
	 */
	public abstract void create();

	/**
	 * Dispose panel. close opened files and get back to the main menu.
	 * 
	 * @author Hossein
	 */
	public abstract void end();

	public void start() {

	}

}
