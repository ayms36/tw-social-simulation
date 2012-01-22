package gui;

import java.awt.Color;
import java.util.Random;

public class IdeaGUI {
	String name;
	Color color;
	public IdeaGUI(String name) {
		if(name == null)
			throw new NullPointerException("name cannot be null");
		this.name = name;
		Random r = new Random();
		this.color = new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
	}
	public Color getC() {
		return color;
	}
	public void setC(Color c) {
		this.color = c;
	}
	public String getName() {
		return name;
	};
	
	@Override
	public boolean equals(Object a) {
		if(a != null && a instanceof IdeaGUI){
			return name.equals(((IdeaGUI) a).getName());
		}
		return false;
	}

	
	
	public String toString(){
		return name;
	}
}
