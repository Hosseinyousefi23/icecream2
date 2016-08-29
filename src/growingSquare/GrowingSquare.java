package growingSquare;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GrowingSquare extends JFrame {

	int counter = 0;
	Color color = Color.BLACK;

	DrawPanel drawPanel = new DrawPanel();

	public GrowingSquare() {
		ActionListener listener = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {

				if (counter == 0) {
					Random random = new Random();
					float hue = random.nextFloat();
					float saturation = (random.nextInt(2000) + 1000) / 10000f;
					float luminance = 0.9f;
					color = Color.getHSBColor(hue, saturation, luminance);
				}
				if (counter < 13) {
					counter++;
					drawPanel.repaint();
					if (counter == 12) {
						counter = 0;
					}
				}
			}
		};
		final Timer timer = new Timer(500, listener);

		drawPanel.setLayout(null);
		add(drawPanel);

		JButton start = new JButton("Start");
		start.setBounds(40, 100, 100, 60);
		drawPanel.add(start);

		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				timer.start();
				counter = 0;
			}
		});

		JButton end = new JButton("End");
		end.setBounds(40, 170, 100, 60);
		drawPanel.add(end);

		end.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				timer.stop();
			}
		});

		pack();
		setSize(1200, 730);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		drawPanel.setBackground(Color.darkGray);

	}

	private class DrawPanel extends JPanel {

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			g.setColor(color);
			g.drawRect(600 - counter * 20, 350 - counter * 20, counter * 40, counter * 40);
			g.drawOval(600 - counter * 20 - 2, 350 - counter * 20 - 2, 4, 4);
			g.drawOval(600 - counter * 20 - 2, 350 - counter * 20 + counter * 40 - 2, 4, 4);
			g.drawOval(600 - counter * 20 + counter * 40 - 2, 350 - counter * 20 - 2, 4, 4);
			g.drawOval(600 - counter * 20 + counter * 40 - 2, 350 - counter * 20 + counter * 40 - 2, 4, 4);
			g.drawOval(600 - 2, 350 - 2, 4, 4);
		}

	}

}
