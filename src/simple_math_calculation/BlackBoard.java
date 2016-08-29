package simple_math_calculation;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BlackBoard extends JPanel {

	private static final long serialVersionUID = 1L;

	private int ScreenWidth, ScreenHeight;
	private String fontName = "Yekan.ttf";
	private ArrayList<String> allQuestions;
	private int firstOperand = 0, secondOperand = 2, equal = 3, operator = 1, answer = 4;
	private String currentAnswerString = "...";
	private Image blackBoardImg;
	private GraphicsEnvironment ge = null;
	private int seconds, minutes;

	private int score;
	private int questionsNumber;
	private int currentQuestionNumber;
	private double currentAnswer;

	private TextField answerInput;
	private Button nextQuestionBt;

	private int blackBoardWidth, blackBoardHeight, dashboardWidth, dashboardHeight;
	private int blackboardLeftMargin, blackBoardTopMargin, dashboardLeftMargin, dashboardTopMargin;

	private boolean isEnd = false;

	private Thread t;

	public BlackBoard(int ScreenWidth, int ScreenHeight, String difficulty) {
		this.ScreenHeight = ScreenHeight;
		this.ScreenWidth = ScreenWidth;
		GetQuestions(difficulty);
		loadImages();
		StartTimer();
		SetItems();
		ControlActions();

		questionsNumber = allQuestions.size();

		currentQuestionNumber = 0;
		score = 0;
		seconds = 0;
		minutes = 0;
	}

	private void SetItems() {
		answerInput = new TextField();
		nextQuestionBt = new Button("بعدی");

		add(answerInput);
		add(nextQuestionBt);
	}

	private void SetItemsSizeAndPosition() {
		int btX = (blackboardLeftMargin + blackBoardWidth) - 110, btY = blackBoardTopMargin + blackBoardHeight + 10,
				btWidth = 100, btHeight = 50;
		int txtFX = (ScreenWidth / 2) - 100, txtFY = blackBoardHeight + blackBoardTopMargin + 10, txtFWidth = 200,
				txtFHeight = 50;
		answerInput.setBounds(txtFX, txtFY, txtFWidth, txtFHeight);
		answerInput.setFont(new Font("Yektan.ttf", Font.BOLD, 20));

		nextQuestionBt.setBounds(btX, btY, btWidth, btHeight);
		nextQuestionBt.setFont(new Font("Yektan.ttf", Font.BOLD, 20));

	}

	private void ControlActions() {
		answerInput.addTextListener(new TextListener() {

			@Override
			public void textValueChanged(TextEvent e) {
				String currentValue = answerInput.getText();
				if (currentValue.equals("")) {
					currentAnswerString = "...";
					repaint();

				} else {
					currentAnswerString = currentValue;
					repaint();
				}
			}
		});
		nextQuestionBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String enteredValue = answerInput.getText();
				double answer = 0;
				try {
					answer = Integer.parseInt(enteredValue);
					if (answer == currentAnswer) {
						score++;

					}
					if (currentQuestionNumber + 1 >= questionsNumber) {
						isEnd = true;
						t.stop();
					}
					currentQuestionNumber++;
					answerInput.setText("");
					currentAnswerString = "...";

					repaint();
				} catch (Exception s) {
					JOptionPane.showMessageDialog(null, "لطفا عدد وارد کنید!");
				}
			}
		});
	}

	private void loadImages() {
		String parentDirectory = "files/simple_math_calculation/images/%s";
		blackBoardImg = new ImageIcon(String.format(parentDirectory, "blackboard.png")).getImage();
	}

	public void StartTimer() {

		t = new Thread() {
			public void run() {
				try {
					while (true) {
						Thread.sleep(1000);
						seconds++;
						if (seconds > 60) {
							minutes++;
							seconds = 0;
						}
						repaint();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}

	public String[] GetQuestionComponents(String question) {
		String[] questionPart = new String[5];
		questionPart = question.split(" ");
		return questionPart;
	}

	public void GetQuestions(String difficulty) {
		allQuestions = new ArrayList<>();
		File fileToRead = null;
		String parentDir = "files/simple_math_calculation/questions/%s";
		if (difficulty == "simple")
			fileToRead = new File(String.format(parentDir, "simple.txt"));
		if (difficulty == "medium")
			fileToRead = new File(String.format(parentDir, "medium.txt"));
		if (difficulty == "hard")
			fileToRead = new File(String.format(parentDir, "hard.txt"));

		try (BufferedReader br = new BufferedReader(new FileReader(fileToRead))) {
			String line;
			try {
				ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(String.format("font/%s", fontName))));
			} catch (IOException | FontFormatException e) {
			}
			while ((line = br.readLine()) != null) {
				line = new String(line.getBytes(), "UTF-8");
				allQuestions.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics g) {

		Graphics2D gg = (Graphics2D) g;
		super.paint(gg);
		int gap = 25;
		blackBoardWidth = ScreenWidth - 400;
		blackBoardHeight = ScreenHeight - 400;
		blackboardLeftMargin = 200;
		blackBoardTopMargin = 150;

		dashboardWidth = blackBoardWidth;
		dashboardHeight = blackBoardTopMargin - gap;
		dashboardLeftMargin = blackboardLeftMargin;
		dashboardTopMargin = 10;
		this.setBackground(Color.white);
		Dashboard(gg);
		MathQuestions(gg);
		InputAnswer(gg);

	}

	public void Dashboard(Graphics2D g) {

		g.setColor(Color.lightGray);
		g.fillRect(dashboardLeftMargin, dashboardTopMargin, dashboardWidth, dashboardHeight);
		g.setFont(new Font("Yekan.ttf", Font.BOLD, 20));
		g.setColor(Color.black);

		int xOfTimePassed = dashboardWidth + dashboardLeftMargin - 170,
				yOfTimePassed = (dashboardTopMargin + dashboardHeight + 10) / 2;

		String timePassed = "زمان طی شده : ";
		g.drawString(timePassed, xOfTimePassed, yOfTimePassed);
		xOfTimePassed = xOfTimePassed - 60;
		g.drawString(minutes + ":" + seconds, xOfTimePassed, yOfTimePassed);

		xOfTimePassed = xOfTimePassed - 370;
		String stage = "مرحله : ";
		g.drawString(stage, xOfTimePassed, yOfTimePassed);
		xOfTimePassed = xOfTimePassed - 50;
		g.drawString(currentQuestionNumber + "/" + questionsNumber, xOfTimePassed, yOfTimePassed);

		String score = "امتیاز : ";
		xOfTimePassed = xOfTimePassed - 370;
		g.drawString(score, xOfTimePassed, yOfTimePassed);
		xOfTimePassed = xOfTimePassed - 10;
		g.drawString(this.score + "", xOfTimePassed, yOfTimePassed);

	}

	public void MathQuestions(Graphics2D g) {

		g.drawImage(blackBoardImg, blackboardLeftMargin, blackBoardTopMargin, blackBoardWidth, blackBoardHeight, this);
		if (!isEnd) {
			String[] question = GetQuestionComponents(allQuestions.get(currentQuestionNumber));

			g.setFont(new Font("Yekan.ttf", Font.BOLD, 150));
			g.setColor(Color.white);

			int xOfQuestion = (blackboardLeftMargin) + 100;
			int yOfQuestion = (blackBoardHeight + blackBoardTopMargin + 180) / 2;
			g.drawString(question[firstOperand], xOfQuestion, yOfQuestion);
			xOfQuestion += 180;
			g.drawString(question[operator], xOfQuestion, yOfQuestion);
			xOfQuestion += 150;
			g.drawString(question[secondOperand], xOfQuestion, yOfQuestion);
			xOfQuestion += 150;
			g.drawString(question[equal], xOfQuestion, yOfQuestion);
			xOfQuestion += 150;
			g.setFont(new Font("Yekan.ttf", Font.BOLD, 150));
			g.drawString(currentAnswerString, xOfQuestion, yOfQuestion);

			currentAnswer = Integer.parseInt(question[answer]);
		} else {
			int xOfEnd = ScreenWidth / 2 - 450;
			int yOfEnd = ScreenHeight / 2;
			g.setFont(new Font("Yekan.ttf", Font.BOLD, 200));
			g.setColor(Color.white);
			g.drawString("پایان آزمون", xOfEnd, yOfEnd);
		}
	}

	public void InputAnswer(Graphics2D g) {
		SetItemsSizeAndPosition();
	}

}
