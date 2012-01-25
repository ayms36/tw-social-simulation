package gui;

import java.awt.Color;
import java.util.Random;

public class IdeaGUI {
	Color color;
	public IdeaGUI() {
		Random r = new Random();
		this.color = new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
	}
	public Color getC() {
		return color;
	}
	public void setC(Color c) {
		this.color = c;
	}
}
