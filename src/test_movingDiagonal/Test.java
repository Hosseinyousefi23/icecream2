package test_movingDiagonal;

import java.awt.EventQueue;

import movingDiagonal.movingDiagonal;


public class Test {

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
            public void run() {
            	 new movingDiagonal();
            }
        });
		
		

	}
}
