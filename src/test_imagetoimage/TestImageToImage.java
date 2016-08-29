package test_imagetoimage;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import imagetoimage.ImageToImage;

public class TestImageToImage {
	public static void main(String[] args) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth() - 10;
		int height = (int) screenSize.getHeight() - 10;
		JFrame application = new JFrame();
		application.setSize(width, height);
		application.setVisible(true);
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageToImage blinkingImages = new ImageToImage(width, height);
		blinkingImages.invokeAllImages();
		application.add(blinkingImages);
		application.setSize(width - 1, height - 1);

	}

}
