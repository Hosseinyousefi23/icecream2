package base;

import java.awt.Button;
import java.util.ArrayList;

import javax.swing.JPanel;

public class MainMenu extends JPanel {
	private static final long serialVersionUID = 1L;
	public static String[] names = { "growing square", "image to image", "three line text" };
	public static ArrayList<Button> buttons;
	public static String[] classNames = { "growingSquare.GrowingSquare", "imagetoimage.ImageToImage",
			"matn_se_khati.ThreeLineText" };

	public MainMenu() {

		for (int i = 0; i < names.length; i++) {
			Button b = new Button(names[i]);
			b.addMouseListener(new EventListener(classNames[i]));
			this.add(b);
		}
	}
}
