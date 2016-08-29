package matn_se_khati;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ThreeLineText extends JPanel {
	public static final int SPEED = 10;
	private static File textsFolder = new File("files/matn_se_khati/texts");
	private Random r;
	private Scanner scanner;
	private String text;
	private ArrayList<String> splitedText;
	int currentLine = 0;

	public ThreeLineText() {
		r = new Random();
		splitedText = new ArrayList<String>();
		try {
			create();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void splitString(Graphics g, String s, int width) {
		FontMetrics fm = g.getFontMetrics();

		String[] words = s.split(" ");

		int curX = 0;
		String segment = "";
		int i = 0;
		while (i < words.length) {
			int wordWidth = fm.stringWidth(words[i] + " ");
			if (curX + wordWidth >= width) {
				splitedText.add(segment);
				segment = "";
				curX = 0;
			} else {
				segment += words[i] + " ";
				curX += wordWidth;
				i++;
			}

		}
	}

	public void create() throws FileNotFoundException {
		File[] files = textsFolder.listFiles();
		int a = r.nextInt(files.length);
		scanner = new Scanner(files[a]);
		text = scanner.useDelimiter("\\Z").next();
		try {
			text = new String(text.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(new Font("Segoe Ui", 20, 20));
		g.drawString(text, 20, 20);

	}

}
