package test_simple_math_calculation;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import simple_math_calculation.BlackBoard;

public class TestBlackBoard {
	public static void main(String[] args) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight() - 10;
		JFrame application = new JFrame();
		application.setSize(width, height);
		application.setVisible(true);
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String simple = "simple";
		BlackBoard blinkingImages = new BlackBoard(width, height, simple);
		application.add(blinkingImages);
		application.setSize(width - 1, height - 1);

	}
}
