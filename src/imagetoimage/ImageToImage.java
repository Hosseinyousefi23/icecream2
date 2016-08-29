package imagetoimage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ImageToImage extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	// all images in given directory
	private ArrayList<Image> images;
	// images to show (selected randomaly in this version)
	private ArrayList<Image> toShowImages;
	// Directory
	private static final File dir = new File("files/blinking_images/images");
	// all pasvands :D
	private static final String[] EXTENSIONS = new String[] { "gif", "png", "bmp" };
	// screen size for dynamic show :D
	private int SCREEN_WIDTH, SCREEN_HEIGHT;
	// Timer for sleeping and blinking
	private Timer timer;
	// timer time to sleep in miliseconds
	private int blinkingTime = 500;
	// How many images you like to show each time
	private int toShowImagesNumber = 20;
	// images width and height
	private int imagesWidth = 30, imagesHeight = 30;

	// pass screen size from frame class
	public ImageToImage(int screenWidth, int screenHeight) {
		images = new ArrayList<>();
		toShowImages = new ArrayList<>();
		this.SCREEN_WIDTH = screenWidth;
		this.SCREEN_HEIGHT = screenHeight;
		timer = new Timer(blinkingTime, this);
		timer.start();

	}

	// searching for all images throught the give directory
	private static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

		@Override
		public boolean accept(final File dir, final String name) {
			for (final String ext : EXTENSIONS) {
				if (name.endsWith("." + ext)) {
					return (true);
				}
			}
			return (false);
		}
	};

	public void invokeAllImages() {
		if (dir.isDirectory()) {
			for (File f : dir.listFiles(IMAGE_FILTER)) {

				BufferedImage img = null;

				try {
					img = ImageIO.read(f);
					// add to images array
					ImageIcon imgIcon = new ImageIcon(img);
					images.add(imgIcon.getImage());
					// images.addAll(images);
					// toShowImages = getRandomImages(toShowImagesNumber);
					selectWhichImagesToShow();
				} catch (final IOException e) {
					// in ja error ro neshon midim cherto perte ......
				}
			}
		}
	}

	public void selectWhichImagesToShow() {

		Thread t = new Thread() {
			public void run() {
				try {
					while (true) {
						toShowImages = new ArrayList<>();
						Thread.sleep(blinkingTime);
						toShowImages = getRandomImages(toShowImagesNumber);
						Thread.sleep(blinkingTime);
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}

	public ArrayList<Image> getRandomImages(int imagesToPickUp) {
		Random rnd = new Random();
		ArrayList<Image> selectedImages = new ArrayList<>();
		for (int i = 0; i < imagesToPickUp; i++) {
			selectedImages.add(images.get(rnd.nextInt(images.size() - 1)));
		}
		return selectedImages;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D gg = (Graphics2D) g;
		super.paint(gg);

		ArrayList<Point> positions = getPositions();
		for (int i = 0; i < positions.size(); i++) {
			gg.drawImage(toShowImages.get(i), positions.get(i).x, positions.get(i).y, imagesWidth, imagesHeight, this);
		}

	}

	// make random positions and return array of points respectively
	public ArrayList<Point> getPositions() {
		ArrayList<Point> positions = new ArrayList<>();
		Random rnd = new Random();
		int rightBoundary = SCREEN_WIDTH - 100;
		int leftBoundary = SCREEN_HEIGHT - 100;
		int x, y;
		for (int i = 0; i < toShowImages.size(); i++) {

			x = rnd.nextInt(rightBoundary);
			y = rnd.nextInt(leftBoundary);
			Point tempPoint = new Point(x, y);
			if (!checkDuplicated(tempPoint, positions)) {
				positions.add(new Point(x, y));
			} else {
				i = i - 1;
			}

		}
		return positions;
	}

	public boolean checkDuplicated(Point newPoint, ArrayList<Point> allPoints) {
		int Threashold = 10;
		Rectangle newPointRect = new Rectangle(newPoint.x, newPoint.y, imagesWidth + Threashold,
				imagesHeight + Threashold);
		for (Point eachPoint : allPoints) {
			Rectangle eachPointRect = new Rectangle(eachPoint.x, eachPoint.y, imagesWidth + Threashold,
					imagesHeight + Threashold);
			if (newPointRect.intersects(eachPointRect)) {
				return true;
			}
		}
		return false;
	}

	// repeat repainting for blinking purposes
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

}
