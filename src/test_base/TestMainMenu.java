package test_base;

import javax.swing.JFrame;

import base.MainMenu;

public class TestMainMenu {
	public static void main(String[] args) {
		JFrame application = new JFrame();
		application.setSize(600, 600);
		application.setVisible(true);
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainMenu menu = new MainMenu();
		application.add(menu);
		application.setSize(700, 700);
	}
}
