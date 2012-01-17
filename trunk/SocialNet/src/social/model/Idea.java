package social.model;

import java.util.List;

public class Idea {
	String name;
	
	Idea(String name) {
		if(name == null)
			throw new NullPointerException("name cannot be null");
		this.name = name;
	}
	public String getName() {
		return name;
	};
	
	@Override
	public boolean equals(Object a) {
		if(a != null && a instanceof Idea){
			return name.equals(((Idea) a).getName());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
