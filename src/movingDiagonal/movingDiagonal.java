package movingDiagonal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import base.AbstractPanel;

public class movingDiagonal extends JFrame {
	int counter = 0;
	int sgn = 1, sgn2 = -1;
	int index = 0;
	int width = 1200, height = 700;
	Color color = Color.BLACK;
	String[] imgName = { "best-practices.png.rendition.intel.web.36.36.png",
			"fb.png", "google.png", "instagram.png", "linkedin.png",
			"original-original-for-sale.png", "things_to_do_icon_50_50_s_c1" };

	DrawPanel drawPanel = new DrawPanel();

	public movingDiagonal() {
		ActionListener listener = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {

				if (counter == 0) {

				}
				if (counter < 12) {
					counter++;
					sgn = sgn * (-1);
					drawPanel.repaint();
					if (counter == 11) {
						counter = 0;
						sgn = 1;
						sgn2 = -sgn2;
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
				sgn = 1;
				sgn2 = -1;

				Random random = new Random();
				index = random.nextInt(imgName.length) - 1;
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
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		drawPanel.setBackground(Color.darkGray);

	}

	private class DrawPanel extends AbstractPanel {

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			try {
				g.setColor(color);

				String IMG_PATH = "files/ÇÔ˜Çá_ÏÑåã_ÏÑåã/images/"
						+ imgName[index];
				BufferedImage img = ImageIO.read(new File(IMG_PATH));

				int step = width / 30;
				if (counter > 0)
					g.drawImage(img, width / 2 + sgn * counter * step,
							height / 2 + -sgn2 * sgn * counter * height * step
									/ width, null);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void paint(Graphics2D g) {

		}

		@Override
		public void create() {
			// TODO Auto-generated method stub

		}

		@Override
		public void end() {
			// TODO Auto-generated method stub

		}

	}

}
