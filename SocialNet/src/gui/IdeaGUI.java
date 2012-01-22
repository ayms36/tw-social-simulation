package gui;

public class IdeaGUI {
	String name;
	
	public IdeaGUI(String name) {
		if(name == null)
			throw new NullPointerException("name cannot be null");
		this.name = name;
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
