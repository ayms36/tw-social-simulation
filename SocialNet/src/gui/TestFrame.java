package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class TestFrame {

	private static GUIFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				frame = new GUIFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
	});
	}
}
